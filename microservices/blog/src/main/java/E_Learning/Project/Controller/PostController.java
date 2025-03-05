package E_Learning.Project.Controller;

import E_Learning.Project.Entity.Post;
import E_Learning.Project.Service.PostService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/blog/posts")
public class PostController {
    @Autowired
    private PostService postService;

<<<<<<< HEAD
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPost(@RequestParam("title") String title,
                                        @RequestParam("content") String content,
                                        @RequestParam("postedBy") String postedBy,
                                        @RequestParam("category") String category,
                                        @RequestParam(value = "imageFile", required = false) MultipartFile file) {
        try {
            Post post = new Post();
            post.setTitle(title);
            post.setContent(content);
            post.setPostedBy(postedBy);
            post.setCategory(category);
=======
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody Post post){
        try{
            Post createdPost= postService.savePost(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping
    public ResponseEntity<List<Post>> getAllposts(){
        try{
>>>>>>> origin/Training

            if (file != null && !file.isEmpty()) {
                String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
                post.setImg(base64Image);
               
            } else {
                System.out.println("No image file provided");
            }
            Post createdPost = postService.savePost(post);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la création : " + e.getMessage());
        }
    }

    @PutMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updatePost(@PathVariable Long postId,
                                        @RequestParam(value = "imageFile", required = false) MultipartFile file,
                                        @RequestParam("title") String title,
                                        @RequestParam("content") String content,
                                        @RequestParam("postedBy") String postedBy,
                                        @RequestParam("category") String category) {
        try {
            Post existingPost = postService.getPostByIdAndUpdating(postId);
            existingPost.setTitle(title);
            existingPost.setContent(content);
            existingPost.setPostedBy(postedBy);
            existingPost.setCategory(category);

            if (file != null && !file.isEmpty()) {
                String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
                existingPost.setImg(base64Image);
              
            }

            Post savedPost = postService.updatePost(postId, existingPost);
            return ResponseEntity.ok(savedPost);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Post non trouvé : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        posts.forEach(post -> {
           
        });
       
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(@PathVariable Long postId) {
        try {
            Post post = postService.getPostById(postId);

            return ResponseEntity.ok(post);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{postId}/react")
    public ResponseEntity<?> reactPost(@PathVariable Long postId, @RequestParam String reaction) {
        try {
            postService.reactPost(postId, reaction);
            return ResponseEntity.ok(new String[]{"Post reacted successfully with " + reaction});
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}