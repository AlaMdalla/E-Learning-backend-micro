package tn.esprit.pidev.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Utilisateur {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long idUtilisateur;
  private String email;

  private String nom;

  private String prenom;

  private String motDePasse;
  private String role;
}
