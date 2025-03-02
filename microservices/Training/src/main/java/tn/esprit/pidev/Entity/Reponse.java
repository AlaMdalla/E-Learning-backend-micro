package tn.esprit.pidev.Entity;

import jakarta.persistence.*;

@Entity
public class Reponse {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int idReponse;

  @ManyToOne
  private Evaluation evaluation;

  @ManyToOne
  private Question question;


  private String reponse;
  //private String status;

  private Boolean estCorrect;

  private double scoreObtenu;

  public int getIdReponse() {
    return idReponse;
  }

  public void setIdReponse(int idReponse) {
    this.idReponse = idReponse;
  }

  public Evaluation getEvaluation() {
    return evaluation;
  }

  public void setEvaluation(Evaluation evaluation) {
    this.evaluation = evaluation;
  }

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }

  public String getReponse() {
    return reponse;
  }

  public void setReponse(String reponse) {
    this.reponse = reponse;
  }



  public Boolean getEstCorrect() {
    return estCorrect;
  }

  public void setEstCorrect(Boolean estCorrect) {
    this.estCorrect = estCorrect;
  }

  public double getScoreObtenu() {
    return scoreObtenu;
  }

  public void setScoreObtenu(double scoreObtenu) {
    this.scoreObtenu = scoreObtenu;
  }
}
