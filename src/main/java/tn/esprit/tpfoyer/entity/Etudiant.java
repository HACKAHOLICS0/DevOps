package tn.esprit.tpfoyer.entity;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Etudiant {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long idEtudiant;

  String nomEtudiant;
  String prenomEtudiant;
  long cinEtudiant;
  Date dateNaissance;

  @ManyToMany(mappedBy = "etudiants")
  Set<Reservation> reservations;

  public Etudiant(
      long idEtudiant,
      String nomEtudiant,
      String prenomEtudiant,
      long cinEtudiant,
      Date dateNaissance) {
    this.idEtudiant = idEtudiant;
    this.nomEtudiant = nomEtudiant;
    this.prenomEtudiant = prenomEtudiant;
    this.cinEtudiant = cinEtudiant;
    this.dateNaissance = dateNaissance;
  }
}
