package tn.esprit.pidev.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.pidev.Entity.Training;
import tn.esprit.pidev.Service.EvaluationServiceImp;
import tn.esprit.pidev.Service.TrainingServiceImp;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/trainings")
public class TrainingController {
        private final TrainingServiceImp trainingServiceImp;
  private final EvaluationServiceImp evaluationServiceImp;

  public TrainingController(TrainingServiceImp trainingServiceImp, EvaluationServiceImp evaluationServiceImp) {
            this.trainingServiceImp = trainingServiceImp;
    this.evaluationServiceImp = evaluationServiceImp;
  }

        @GetMapping("/retrieve-all-trainings")
        public List<Training> getAllTrainings() {
            return trainingServiceImp.getAllTrainings();
        }

        @GetMapping("/{idTraining}")
        public Training getTrainingById(@PathVariable int idTraining) {
            return trainingServiceImp.getTrainingById(idTraining);
        }

    @PostMapping("/add-training")
    public ResponseEntity<Training> addTraining(@RequestBody Training training) {
        Training createdTraining = trainingServiceImp.saveTraining(training);
        return new ResponseEntity<>(createdTraining, HttpStatus.CREATED);
    }

    @PutMapping("/modify-training")
    public Training modifyTraining(@RequestBody Training training) {
        return trainingServiceImp.saveTraining(training);
    }

    // DELETE training
    @DeleteMapping("/remove-training/{training-id}")
    public void removeTraining(@PathVariable("training-id") int id) {
        trainingServiceImp.deleteTraining(id);
    }

  @DeleteMapping("/trainings/{training-id}/evaluations")
  public ResponseEntity<Void> deleteEvaluationsByTraining(@PathVariable("training-id") int trainingId) {
    evaluationServiceImp.deleteEvaluationsByTrainingId(trainingId);
    return ResponseEntity.noContent().build();
  }
     }

