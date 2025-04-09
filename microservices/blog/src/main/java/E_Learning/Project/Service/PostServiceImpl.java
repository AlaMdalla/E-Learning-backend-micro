package E_Learning.Project.Service;

import E_Learning.Project.Entity.Post;
import E_Learning.Project.Entity.PostInteraction;
import E_Learning.Project.Repository.PostRepository;
import E_Learning.Project.Repository.PostInteractionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostInteractionRepository postInteractionRepository;

    public Post savePost(Integer userId, Post post) {
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

        // Mise à jour des champs uniquement si non null
        Optional.ofNullable(updatedPost.getTitle()).ifPresent(existingPost::setTitle);
        Optional.ofNullable(updatedPost.getContent()).ifPresent(existingPost::setContent);
        Optional.ofNullable(updatedPost.getImg()).ifPresent(existingPost::setImg);
        Optional.ofNullable(updatedPost.getCategory()).ifPresent(existingPost::setCategory);

        return postRepository.save(existingPost);
    }
}