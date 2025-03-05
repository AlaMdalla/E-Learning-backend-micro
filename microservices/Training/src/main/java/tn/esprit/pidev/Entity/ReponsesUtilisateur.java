package tn.esprit.pidev.Entity;

import ch.qos.logback.classic.pattern.Util;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import tn.esprit.pidev.Entity.Utilisateur;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ReponsesUtilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReponse;

    @ManyToOne
    @JoinColumn(name = "idQuestion")
    private QuestionReponse question; // Référence à la question

    @ManyToOne
    @JoinColumn(name = "idUser")
    private Utilisateur user; // L'utilisateur qui a répondu

    private String reponseDonnee; // La réponse de l’utilisateur

    private boolean correct; // Est-ce que la réponse est correcte ?

    // Getters et Setters
    public int getIdReponse() {
        return idReponse;
    }

    public void setIdReponse(int idReponse) {
        this.idReponse = idReponse;
    }

    public QuestionReponse getQuestion() {
        return question;
    }

    public void setQuestion(QuestionReponse question) {
        this.question = question;
    }


    public String getReponseDonnee() {
        return reponseDonnee;
    }

    public void setReponseDonnee(String reponseDonnee) {
        this.reponseDonnee = reponseDonnee;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }
}
