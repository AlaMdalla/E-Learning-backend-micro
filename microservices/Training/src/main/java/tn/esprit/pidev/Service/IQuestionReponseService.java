package tn.esprit.pidev.Service;

import tn.esprit.pidev.Entity.QuestionReponse;

import java.util.List;

public interface IQuestionReponseService {
    QuestionReponse addQuestion(QuestionReponse question);
    QuestionReponse updateQuestion(int idQuestion, QuestionReponse question);
    void deleteQuestion(int idQuestion);
    QuestionReponse getQuestionById(int idQuestion);
    List<QuestionReponse> getAllQuestions();

    List<QuestionReponse> getRandomQuestions(int idEvaluation);
}
