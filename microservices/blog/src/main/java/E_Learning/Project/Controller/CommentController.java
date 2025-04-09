package E_Learning.Project.Controller;

import E_Learning.Project.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/blog/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("comments/create")
    public ResponseEntity<?> createComment(
            @RequestParam Long postId,
            @RequestParam Long userId, // Add userId parameter
            @RequestParam String content) {
        try {
            return ResponseEntity.ok(commentService.createComment(postId, userId, content));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }

    @GetMapping("comments/{postId}")
    public ResponseEntity<?> getCommentByPostId(@PathVariable Long postId) {
        try {
            return ResponseEntity.ok(commentService.getCommentByPostId(postId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something Wrong");
        }
    }

    @PostMapping("comments/reply")
    public ResponseEntity<?> replyToComment(
            @RequestParam Long parentCommentId,
            @RequestParam Long userId, // Add userId parameter
            @RequestParam String content) {
        try {
            return ResponseEntity.ok(commentService.replyToComment(parentCommentId, userId, content));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(e.getMessage());
        }
    }
}