package tn.esprit.pidev.dto;

import tn.esprit.pidev.Entity.Evaluation;
import tn.esprit.pidev.Entity.Subscription;
import tn.esprit.pidev.Entity.Training;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class TrainingDTO {
  private int idTraining;
  private String title;
  private String content;
  private Date trainingdate;
  private String duration;
  private String status;
  private String level;
  private List<Integer> evaluationIds;
  private List<Long> subscriptionIds;
  private boolean premium;
  public boolean isPremium() {
    return premium;
  }

  public void setPremium(boolean premium) {
    this.premium = premium;
  }

  public TrainingDTO(int idTraining, String title, String content, Date trainingdate, String duration, String status, String level, List<Integer> evaluationIds, List<Long> subscriptionIds, boolean premium) {
    this.idTraining = idTraining;
    this.title = title;
    this.content = content;
    this.trainingdate = trainingdate;
    this.duration = duration;
    this.status = status;
    this.level = level;
    this.evaluationIds = evaluationIds;
    this.subscriptionIds = subscriptionIds;
    this.premium = premium;
  }

  public TrainingDTO(Training training) {
    this.idTraining = training.getIdTraining();
    this.title = training.getTitle();
    this.content = training.getContent();
    this.trainingdate = training.getTrainingdate();
    this.duration = training.getDuration();
    this.status = training.getStatus().name();
    this.level = training.getLevel().name();

    // Extract only evaluation IDs
    if (training.getEvaluations() != null) {
      this.evaluationIds = training.getEvaluations().stream()
        .map(Evaluation::getIdEvaluation)
        .collect(Collectors.toList());
    }

    // Extract only subscription IDs
    if (training.getSubscriptions() != null) {
      this.subscriptionIds = training.getSubscriptions().stream()
        .map(Subscription::getSubId)
        .collect(Collectors.toList());
    }
  }

  // Getters and Setters
  public int getIdTraining() {
    return idTraining;
  }

  public void setIdTraining(int idTraining) {
    this.idTraining = idTraining;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getTrainingdate() {
    return trainingdate;
  }

  public void setTrainingdate(Date trainingdate) {
    this.trainingdate = trainingdate;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getLevel() {
    return level;
  }

  public void setLevel(String level) {
    this.level = level;
  }

  public List<Integer> getEvaluationIds() {
    return evaluationIds;
  }

  public void setEvaluationIds(List<Integer> evaluationIds) {
    this.evaluationIds = evaluationIds;
  }

  public List<Long> getSubscriptionIds() {
    return subscriptionIds;
  }

  public void setSubscriptionIds(List<Long> subscriptionIds) {
    this.subscriptionIds = subscriptionIds;
  }
}
