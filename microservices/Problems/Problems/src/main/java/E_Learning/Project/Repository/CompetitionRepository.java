package E_Learning.Project.Repository;

import E_Learning.Project.Entity.Competition;
import E_Learning.Project.Entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository  extends JpaRepository<Competition,Integer> {
}
