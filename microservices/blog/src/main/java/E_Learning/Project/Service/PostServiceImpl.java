package E_Learning.Project.Service;

import E_Learning.Project.Entity.Post;
import E_Learning.Project.Repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class PostServiceImpl implements PostService{

    @Autowired
    private PostRepository postRepository;


    public Post savePost(Post post) {
        post.setLikeCount(0);
        post.setLaught(0);  // Initialisation
        post.setAngry(0);   // Initialisation
        post.setViewCount(0);
        post.setDate(new Date());
        return postRepository.save(post);
    }

    public List<Post> getAllPosts(){
        return postRepository.findAll();
    }
    public Post getPostById(Long postId){
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()){
            Post post = optionalPost.get();

            post.setViewCount(post.getViewCount() + 1);
            return postRepository.save(post);
        }else {
            throw new EntityNotFoundException("Post not Found");
        }
    }
    public Post getPostByIdAndUpdating(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post not Found"));
    }

    public void deletePost(Long postId) {
        if (!postRepository.existsById(postId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with ID " + postId + " not found");
        }
        postRepository.deleteById(postId);
    }
    public Post updatePost(Long postId, Post updatedPost) {
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("Post non trouvé avec l'ID : " + postId));

        // Mise à jour des champs uniquement si non null
        Optional.ofNullable(updatedPost.getTitle()).ifPresent(existingPost::setTitle);
        Optional.ofNullable(updatedPost.getContent()).ifPresent(existingPost::setContent);
        Optional.ofNullable(updatedPost.getPostedBy()).ifPresent(existingPost::setPostedBy);
        Optional.ofNullable(updatedPost.getImg()).ifPresent(existingPost::setImg);
        Optional.ofNullable(updatedPost.getCategory()).ifPresent(existingPost::setCategory);

        return postRepository.save(existingPost);
    }




    public void reactPost(Long postId, String reactionType) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
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
            postRepository.save(post);
        } else {
            throw new EntityNotFoundException("Post Not Found");
        }
    }

}
