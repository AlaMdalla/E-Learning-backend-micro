package E_Learning.Project.Repository;

import E_Learning.Project.Entity.PostInteraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostInteractionRepository extends JpaRepository<PostInteraction, Long> {
    Optional<PostInteraction> findByPostIdAndUserId(Long postId, Integer userId);
}
