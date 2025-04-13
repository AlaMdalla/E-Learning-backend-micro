package E_Learning.Project.Repository;

import E_Learning.Project.Entity.CommentInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentInteractionRepository extends JpaRepository<CommentInteraction, Long> {
    Optional<CommentInteraction> findByCommentIdAndUserId(Long commentId, Long userId);
}
