package tn.esprit.pidev.Service;

import tn.esprit.pidev.Entity.Reponse;

import java.util.List;

public interface IReponseService {
  Reponse ajouterReponse(Reponse reponse);

  Reponse mettreAJourReponse(Long idReponse, Reponse reponse);

  void supprimerReponse(Long idReponse);

  List<Reponse> recupererToutesLesReponses();

  Reponse recupererReponseParId(Long idReponse);

  List<Reponse> recupererReponsesParUtilisateur(Long idUtilisateur);

  List<Reponse> recupererReponsesParEvaluation(Long idEvaluation);
}
