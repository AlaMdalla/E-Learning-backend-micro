package tn.esprit.pidev.dto;

import tn.esprit.pidev.Entity.Evaluation;
import tn.esprit.pidev.Entity.Niveau;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class EvaluationDTO {
  private int idEvaluation;
  private String description;
  private String type;
  private String evaluationDuration; // Renamed to match Java naming conventions
  private double score;
  private Date createdAt;
  private boolean certificat;
  private int trainingId; // Store only the training ID to prevent recursion
  private Set<QuestionReponseDTO> questions; // Use DTO for questions
  private Niveau niveau;
  // Constructor
  public EvaluationDTO(Evaluation evaluation) {
    this.idEvaluation = evaluation.getIdEvaluation();
    this.description = evaluation.getDescription();
    this.type = evaluation.getType();
    this.evaluationDuration = evaluation.getEvaluation_duration();
    this.score = evaluation.getScore();
    this.createdAt = evaluation.getCreatedAt();
    this.certificat = evaluation.isCertificat();
    this.trainingId = evaluation.getTraining().getIdTraining(); // Store only the ID
    this.niveau = evaluation.getNiveau();
    // Convert QuestionReponse entities to DTOs (to avoid recursion)
    if (evaluation.getQuestions() != null) {
      this.questions = evaluation.getQuestions().stream()
        .map(QuestionReponseDTO::new)
        .collect(Collectors.toSet());
    }
  }
  // ✅ Constructeur par défaut (obligatoire pour Jackson)
  public EvaluationDTO() {}

  // ✅ Constructeur avec paramètres (si nécessaire)
  public EvaluationDTO(int trainingId, String description, String type, String evaluationDuration,
                       double score, Set<QuestionReponseDTO> questions, Niveau niveau) {
    this.trainingId = trainingId;
    this.description = description;
    this.type = type;
    this.evaluationDuration = evaluationDuration;
    this.score = score;
    this.questions = questions;

  }

  // Getters and Setters
  public int getIdEvaluation() {
    return idEvaluation;
  }

  public void setIdEvaluation(int idEvaluation) {
    this.idEvaluation = idEvaluation;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getEvaluationDuration() {
    return evaluationDuration;
  }

  public void setEvaluationDuration(String evaluationDuration) {
    this.evaluationDuration = evaluationDuration;
  }

  public double getScore() {
    return score;
  }

  public void setScore(double score) {
    this.score = score;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public boolean isCertificat() {
    return certificat;
  }

  public void setCertificat(boolean certificat) {
    this.certificat = certificat;
  }

  public int getTrainingId() {
    return trainingId;
  }

  public void setTrainingId(int trainingId) {
    this.trainingId = trainingId;
  }

  public Set<QuestionReponseDTO> getQuestions() {
    return questions;
  }

  public void setQuestions(Set<QuestionReponseDTO> questions) {
    this.questions = questions;
  }
  public Niveau getNiveau() {
    return niveau;
  }

  public void setNiveau(Niveau niveau) {
    this.niveau = niveau;
  }
}
