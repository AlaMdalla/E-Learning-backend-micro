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

    @PostMapping(value = "/create/{User-Id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createPost(
            @PathVariable("User-Id") Integer userId,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("category") String category,
            @RequestParam(value = "imageFile", required = false) MultipartFile file) {
        try {
            Post post = new Post();
            post.setTitle(title);
            post.setContent(content);
            post.setCategory(category);

            if (file != null && !file.isEmpty()) {
                String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
                post.setImg(base64Image);
            } else {
                System.out.println("No image file provided");
            }

            Post createdPost = postService.savePost(userId, post);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la création : " + e.getMessage());
        }
    }

    @PutMapping(value = "/{postId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updatePost(
            @RequestHeader("User-Id") Integer userId,
            @PathVariable Long postId,
            @RequestParam(value = "imageFile", required = false) MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("category") String category) {
        try {
            Post updatedPost = new Post();
            updatedPost.setTitle(title);
            updatedPost.setContent(content);
            updatedPost.setCategory(category);

            if (file != null && !file.isEmpty()) {
                String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
                updatedPost.setImg(base64Image);
            }

            Post savedPost = postService.updatePost(userId, postId, updatedPost);
            return ResponseEntity.ok(savedPost);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Post non trouvé : " + e.getMessage());
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Non autorisé : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Post>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<?> getPostById(
            @RequestHeader("User-Id") Integer userId,
            @PathVariable Long postId) {
        try {
            Post post = postService.getPostById(userId, postId);
            return ResponseEntity.ok(post);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Non autorisé : " + e.getMessage());
        }
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @RequestHeader("User-Id") Integer userId,
            @PathVariable Long postId) {
        try {
            postService.deletePost(userId, postId);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/{postId}/view")
    public ResponseEntity<?> viewPost(
            @RequestHeader("User-Id") Integer userId,
            @PathVariable Long postId) {
        try {
            postService.viewPost(userId, postId);
            return ResponseEntity.ok(new String[]{"Post view recorded successfully"});
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/{postId}/react")
    public ResponseEntity<?> reactPost(
            @RequestHeader("User-Id") Integer userId,
            @PathVariable Long postId,
            @RequestParam String reaction) {
        try {
            postService.reactPost(userId, postId, reaction);
            return ResponseEntity.ok(new String[]{"Post reacted successfully with " + reaction});
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}