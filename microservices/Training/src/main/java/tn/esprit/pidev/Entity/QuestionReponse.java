package tn.esprit.pidev.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class QuestionReponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idQuestion;

    private String questionText;

    @Enumerated(EnumType.STRING)
    private QuestionType type;
    @ElementCollection
    private List<String> options;

    private String bonneReponse;

    private double score;

    @ManyToOne
    @JoinColumn(name = "idEvaluation")
    private Evaluation evaluation;
    // Getters et Setters
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

    public QuestionType getType() {
        return type;
    }

    public void setType(QuestionType type) {
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

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }
}
