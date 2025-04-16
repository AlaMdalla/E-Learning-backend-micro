package E_Learning.Project.Service;

import E_Learning.Project.Entity.Post;
import E_Learning.Project.Entity.PostInteraction;
import E_Learning.Project.Repository.PostRepository;
import E_Learning.Project.Repository.PostInteractionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostInteractionRepository postInteractionRepository;
    private final RestTemplate restTemplate = new RestTemplate();
    private final String apiKey = "AIzaSyCZ5SdI-SOUauldOzb5h48u1YIklPvJAoc";
    public double analyzeToxicity(String content) {
        String url = "https://commentanalyzer.googleapis.com/v1alpha1/comments:analyze?key=" + apiKey;

        Map<String, Object> request = new HashMap<>();
        Map<String, String> comment = new HashMap<>();
        comment.put("text", content);
        request.put("comment", comment);
        request.put("languages", List.of("en")); // Adjust language as needed
        request.put("requestedAttributes", Map.of("TOXICITY", new HashMap<>()));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(url, entity, Map.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to analyze toxicity: API returned status " + response.getStatusCode());
            }

            Map data = response.getBody();
            if (data == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to analyze toxicity: Empty response from API");
            }

            Map attributeScores = (Map) data.get("attributeScores");
            if (attributeScores == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to analyze toxicity: Missing attributeScores in response");
            }

            Map toxicity = (Map) attributeScores.get("TOXICITY");
            if (toxicity == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to analyze toxicity: Missing TOXICITY in response");
            }

            Map summaryScore = (Map) toxicity.get("summaryScore");
            if (summaryScore == null) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to analyze toxicity: Missing summaryScore in response");
            }

            Object value = summaryScore.get("value");
            if (value == null || !(value instanceof Number)) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to analyze toxicity: Invalid toxicity score in response");
            }

            return ((Number) value).doubleValue(); // Returns a score between 0.0 and 1.0
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Failed to analyze toxicity: " + e.getMessage(), e);
        }
    }

    public Post savePost(Integer userId, Post post) {
        String content = post.getContent();
        if (content != null && !content.isEmpty()) {
            try {
                double toxicityScore = analyzeToxicity(content);
                if (toxicityScore > 0.5) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                            "Le contenu est trop toxique (score : " + toxicityScore + ") et ne peut pas être publié");
                }
            } catch (ResponseStatusException e) {
                if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                    // Log the error and allow the post to be saved with a warning
                    System.err.println("Toxicity analysis failed: " + e.getMessage());
                    // Optionally, you could flag the post for manual review
                } else {
                    throw e; // Re-throw other errors (e.g., toxic content)
                }
            }
        }
        post.setUserId(userId);
        post.setLikeCount(0);
        post.setLaught(0);
        post.setAngry(0);
        post.setViewCount(0);
        post.setDate(new Date());
        return postRepository.save(post);
    }
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Post getPostById(Integer userId, Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            return optionalPost.get(); // Simply return the post if it exists
        } else {
            throw new EntityNotFoundException("Post not Found");
        }
    }

    public void viewPost(Integer userId, Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            // Check if the user has already viewed the post
            Optional<PostInteraction> interactionOpt = postInteractionRepository.findByPostIdAndUserId(postId, userId);
            PostInteraction interaction;

            if (interactionOpt.isPresent()) {
                interaction = interactionOpt.get();
                // If the user has already viewed the post, throw an exception
                if (interaction.isHasViewed()) {
                    throw new IllegalStateException("User has already viewed this post");
                }
            } else {
                // Create a new interaction if none exists
                interaction = new PostInteraction();
                interaction.setPost(post);
                interaction.setUserId(userId);
            }

            // Increment view count and mark as viewed
            post.setViewCount(post.getViewCount() + 1);
            interaction.setHasViewed(true);
            postRepository.save(post);
            postInteractionRepository.save(interaction);
        } else {
            throw new EntityNotFoundException("Post Not Found");
        }
    }

    public void reactPost(Integer userId, Long postId, String reactionType) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            // Check if the user has already reacted
            Optional<PostInteraction> interactionOpt = postInteractionRepository.findByPostIdAndUserId(postId, userId);
            PostInteraction interaction;

            if (interactionOpt.isPresent()) {
                interaction = interactionOpt.get();
                // If the user has already reacted, throw an exception
                if (interaction.getReactionType() != null) {
                    throw new IllegalStateException("User has already reacted to this post");
                }
            } else {
                // Create a new interaction if none exists
                interaction = new PostInteraction();
                interaction.setPost(post);
                interaction.setUserId(userId);
            }

            // Record the reaction
            switch (reactionType.toLowerCase()) {
                case "like":
                    post.setLikeCount(post.getLikeCount() + 1);
                    break;
                case "laugh":
                    post.setLaught(post.getLaught() + 1);
                    break;
                case "angry":
                    post.setAngry(post.getAngry() + 1);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid reaction type: " + reactionType);
            }

            interaction.setReactionType(reactionType.toLowerCase());
            postRepository.save(post);
            postInteractionRepository.save(interaction);
        } else {
            throw new EntityNotFoundException("Post Not Found");
        }
    }

    public Post getPostByIdAndUpdating(Integer userId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not Found"));
        // Optional: Add authorization check
        if (!post.getUserId().equals(userId)) {
            throw new SecurityException("User not authorized to access this post");
        }
        return post;
    }

    public void deletePost(Integer userId, Long postId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (!optionalPost.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with ID " + postId + " not found");
        }
        Post post = optionalPost.get();
        // Check if user is authorized to delete
        if (!post.getUserId().equals(userId)) {
            throw new SecurityException("User not authorized to delete this post");
        }
        postRepository.deleteById(postId);
    }

    public Post updatePost(Integer userId, Long postId, Post updatedPost) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post non trouvé avec l'ID : " + postId));

        // Check if user is authorized to update
        if (!existingPost.getUserId().equals(userId)) {
            throw new SecurityException("User not authorized to update this post");
        }

        // Check toxicity if the content is being updated
        if (updatedPost.getContent() != null && !updatedPost.getContent().isEmpty()) {
            double toxicityScore = analyzeToxicity(updatedPost.getContent());
            if (toxicityScore > 0.5) { // Threshold set to 0.5
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Le contenu mis à jour est trop toxique (score : " + toxicityScore + ") et ne peut pas être publié");
            }
        }

        // Mise à jour des champs uniquement si non null
        Optional.ofNullable(updatedPost.getTitle()).ifPresent(existingPost::setTitle);
        Optional.ofNullable(updatedPost.getContent()).ifPresent(existingPost::setContent);
        Optional.ofNullable(updatedPost.getImg()).ifPresent(existingPost::setImg);
        Optional.ofNullable(updatedPost.getCategory()).ifPresent(existingPost::setCategory);

        return postRepository.save(existingPost);
    }


        public String exchangeAccessToken(String shortLivedToken) throws Exception {
            String exchangeUrl = "https://graph.facebook.com/v20.0/oauth/access_token";
            Map<String, String> params = new HashMap<>();
            params.put("grant_type", "fb_exchange_token");
            params.put("client_id", "462761353526376");
            params.put("client_secret", "e6a78a363faea17aafa37efdb0c66d49"); // Use the App Secret here
            params.put("fb_exchange_token", shortLivedToken);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, String>> entity = new HttpEntity<>(params, headers);

            try {
                ResponseEntity<Map> response = restTemplate.postForEntity(exchangeUrl, entity, Map.class);
                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    return (String) response.getBody().get("access_token");
                } else {
                    throw new Exception("Failed to exchange access token: API returned status " + response.getStatusCode());
                }
            } catch (Exception e) {
                throw new Exception("Failed to exchange access token: " + e.getMessage(), e);
            }
        }

    public void sharePostOnFacebook(Integer userId, Long postId, String accessToken) throws Exception {
        // Étape 1 : Échanger le token
        String longLivedToken = exchangeAccessToken(accessToken);

        // Étape 2 : Vérifier le post
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (!optionalPost.isPresent()) {
            throw new EntityNotFoundException("Post with ID " + postId + " not found");
        }

        Post post = optionalPost.get();
        if (!post.getUserId().equals(userId)) {
            throw new SecurityException("User not authorized to share this post");
        }

        // Étape 3 : Obtenir pageId et pageToken
        Map<String, String> pageData = getPages(longLivedToken);
        String pageId = pageData.get("pageId");
        String pageAccessToken = pageData.get("pageToken");

        // Construire le message
        String message = post.getTitle() + "\n" + post.getContent();
        String postUrl = "https://localhost:4200/view-post/" + postId;
        String fullMessage = message + "\n" + postUrl;

        // Étape 4 : Préparer l'appel à Facebook Graph API
        String graphApiUrl = "https://graph.facebook.com/v20.0/" + pageId + "/feed";

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("message", fullMessage);
        requestBody.put("access_token", pageAccessToken);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(graphApiUrl, entity, Map.class);
            if (response.getStatusCode() != HttpStatus.OK) {
                throw new Exception("Failed to share post on Facebook: API returned status " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new Exception("Failed to share post on Facebook: " + e.getMessage(), e);
        }
    }

    public Map<String, String> getPages(String userAccessToken) throws Exception {
        String url = "https://graph.facebook.com/me/accounts?access_token=" + userAccessToken;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            List<Map<String, Object>> pages = (List<Map<String, Object>>) response.getBody().get("data");

            if (!pages.isEmpty()) {
                Map<String, Object> firstPage = pages.get(0);
                String pageId = (String) firstPage.get("id");
                String pageToken = (String) firstPage.get("access_token");

                return Map.of("pageId", pageId, "pageToken", pageToken);
            }
        }

        throw new Exception("No pages found or failed to retrieve pages");
    }
}