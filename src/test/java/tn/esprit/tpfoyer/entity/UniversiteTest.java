package tn.esprit.tpfoyer.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UniversiteTest {

    private Universite universite;
    private Foyer foyer;

    @BeforeEach
    void setUp() {
        // Initialize Universite and Foyer objects for testing
        universite = new Universite();
        universite.setNomUniversite("University of Example");
        universite.setAdresse("123 Example St");

        foyer = new Foyer();
        foyer.setNomFoyer("Foyer A");
        foyer.setCapaciteFoyer(100);

        // Set the relationship between Universite and Foyer
        universite.setFoyer(foyer);
    }

    @Test
    void testSetNomUniversite() {
        universite.setNomUniversite("New University");
        assertEquals("New University", universite.getNomUniversite());
    }

    @Test
    void testSetAdresse() {
        universite.setAdresse("456 New Address");
        assertEquals("456 New Address", universite.getAdresse());
    }

    @Test
    void testSetFoyer() {
        Foyer newFoyer = new Foyer();
        newFoyer.setNomFoyer("New Foyer");
        newFoyer.setCapaciteFoyer(200);

        universite.setFoyer(newFoyer);
        assertEquals("New Foyer", universite.getFoyer().getNomFoyer());
        assertEquals(200, universite.getFoyer().getCapaciteFoyer());
    }

    @Test
    void testSetId() {
        // Test for setId method, even though it's not typically needed in most use cases.
        universite.setId(1L);
        assertEquals(1L, universite.getIdUniversite());
    }

    @Test
    void testConstructor() {
        Universite newUniversite = new Universite("University of Test", "789 Test St", foyer);
        assertEquals("University of Test", newUniversite.getNomUniversite());
        assertEquals("789 Test St", newUniversite.getAdresse());
        assertEquals(foyer, newUniversite.getFoyer());
    }

    @Test
    void testToString() {
        String expectedString = "Universite(idUniversite=" + universite.getIdUniversite() +
                ", nomUniversite=University of Example, adresse=123 Example St, foyer=Foyer(idFoyer=null, nomFoyer=Foyer A, capaciteFoyer=100))";
        assertTrue(universite.toString().contains("University of Example"));
        assertTrue(universite.toString().contains("123 Example St"));
        assertTrue(universite.toString().contains("Foyer A"));
    }

}
