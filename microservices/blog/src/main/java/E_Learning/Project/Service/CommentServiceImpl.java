package E_Learning.Project.Service;

import E_Learning.Project.Entity.Comment;
import E_Learning.Project.Entity.CommentInteraction;
import E_Learning.Project.Entity.Post;
import E_Learning.Project.Entity.BadWordFilter;
import E_Learning.Project.Repository.CommentRepository;
import E_Learning.Project.Repository.CommentInteractionRepository;
import E_Learning.Project.Repository.PostRepository;
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

    @Autowired
    private CommentInteractionRepository commentInteractionRepository; // Ajout du repository pour les interactions

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
            comment.setLikeCount(0); // Initialisation des compteurs
            comment.setLaught(0);
            comment.setAngry(0);


            return commentRepository.save(comment);
        }
        throw new EntityNotFoundException("Post Not Found");
    }

    @Override
    public Comment replyToComment(Long parentCommentId, Long userId, String content) {
        // Validate inputs
        if (parentCommentId == null || userId == null || content == null) {
            throw new IllegalArgumentException("Parent comment ID, user ID, and content are required");
        }
        if (content.trim().isEmpty()) {
            throw new IllegalArgumentException("Comment content cannot be empty");
        }
        if (content.length() < 3) {
            throw new IllegalArgumentException("Comment content must be at least 3 characters long");
        }

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
            reply.setLikeCount(0);
            reply.setLaught(0);
            reply.setAngry(0);

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


    public void reactComment(Long userId, Long commentId, String reactionType) {
        Optional<Comment> optionalComment = commentRepository.findById(commentId);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();

            // Vérifier si l'utilisateur a déjà réagi
            Optional<CommentInteraction> interactionOpt = commentInteractionRepository.findByCommentIdAndUserId(commentId, userId);
            CommentInteraction interaction;

            if (interactionOpt.isPresent()) {
                interaction = interactionOpt.get();
                // Si l'utilisateur a déjà réagi, lancer une exception
                if (interaction.getReactionType() != null) {
                    throw new IllegalStateException("User has already reacted to this comment");
                }
            } else {
                // Créer une nouvelle interaction si aucune n'existe
                interaction = new CommentInteraction();
                interaction.setComment(comment);
                interaction.setUserId(userId);
            }

            // Enregistrer la réaction
            switch (reactionType.toLowerCase()) {
                case "like":
                    comment.setLikeCount(comment.getLikeCount() + 1);
                    break;
                case "laugh":
                    comment.setLaught(comment.getLaught() + 1);
                    break;
                case "angry":
                    comment.setAngry(comment.getAngry() + 1);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid reaction type: " + reactionType);
            }

            interaction.setReactionType(reactionType.toLowerCase());
            commentRepository.save(comment);
            commentInteractionRepository.save(interaction);
        } else {
            throw new EntityNotFoundException("Comment Not Found");
        }
    }
}