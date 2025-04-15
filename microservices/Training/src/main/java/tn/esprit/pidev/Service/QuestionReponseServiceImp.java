package tn.esprit.pidev.Service;

import org.springframework.stereotype.Service;
import tn.esprit.pidev.Entity.Evaluation;
import tn.esprit.pidev.Entity.QuestionReponse;
import tn.esprit.pidev.Entity.Training;
import tn.esprit.pidev.Repository.EvaluationRepository;
import tn.esprit.pidev.Repository.QuestionReponseRepository;
import tn.esprit.pidev.dto.QuestionReponseDTO;

import java.util.Date;
import java.util.List;

@Service
public class QuestionReponseServiceImp implements IQuestionReponseService {

    private final QuestionReponseRepository questionReponseRepository;
    private final EvaluationRepository evaluationRepository;


    public QuestionReponseServiceImp(QuestionReponseRepository questionReponseRepository,EvaluationRepository evaluationRepository) {
        this.questionReponseRepository = questionReponseRepository;
        this.evaluationRepository = evaluationRepository;
    }

    @Override
    public QuestionReponse addQuestion(QuestionReponseDTO question) {
      QuestionReponse quest = new QuestionReponse();
      quest.setBonneReponse(question.getBonneReponse());
      quest.setQuestionText(question.getQuestionText());
      question.setOptions(question.getOptions());
      Evaluation eval=evaluationRepository.findById(question.getEvaluationId()).get();
      quest.setEvaluation(eval);
        return questionReponseRepository.save(quest);
    }

    @Override
    public QuestionReponse updateQuestion(int idQuestion, QuestionReponse question) {
        QuestionReponse existingQuestion = questionReponseRepository.findById(idQuestion).orElse(null);
        if (existingQuestion != null) {
            existingQuestion.setQuestionText(question.getQuestionText());
            existingQuestion.setType(question.getType());
            existingQuestion.setOptions(question.getOptions());
            existingQuestion.setBonneReponse(question.getBonneReponse());
            existingQuestion.setScore(question.getScore());
            return questionReponseRepository.save(existingQuestion);
        } else {
            throw new RuntimeException("Question non trouvée !");
        }
    }

    @Override
    public void deleteQuestion(int idQuestion) {
        questionReponseRepository.deleteById(idQuestion);
    }

    @Override
    public QuestionReponse getQuestionById(int idQuestion) {
        return questionReponseRepository.findById(idQuestion).orElse(null);
    }

    @Override
    public List<QuestionReponse> getAllQuestions() {
        return questionReponseRepository.findAll();
    }

    @Override
    public List<QuestionReponse> getRandomQuestions(int idEvaluation, int limit) {
      return questionReponseRepository.getRandomQuestions(idEvaluation);
       // return questionReponseRepository.findByEvaluationIdEvaluationOrderByRandomLimit(idEvaluation,5);
   }

}
