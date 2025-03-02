package tn.esprit.pidev.Controller;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.Entity.Reponse;
import tn.esprit.pidev.Service.IReponseService;

import java.util.List;

@Transactional
@RestController
@RequestMapping("/reponse")
public class ReponseController {

  private final IReponseService reponseService;

  public ReponseController(IReponseService reponseService) {
    this.reponseService = reponseService;
  }

  // Ajouter une nouvelle réponse
  @PostMapping("/add")
  public ResponseEntity<Reponse> addReponse(@RequestBody Reponse reponse) {
    Reponse createdReponse = reponseService.addReponse(reponse);
    return new ResponseEntity<>(createdReponse, HttpStatus.CREATED);
  }

  // Récupérer toutes les réponses pour une évaluation spécifique
  @GetMapping("/get/{idEvaluation}")
  public ResponseEntity<List<Reponse>> getReponsesByEvaluationId(@PathVariable int idEvaluation) {
    List<Reponse> reponses = reponseService.getReponsesByEvaluationId(idEvaluation);
    return new ResponseEntity<>(reponses, HttpStatus.OK);
  }

  // Certifier une réponse
  @GetMapping("/certifier/{idReponse}")
  public ResponseEntity<String> certifierReponse(@PathVariable int idReponse) {
    Reponse reponse = reponseService.getReponsesByEvaluationId(idReponse).get(0);  // Juste pour illustrer, ajustez selon votre logique
    String certificationStatus = reponseService.certifierReponse(reponse);
    return new ResponseEntity<>(certificationStatus, HttpStatus.OK);
  }
}
