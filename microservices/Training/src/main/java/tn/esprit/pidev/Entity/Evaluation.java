package tn.esprit.pidev.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int idEvaluation;
    @ManyToOne
    @JoinColumn(name = "training_id")

    private Training training;

    private String description;
    private String type;
    private String evaluation_duration;
    private double score;
    private Date createdAt;
    private boolean certificat;

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL)
    private List<QuestionReponse> questions;  // Removed @JsonDeserialize annotation


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

    public Evaluation(List<QuestionReponse> questions) {
        this.questions = questions;
    }

    public List<QuestionReponse> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionReponse> questions) {
        this.questions = questions;
    }



}
