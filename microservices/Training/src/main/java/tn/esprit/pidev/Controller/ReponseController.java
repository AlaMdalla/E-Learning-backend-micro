package tn.esprit.pidev.Controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.Entity.Reponse;
import tn.esprit.pidev.Service.IReponseService;

import java.util.List;

@Transactional
  @RestController
@RequestMapping("/api/reponses")
public class ReponseController {

  @Autowired
  private IReponseService reponseService;


  @PostMapping("/create")
  public Reponse createReponse(@RequestBody Reponse reponse) {
    return reponseService.ajouterReponse(reponse);
  }


  @GetMapping("/all")
  public List<Reponse> getAllReponses() {
    return reponseService.recupererToutesLesReponses();
  }

  @GetMapping("/utilisateur/{idUtilisateur}")
  public List<Reponse> getReponsesByUtilisateur(@PathVariable Long idUtilisateur) {
    return reponseService.recupererReponsesParUtilisateur(idUtilisateur);
  }

  @GetMapping("/evaluation/{idEvaluation}")
  public List<Reponse> getReponsesByEvaluation(@PathVariable Long idEvaluation) {
    return reponseService.recupererReponsesParEvaluation(idEvaluation);
  }

  @DeleteMapping("/delete/{idReponse}")
  public void deleteReponse(@PathVariable Long idReponse) {
    reponseService.supprimerReponse(idReponse);
  }
}

