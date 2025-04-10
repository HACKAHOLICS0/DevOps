package tn.esprit.tpfoyer.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    private Reservation reservation;
    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        // Initialize Reservation object for testing
        reservation = new Reservation();
        reservation.setIdReservation("R123");
        reservation.setAnneeUniversitaire(new Date());
        reservation.setEstValide(true);

        // Initialize Etudiant object
        etudiant = new Etudiant();
        etudiant.setCin(12345L);
        etudiant.setNom("John");
        etudiant.setPrenom("Doe");

        // Set the related Etudiants to the Reservation
        Set<Etudiant> etudiants = new HashSet<>();
        etudiants.add(etudiant);
        reservation.setEtudiants(etudiants);
    }

    @Test
    void testSetIdReservation() {
        reservation.setIdReservation("R124");
        assertEquals("R124", reservation.getIdReservation());
    }

    @Test
    void testSetAnneeUniversitaire() {
        Date newDate = new Date(2025 - 1900, 5, 15); // A sample date in 2025
        reservation.setAnneeUniversitaire(newDate);
        assertEquals(newDate, reservation.getAnneeUniversitaire());
    }

    @Test
    void testSetEstValide() {
        reservation.setEstValide(false);
        assertFalse(reservation.isEstValide());
    }

    @Test
    void testSetEtudiants() {
        Set<Etudiant> newEtudiants = new HashSet<>();
        Etudiant newEtudiant = new Etudiant();
        newEtudiant.setCin(67890L);
        newEtudiant.setNom("Jane");
        newEtudiants.add(newEtudiant);
        reservation.setEtudiants(newEtudiants);
        assertTrue(reservation.getEtudiants().contains(newEtudiant));
    }

    @Test
    void testConstructor() {
        Reservation newReservation = new Reservation("R125", new Date(), true);
        assertEquals("R125", newReservation.getIdReservation());
        assertNotNull(newReservation.getAnneeUniversitaire());
        assertTrue(newReservation.isEstValide());
    }

    @Test
    void testToString() {
        String expectedString = "Reservation(idReservation=R123, anneeUniversitaire=" + reservation.getAnneeUniversitaire() + ", estValide=true, etudiants=[Etudiant(cin=12345, nom=John, prenom=Doe)])";
        assertTrue(reservation.toString().contains("R123"));
        assertTrue(reservation.toString().contains("true"));
        assertTrue(reservation.toString().contains("John"));
        assertTrue(reservation.toString().contains("Doe"));
    }

    @Test
    void testManyToManyRelationshipWithEtudiants() {
        assertTrue(reservation.getEtudiants().size() > 0);
        assertTrue(reservation.getEtudiants().contains(etudiant));
    }

}
