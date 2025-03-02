package tn.esprit.pidev.Service;

import tn.esprit.pidev.Entity.Reponse;

import java.util.List;

public interface IReponseService {
  // Ajouter une réponse et calculer le score
  Reponse addReponse(Reponse reponse);

  // Calculer le score d'une réponse en fonction de sa validité
  double calculerScore(Reponse reponse);

  // Récupérer toutes les réponses pour une évaluation spécifique
  List<Reponse> getReponsesByEvaluationId(int idEvaluation);

  // Certifier la réponse en fonction de sa validité
  String certifierReponse(Reponse reponse);
}


