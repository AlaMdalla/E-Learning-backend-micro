package tn.esprit.pidev.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidev.Entity.Reponse;

import java.util.List;

public interface ReponseRepository extends JpaRepository<Reponse, Long> {

    List<Reponse> findByEvaluation_IdEvaluation(int evaluationId);  // Utiliser idEvaluation
  }








