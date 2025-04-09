package tn.esprit.pidev.Service;

import tn.esprit.pidev.Entity.Evaluation;
import tn.esprit.pidev.Repository.EvaluationRepository;
import tn.esprit.pidev.dto.EvaluationDTO;

import java.util.List;
import java.util.stream.Collectors;

public interface IEvaluationService {
  Evaluation addEvaluation(EvaluationDTO evaluation);

  Evaluation updateEvaluation(int idEvaluation, Evaluation evaluation);

  void deleteEvaluation(int idEvaluation);

  Evaluation getEvaluationById(int idEvaluation);

 // EvaluationDTO getEvaluationByTrainingId(int idTraining);

  List<Evaluation> getAllEvaluations();

  List<EvaluationDTO> getEvaluationsByTrainingId(int idTraining);
}
