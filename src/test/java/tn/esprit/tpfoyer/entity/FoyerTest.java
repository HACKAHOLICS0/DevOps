package tn.esprit.tpfoyer.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class FoyerTest {

    private Foyer foyer;
    private Universite universite;
    private Bloc bloc;

    @BeforeEach
    void setUp() {
        // Initialize Foyer object for testing
        foyer = new Foyer();
        foyer.setNomFoyer("Foyer A");
        foyer.setCapaciteFoyer(100);

        // Initialize related Universite object
        universite = new Universite();
        universite.setNomUniversite("University X");
        foyer.setUniversite(universite);

        // Initialize related Bloc object
        bloc = new Bloc();
        bloc.setNomBloc("Bloc 1");
        bloc.setCapaciteBloc(50);

        // Set the related Bloc to the Foyer
        Set<Bloc> blocs = new HashSet<>();
        blocs.add(bloc);
        foyer.setBlocs(blocs);
    }

    @Test
    void testSetNomFoyer() {
        foyer.setNomFoyer("Foyer B");
        assertEquals("Foyer B", foyer.getNomFoyer());
    }

    @Test
    void testSetCapaciteFoyer() {
        foyer.setCapaciteFoyer(200);
        assertEquals(200, foyer.getCapaciteFoyer());
    }

    @Test
    void testSetUniversite() {
        Universite newUniversite = new Universite();
        newUniversite.setNomUniversite("University Y");
        foyer.setUniversite(newUniversite);
        assertEquals("University Y", foyer.getUniversite().getNomUniversite());
    }

    @Test
    void testSetBlocs() {
        Set<Bloc> newBlocs = new HashSet<>();
        Bloc newBloc = new Bloc();
        newBloc.setNomBloc("Bloc 2");
        newBlocs.add(newBloc);
        foyer.setBlocs(newBlocs);
        assertTrue(foyer.getBlocs().contains(newBloc));
    }

    @Test
    void testSetId() {
        // If you want to test setId behavior, you can test it here
        foyer.setId(1);
        // If the setId method is supposed to do something meaningful, you should assert its impact here
        // Since the method is empty, no state change will occur. Consider removing or modifying the method.
        assertNull(foyer.getIdFoyer());
    }

    @Test
    void testConstructor() {
        Foyer newFoyer = new Foyer("Foyer C", 150);
        assertEquals("Foyer C", newFoyer.getNomFoyer());
        assertEquals(150, newFoyer.getCapaciteFoyer());
    }

    @Test
    void testToString() {
        String expectedString = "Foyer(idFoyer=0, nomFoyer=Foyer A, capaciteFoyer=100, universite=University X, blocs=[Bloc(idBloc=0, nomBloc=Bloc 1, capaciteBloc=50)])";
        assertTrue(foyer.toString().contains("Foyer A"));
        assertTrue(foyer.toString().contains("100"));
        assertTrue(foyer.toString().contains("University X"));
        assertTrue(foyer.toString().contains("Bloc 1"));
    }

    @Test
    void testOneToManyRelationshipWithBlocs() {
        assertTrue(foyer.getBlocs().size() > 0);
        assertTrue(foyer.getBlocs().contains(bloc));
    }

    @Test
    void testOneToOneRelationshipWithUniversite() {
        assertNotNull(foyer.getUniversite());
        assertEquals("University X", foyer.getUniversite().getNomUniversite());
    }
}
