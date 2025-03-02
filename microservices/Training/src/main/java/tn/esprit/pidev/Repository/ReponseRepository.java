package tn.esprit.pidev.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.pidev.Entity.Reponse;

import java.util.List;

public interface ReponseRepository extends JpaRepository<Reponse, Long> {
  // Recherche des réponses d'un utilisateur donné
  List<Reponse> findByUtilisateurIdUtilisateur(Long idUtilisateur);

  // Recherche des réponses d'une évaluation donnée
  List<Reponse> findByEvaluationIdEvaluation(Long idEvaluation);
}
