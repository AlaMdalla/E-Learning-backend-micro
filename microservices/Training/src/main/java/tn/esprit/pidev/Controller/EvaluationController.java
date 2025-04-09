package tn.esprit.pidev.Controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.Entity.Evaluation;
import tn.esprit.pidev.Service.IEvaluationService;
import tn.esprit.pidev.dto.EvaluationDTO;

import java.util.List;

@RestController
@RequestMapping("/trainings/evaluation")
public class EvaluationController {

    private final IEvaluationService evaluationService;

    public EvaluationController(IEvaluationService evaluationService) {
        this.evaluationService = evaluationService;
    }

    // http://localhost:8089/e-learning/evaluation/retrieve-all
    @GetMapping("/retrieve-all")
    public ResponseEntity<List<Evaluation>> getAllEvaluations() {
        return new ResponseEntity<>(evaluationService.getAllEvaluations(), HttpStatus.OK);
    }
    // http://localhost:8089/e-learning/evaluation/retrieve/{idEvaluation}
    @GetMapping("/retrieve/{idEvaluation}")
    public ResponseEntity<Evaluation> getEvaluationById(@PathVariable("idEvaluation") int idEvaluation) {
        Evaluation evaluation = evaluationService.getEvaluationById(idEvaluation);
        if (evaluation != null) {
            return new ResponseEntity<>(evaluation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
  // http://localhost:8089/e-learning/evaluation/{idTraining}
  @GetMapping("/{idTraining}")
  public ResponseEntity<List<EvaluationDTO>> getEvaluationsByTrainingId(@PathVariable("idTraining") int idTraining) {
    List<EvaluationDTO> evaluations = evaluationService.getEvaluationsByTrainingId(idTraining);
    System.out.println("Évaluations envoyées au frontend : " + evaluations);
    return new ResponseEntity<>(evaluations, HttpStatus.OK);
  }

  // http://localhost:8089/e-learning/evaluation/add
    @PostMapping("/add")
    public ResponseEntity<Evaluation> addEvaluation(@RequestBody EvaluationDTO evaluation) {
        return new ResponseEntity<>(evaluationService.addEvaluation(evaluation), HttpStatus.CREATED);
    }


    // http://localhost:8089/e-learning/evaluation/update/idEvaluation
    @PutMapping("/update/{idEvaluation}")
    public ResponseEntity<Evaluation> updateEvaluation(@PathVariable("idEvaluation") int idEvaluation, @RequestBody Evaluation evaluation) {
        Evaluation updatedEvaluation = evaluationService.updateEvaluation(idEvaluation, evaluation);
        if (updatedEvaluation != null) {
            return new ResponseEntity<>(updatedEvaluation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/delete/{idEvaluation}")
    public ResponseEntity<Void> deleteEvaluation(@PathVariable("idEvaluation") int idEvaluation) {
        evaluationService.deleteEvaluation(idEvaluation);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
