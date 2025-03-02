package tn.esprit.pidev.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.pidev.Entity.Reponse;
import tn.esprit.pidev.Repository.ReponseRepository;

import java.util.List;
@Service
public class ReponseServiceImp implements IReponseService
{
  @Autowired
  private ReponseRepository reponseRepository;

  @Override
  public Reponse ajouterReponse(Reponse reponse) {
    return reponseRepository.save(reponse);
  }

  @Override
  public Reponse mettreAJourReponse(Long idReponse, Reponse reponse) {
    if (reponseRepository.existsById(idReponse)) {
      reponse.setIdReponse(idReponse);
      return reponseRepository.save(reponse);
    }
    return null;
  }

  @Override
  public void supprimerReponse(Long idReponse) {
    reponseRepository.deleteById(idReponse);
  }

  @Override
  public List<Reponse> recupererToutesLesReponses() {
    return reponseRepository.findAll();
  }

  @Override
  public Reponse recupererReponseParId(Long idReponse) {
    return reponseRepository.findById(idReponse).orElse(null);
  }

  @Override
  public List<Reponse> recupererReponsesParUtilisateur(Long idUtilisateur) {
    return reponseRepository.findByUtilisateurIdUtilisateur(idUtilisateur);
  }

  @Override
  public List<Reponse> recupererReponsesParEvaluation(Long idEvaluation) {
    return reponseRepository.findByEvaluationIdEvaluation(idEvaluation);  }}
