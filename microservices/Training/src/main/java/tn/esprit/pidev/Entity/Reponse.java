package tn.esprit.pidev.Entity;

import jakarta.persistence.*;

@Entity
public class Reponse {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idReponse;

  @ManyToOne
  private Utilisateur utilisateur;

  @ManyToOne
  private Evaluation evaluation;

  @ManyToOne
  private Question question;


  private String reponse;


  private Boolean estCorrect;


  public Long getIdReponse() {
    return idReponse;
  }

  public void setIdReponse(Long idReponse) {
    this.idReponse = idReponse;
  }

  public Utilisateur getUtilisateur() {
    return utilisateur;
  }

  public void setUtilisateur(Utilisateur utilisateur) {
    this.utilisateur = utilisateur;
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
}
