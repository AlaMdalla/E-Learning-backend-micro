package tn.esprit.pidev.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.pidev.Entity.Training;
import tn.esprit.pidev.Repository.EvaluationRepository;
import tn.esprit.pidev.Repository.TrainingRepository;
import tn.esprit.pidev.dto.TrainingDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainingServiceImp implements ITrainingService
{
    private final TrainingRepository trainingRepository;
  private final EvaluationRepository evaluationRepository;

  public TrainingServiceImp(TrainingRepository trainingRepository, EvaluationRepository evaluationRepository) {
        this.trainingRepository = trainingRepository;
    this.evaluationRepository = evaluationRepository;
  }


    @Override
    public List<TrainingDTO> getAllTrainings() {
      List<Training> trainings = trainingRepository.findAll();
      return trainings.stream().map(TrainingDTO::new).collect(Collectors.toList());

    }

    @Override
    public Training getTrainingById(int idTraining) {
        return trainingRepository.findById(idTraining).orElse(null);
    }

    @Override
    public Training saveTraining(Training training) {
        return trainingRepository.save(training);
    }

    @Override
    public void deleteTraining(int idTraining) {
      evaluationRepository.deleteById(idTraining);
      trainingRepository.deleteById(idTraining);
    }


  }




