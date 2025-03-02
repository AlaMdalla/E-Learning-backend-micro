package tn.esprit.pidev.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.Entity.Evaluation;
import tn.esprit.pidev.Entity.Question;
import tn.esprit.pidev.Entity.Reponse;
import tn.esprit.pidev.Repository.EvaluationRepository;
import tn.esprit.pidev.Repository.QuestionRepository;
import tn.esprit.pidev.Repository.ReponseRepository;

import java.util.List;

@Service
public class ReponseServiceImp implements IReponseService {

  @Autowired
  private ReponseRepository reponseRepository;

  // Ajouter une réponse et calculer le score
  @Override
  public Reponse addReponse(Reponse reponse) {
    // Calcul du score de la réponse en fonction de la validité
    double score = calculerScore(reponse);
    reponse.setScoreObtenu(score);

    // Sauvegarder la réponse dans la base de données
    return reponseRepository.save(reponse);
  }

  // Calculer le score d'une réponse
  @Override
  public double calculerScore(Reponse reponse) {
    // Si la réponse est correcte, le score est maximal (par exemple, 10 points)
    if (reponse.getEstCorrect() != null && reponse.getEstCorrect()) {
      return 10; // Score maximal pour une réponse correcte
    } else {
      return 0; // Score de 0 pour une réponse incorrecte
    }
  }

  // Récupérer toutes les réponses pour une évaluation spécifique
  @Override
  public List<Reponse> getReponsesByEvaluationId(int idEvaluation) {
    return reponseRepository.findByEvaluation_IdEvaluation(idEvaluation);
  }

  // Certifier la réponse en fonction de sa validité
  @Override
  public String certifierReponse(Reponse reponse) {
    if (reponse.getEstCorrect() != null && reponse.getEstCorrect()) {
      return "Réponse correcte, certifiée";
    } else {
      return "Réponse incorrecte, non certifiée";
    }
  }
}
