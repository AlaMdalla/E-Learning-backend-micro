package tn.esprit.pidev.dto;

import tn.esprit.pidev.Entity.QuestionReponse;

import java.util.List;
import java.util.Set;

public class QuestionReponseDTO {
  private int idQuestion;
  private String questionText;
  private String type;
  private List<String> options;
  private String bonneReponse;
  private double score;
  private int evaluationId;

  // Constructor
  public QuestionReponseDTO(QuestionReponse questionReponse) {
    this.idQuestion = questionReponse.getIdQuestion();
    this.questionText = questionReponse.getQuestionText();
    this.type = questionReponse.getType().name(); // Convert Enum to String
    this.options = questionReponse.getOptions();
    this.bonneReponse = questionReponse.getBonneReponse();
    this.score = questionReponse.getScore();

    if (questionReponse.getEvaluation() != null) {
      this.evaluationId = questionReponse.getEvaluation().getIdEvaluation();
    }
  }


  // ✅ Constructeur par défaut (obligatoire pour Jackson)
  public QuestionReponseDTO() {}

  // ✅ Constructeur avec paramètres (si nécessaire)
  public QuestionReponseDTO(String questionText, String bonneReponse, int evaluationId,
                            List<String> options) {
    this.questionText = questionText;
    this.bonneReponse = bonneReponse;
    //this.type = type;
    this.evaluationId = evaluationId;
    //this.score = score;
    this.options = options;
  }
  // Getters and Setters
  public int getIdQuestion() {
    return idQuestion;
  }

  public void setIdQuestion(int idQuestion) {
    this.idQuestion = idQuestion;
  }

  public String getQuestionText() {
    return questionText;
  }

  public void setQuestionText(String questionText) {
    this.questionText = questionText;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public List<String> getOptions() {
    return options;
  }

  public void setOptions(List<String> options) {
    this.options = options;
  }

  public String getBonneReponse() {
    return bonneReponse;
  }

  public void setBonneReponse(String bonneReponse) {
    this.bonneReponse = bonneReponse;
  }

  public double getScore() {
    return score;
  }

  public void setScore(double score) {
    this.score = score;
  }

  public int getEvaluationId() {
    return evaluationId;
  }

  public void setEvaluationId(int evaluationId) {
    this.evaluationId = evaluationId;
  }
}
