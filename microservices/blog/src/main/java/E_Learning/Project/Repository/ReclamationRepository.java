package E_Learning.Project.Repository;

import E_Learning.Project.Entity.Reclamation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {
    List<Reclamation> findByPostId(Long postId);
}
