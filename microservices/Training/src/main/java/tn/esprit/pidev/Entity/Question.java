package tn.esprit.pidev.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int questionId;


  @Enumerated(EnumType.STRING)
  private type type;

  public tn.esprit.pidev.Entity.type getType() {
    return type;
  }

  public void setType(tn.esprit.pidev.Entity.type type) {
    this.type = type;
  }

  public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    private String questionText;


}
