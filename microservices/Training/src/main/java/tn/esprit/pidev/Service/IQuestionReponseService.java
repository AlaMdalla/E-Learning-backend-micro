package tn.esprit.pidev.Service;

import tn.esprit.pidev.Entity.QuestionReponse;
import tn.esprit.pidev.dto.QuestionReponseDTO;

import java.util.List;

public interface IQuestionReponseService {
    QuestionReponse addQuestion(QuestionReponseDTO question);
    QuestionReponse updateQuestion(int idQuestion, QuestionReponse question);
    void deleteQuestion(int idQuestion);
    QuestionReponse getQuestionById(int idQuestion);
    List<QuestionReponse> getAllQuestions();

    List<QuestionReponse> getRandomQuestions(int idEvaluation, int limit);
}
