package E_Learning.Project.Controller;

import E_Learning.Project.Entity.Comment;
import E_Learning.Project.Service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<?> createComment(
            @RequestHeader("User-Id") Long userId,
            @RequestParam Long postId,
            @RequestParam String content,
            @RequestParam(required = false) Long parentCommentId) {
        try {
            Comment comment;
            if (parentCommentId != null) {
                comment = commentService.replyToComment(parentCommentId, userId, content);
            } else {
                comment = commentService.createComment(postId, userId, content);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(comment);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la création du commentaire : " + e.getMessage());
        }
    }
    @GetMapping("/post/{postId}")
    public ResponseEntity<List<Comment>> getCommentsByPostId(@PathVariable Long postId) {
        List<Comment> comments = commentService.getCommentByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @PutMapping("/{commentId}/react")
    public ResponseEntity<?> reactComment(
            @RequestHeader("User-Id") Long userId,
            @PathVariable Long commentId,
            @RequestParam String reaction) {
        try {
            commentService.reactComment(userId, commentId, reaction);
            return ResponseEntity.ok(new String[]{"Comment reacted successfully with " + reaction});
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @PostMapping("/reply")
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