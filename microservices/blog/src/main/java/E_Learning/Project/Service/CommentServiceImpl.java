package E_Learning.Project.Service;

import E_Learning.Project.Entity.Comment;
import E_Learning.Project.Entity.Post;
import E_Learning.Project.Repository.CommentRepository;
import E_Learning.Project.Repository.PostRepository;
import E_Learning.Project.Entity.BadWordFilter;
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

    @Override
    public Comment createComment(Long postId, Long userId, String content) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (optionalPost.isPresent()) {
            // Check for duplicate comment (same user, same post, same content, within 1 minute)
            List<Comment> recentComments = commentRepository.findByPostIdAndUserId(postId, userId);
            for (Comment recentComment : recentComments) {
                if (recentComment.getContent().equals(content)) {
                    long timeDiff = new Date().getTime() - recentComment.getCreatedAt().getTime();
                    if (timeDiff < 60_000) { // 1 minute in milliseconds
                        throw new IllegalArgumentException("You have already posted this comment recently.");
                    }
                }
            }

            if (BadWordFilter.containsBadWords(content)) {
                throw new IllegalArgumentException("Comment contains inappropriate language. Please revise your content.");
            }

            Comment comment = new Comment();
            comment.setPost(optionalPost.get());
            comment.setUserId(userId);
            comment.setContent(content);
            comment.setCreatedAt(new Date());

            return commentRepository.save(comment);
        }
        throw new EntityNotFoundException("Post Not Found");
    }

    @Override
    public Comment replyToComment(Long parentCommentId, Long userId, String content) {
        Optional<Comment> optionalParentComment = commentRepository.findById(parentCommentId);
        if (optionalParentComment.isPresent()) {
            Comment parentComment = optionalParentComment.get();

            // Check if the user is trying to reply to their own comment
            if (parentComment.getUserId().equals(userId)) {
                throw new IllegalArgumentException("You cannot reply to your own comment.");
            }

            if (BadWordFilter.containsBadWords(content)) {
                throw new IllegalArgumentException("Reply contains inappropriate language. Please revise your content.");
            }

            Comment reply = new Comment();
            reply.setParentComment(parentComment);
            reply.setPost(parentComment.getPost());
            reply.setUserId(userId);
            reply.setContent(content);
            reply.setCreatedAt(new Date());

            Comment savedReply = commentRepository.save(reply);
            parentComment.getReplies().add(savedReply);
            commentRepository.save(parentComment);

            return savedReply;
        }
        throw new EntityNotFoundException("Parent Comment Not Found");
    }

    @Override
    public List<Comment> getCommentByPostId(Long postId) {
        return commentRepository.findByPostId(postId);
    }
}