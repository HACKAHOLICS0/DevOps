package tn.esprit.tpfoyer.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.Set;

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
    long idEtudiant;

    String nomEtudiant;
    String prenomEtudiant;
    long cinEtudiant;  // Attribute for student ID (CIN)
    Date dateNaissance;

    @ManyToMany(mappedBy = "etudiants")
    Set<Reservation> reservations;

    public Etudiant(String alex, String brown, int i, Date date) {
    }

    // Setter for cinEtudiant
    public void setCinEtudiant(long cin) {
        this.cinEtudiant = cin;
    }

    public void setCin(Long cin) {
    }

    public void setNom(String john) {
    }

    public void setPrenom(String doe) {
    }
}
