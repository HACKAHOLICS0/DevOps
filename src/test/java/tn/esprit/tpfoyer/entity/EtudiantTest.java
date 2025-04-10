package tn.esprit.tpfoyer.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class EtudiantTest {

    private Etudiant etudiant;
    private Reservation reservation;

    @BeforeEach
    void setUp() {
        // Initialize an Etudiant object and set its properties for testing
        etudiant = new Etudiant();
        etudiant.setNomEtudiant("John");
        etudiant.setPrenomEtudiant("Doe");
        etudiant.setCinEtudiant(123456789);
        etudiant.setDateNaissance(new Date());

        // Initialize a Set of Reservations for this Etudiant
        reservation = new Reservation();
        reservation.setIdReservation("R001");
        Set<Reservation> reservations = new HashSet<>();
        reservations.add(reservation);
        etudiant.setReservations(reservations);
    }

    @Test
    void testSetNomEtudiant() {
        etudiant.setNomEtudiant("Jane");
        assertEquals("Jane", etudiant.getNomEtudiant());
    }

    @Test
    void testSetPrenomEtudiant() {
        etudiant.setPrenomEtudiant("Smith");
        assertEquals("Smith", etudiant.getPrenomEtudiant());
    }

    @Test
    void testSetCinEtudiant() {
        etudiant.setCinEtudiant(987654321);
        assertEquals(987654321, etudiant.getCinEtudiant());
    }

    @Test
    void testSetDateNaissance() {
        Date newDate = new Date();
        etudiant.setDateNaissance(newDate);
        assertEquals(newDate, etudiant.getDateNaissance());
    }

    @Test
    void testSetReservations() {
        Set<Reservation> newReservations = new HashSet<>();
        Reservation newReservation = new Reservation();
        newReservation.setIdReservation("R002");
        newReservations.add(newReservation);

        etudiant.setReservations(newReservations);

        assertTrue(etudiant.getReservations().contains(newReservation));
    }

    @Test
    void testConstructor() {
        // Create a new Etudiant using the constructor
        Etudiant newEtudiant = new Etudiant("Alex", "Brown", 1122334455, new Date());
        assertEquals("Alex", newEtudiant.getNomEtudiant());
        assertEquals("Brown", newEtudiant.getPrenomEtudiant());
        assertEquals(1122334455, newEtudiant.getCinEtudiant());
    }

    @Test
    void testToString() {
        // Test the toString method
        String expectedString = "Etudiant(idEtudiant=0, nomEtudiant=John, prenomEtudiant=Doe, cinEtudiant=123456789, dateNaissance=" + etudiant.getDateNaissance() + ")";
        assertTrue(etudiant.toString().contains("John"));
        assertTrue(etudiant.toString().contains("Doe"));
        assertTrue(etudiant.toString().contains("123456789"));
    }
}
