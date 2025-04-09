package tn.esprit.pidev.Service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.esprit.pidev.Entity.QuestionReponse;
import tn.esprit.pidev.Repository.QuestionReponseRepository;

import java.util.List;

@Service
public class QuestionReponseServiceImp implements IQuestionReponseService {

    private final QuestionReponseRepository questionReponseRepository;
    

    public QuestionReponseServiceImp(QuestionReponseRepository questionReponseRepository) {
        this.questionReponseRepository = questionReponseRepository;
    }

    @Override
    public QuestionReponse addQuestion(QuestionReponse question) {
        return questionReponseRepository.save(question);
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
    public List<QuestionReponse> getRandomQuestions(int idEvaluation) {
        return questionReponseRepository.getRandomQuestions(idEvaluation);
    }

}
