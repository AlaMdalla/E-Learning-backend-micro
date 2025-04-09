package E_Learning.Project.Service;

import E_Learning.Project.Entity.Comment;
import E_Learning.Project.Entity.Post;
import E_Learning.Project.Repository.CommentRepository;
import E_Learning.Project.Repository.PostRepository;
import E_Learning.Project.Entity.BadWordFilter; // Import the utility class
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    public Comment createComment(Long postId, String content) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            // Check for bad words
            if (BadWordFilter.containsBadWords(content)) {
                throw new IllegalArgumentException("Comment contains inappropriate language. Please revise your content.");
            }

            Comment comment = new Comment();
            comment.setPost(optionalPost.get());
            comment.setContent(content); // Content is clean, no censoring in this case

            comment.setCreatedAt(new Date());

            return commentRepository.save(comment);
        }
        throw new EntityNotFoundException("Post Not Found");
    }

    public Comment replyToComment(Long parentCommentId, String content) {
        Optional<Comment> optionalParentComment = commentRepository.findById(parentCommentId);
        if (optionalParentComment.isPresent()) {
            // Check for bad words
            if (BadWordFilter.containsBadWords(content)) {
                throw new IllegalArgumentException("Reply contains inappropriate language. Please revise your content.");
            }

            Comment parentComment = optionalParentComment.get();
            Comment reply = new Comment();
            reply.setParentComment(parentComment);
            reply.setPost(parentComment.getPost());
            reply.setContent(content); // Content is clean
            reply.setCreatedAt(new Date());

            Comment savedReply = commentRepository.save(reply);
            parentComment.getReplies().add(savedReply);
            commentRepository.save(parentComment);

            return savedReply;
        }
        throw new EntityNotFoundException("Parent Comment Not Found");
    }

    public List<Comment> getCommentByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}