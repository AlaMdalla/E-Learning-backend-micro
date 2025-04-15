package tn.esprit.pidev.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;
@Entity
@AllArgsConstructor

public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEvaluation;

    @ManyToOne

    @JsonBackReference // Stops infinite recursion
    @JoinColumn(name = "training_id", referencedColumnName = "idTraining")
    private Training training;

    @Enumerated(EnumType.STRING)
    private Niveau niveau;
    private String description;
    private String type;
    private String evaluation_duration;
    private double score;
    private Date createdAt;
    private boolean certificat;

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL)
    private Set<QuestionReponse> questions;

    public Evaluation(int idEvaluation, boolean certificat, Date createdAt, String description,
                      String evaluation_duration, double score, String type, Training training) {
        this.idEvaluation = idEvaluation;
        this.certificat = certificat;
        this.createdAt = createdAt;
        this.description = description;
        this.evaluation_duration = evaluation_duration;
        this.score = score;
        this.type = type;
        this.training = training;
    }
    public Evaluation() {

    }
    public int getIdEvaluation() {
        return idEvaluation;
    }

    public void setIdEvaluation(int idEvaluation) {
        this.idEvaluation = idEvaluation;
    }

    public Training getTraining() {
        return training;
    }

    public void setTraining(Training training) {
        this.training = training;
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

    public String getEvaluation_duration() {
        return evaluation_duration;
    }

    public void setEvaluation_duration(String evaluation_duration) {
        this.evaluation_duration = evaluation_duration;
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

    public Evaluation(Set<QuestionReponse> questions) {
        this.questions = questions;
    }

    public Set<QuestionReponse> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<QuestionReponse> questions) {
        this.questions = questions;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

}