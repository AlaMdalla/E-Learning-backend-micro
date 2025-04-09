package tn.esprit.pidev.Service;

import org.springframework.stereotype.Service;
import tn.esprit.pidev.Entity.Evaluation;
import tn.esprit.pidev.Entity.QuestionReponse;
import tn.esprit.pidev.Entity.QuestionType;
import tn.esprit.pidev.Entity.Training;
import tn.esprit.pidev.Repository.EvaluationRepository;
import tn.esprit.pidev.Repository.QuestionReponseRepository;
import tn.esprit.pidev.Repository.TrainingRepository;
import tn.esprit.pidev.dto.EvaluationDTO;
import tn.esprit.pidev.dto.QuestionReponseDTO;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EvaluationServiceImp implements IEvaluationService{

    private final EvaluationRepository evaluationRepository;
      private final TrainingRepository trainingRepository;
    private final QuestionReponseRepository questionReponseRepository;

    public EvaluationServiceImp(EvaluationRepository evaluationRepository, TrainingRepository trainingRepository,QuestionReponseRepository questionReponseRepository) {
        this.evaluationRepository = evaluationRepository;
      this.trainingRepository = trainingRepository;
      this.questionReponseRepository = questionReponseRepository;
    }

    @Override
    public Evaluation addEvaluation(EvaluationDTO eval) {
          Evaluation evaluation = new Evaluation();
          evaluation.setType(eval.getType());
          evaluation.setDescription(eval.getDescription());
          evaluation.setScore(eval.getScore());
          evaluation.setEvaluation_duration(eval.getEvaluationDuration());
          evaluation.setCreatedAt(new Date() );
          Training training=trainingRepository.findById(eval.getTrainingId()).get();
          evaluation.setTraining(training);
          evaluationRepository.save(evaluation);
          Set<QuestionReponseDTO>  questions = eval.getQuestions();
          for(QuestionReponseDTO question:questions) {
            QuestionReponse quest = new QuestionReponse();
            quest.setBonneReponse(question.getBonneReponse());
            quest.setQuestionText(question.getQuestionText());
            quest.setOptions(question.getOptions());
            quest.setEvaluation(evaluation);
            quest.setType(QuestionType.QCM);
            questionReponseRepository.save(quest);
          }
        return evaluation;
    }

    @Override
    public Evaluation updateEvaluation(int idEvaluation, Evaluation evaluation) {
        return evaluationRepository.save(evaluation);
    }

    @Override
    public void deleteEvaluation(int idEvaluation) {
        evaluationRepository.deleteById(idEvaluation);

    }

    @Override
    public Evaluation getEvaluationById(int idEvaluation) {
        return evaluationRepository.findById(idEvaluation).orElse(null);
    }


  //  @Override
//  public EvaluationDTO getEvaluationByTrainingId(int idTraining) {
//    Training training = trainingRepository.findById(idTraining).orElse(null);
//    if (training == null || training.getEvaluations().isEmpty()) {
//      return null;
//    }
//    return new EvaluationDTO(training.getEvaluations().get(0));
//  }
    @Override
    public List<Evaluation> getAllEvaluations() {
        return evaluationRepository.findAll();
    }

  @Override
  public List<EvaluationDTO> getEvaluationsByTrainingId(int idTraining) {
    List<Evaluation> evaluations = evaluationRepository.findByTraining_IdTraining(idTraining);
    return evaluations.stream()
      .map(EvaluationDTO::new)
      .collect(Collectors.toList());
  }


  public void deleteEvaluationsByTrainingId(int trainingId) {
    evaluationRepository.deleteByTrainingIdTraining(trainingId);
  }
}
