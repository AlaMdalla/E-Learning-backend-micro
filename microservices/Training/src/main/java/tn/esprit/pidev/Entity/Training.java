package tn.esprit.pidev.Entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
//@AllArgsConstructor
@NoArgsConstructor
public class Training
{  @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int idTraining;
    private String title;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date trainingdate;

    private String duration;
    @Enumerated(EnumType.STRING)
    private status status;
    @Enumerated(EnumType.STRING)
    private level level;
    private boolean premium;

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public Training(int idTraining, String title, String content, Date trainingdate, String duration, tn.esprit.pidev.Entity.status status, tn.esprit.pidev.Entity.level level, boolean premium, List<Evaluation> evaluations, List<Subscription> subscriptions) {
        this.idTraining = idTraining;
        this.title = title;
        this.content = content;
        this.trainingdate = trainingdate;
        this.duration = duration;
        this.status = status;
        this.level = level;
        this.premium = premium;
        this.evaluations = evaluations;
        this.subscriptions = subscriptions;
    }

    @OneToMany(mappedBy = "training", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // Forward serialization
    private List<Evaluation> evaluations = new ArrayList<>();

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

    public Training() {
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

    public status getStatus() {
        return status;
    }

    public void setStatus(status status) {
        this.status = status;
    }

    public level getLevel() {
        return level;
    }

    public void setLevel(level level) {
        this.level = level;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @OneToMany(mappedBy = "formation")
    private List<Subscription> subscriptions;

}