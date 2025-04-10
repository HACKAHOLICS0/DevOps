package tn.esprit.tpfoyer.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.service.IChambreService;
import tn.esprit.tpfoyer.control.ChambreRestController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ChambreTest {

    @Mock
    private IChambreService chambreService;

    @InjectMocks
    private ChambreRestController chambreRestController;

    private Chambre chambre1;
    private Chambre chambre2;
    private Bloc bloc;
    private Reservation reservation1;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setting up the test data
        bloc = new Bloc();
        bloc.setIdBloc(1L);  // Setting the ID for the bloc

        chambre1 = new Chambre(1L, 101L, TypeChambre.SINGLE, new HashSet<>(), bloc);
        chambre2 = new Chambre(2L, 102L, TypeChambre.DOUBLE, new HashSet<>(), bloc);

        reservation1 = new Reservation();
        reservation1.setIdReservation("R001");
    }

    @Test
    void testGetChambres() {
        // Arrange
        when(chambreService.retrieveAllChambres()).thenReturn(Arrays.asList(chambre1, chambre2));

        // Act
        Set<Chambre> actualChambres = new HashSet<>(chambreRestController.getChambres());

        // Assert
        assertTrue(actualChambres.contains(chambre1));
        assertTrue(actualChambres.contains(chambre2));
        verify(chambreService, times(1)).retrieveAllChambres();
    }

    @Test
    void testRetrieveChambre() {
        // Arrange
        Long chambreId = 1L;
        when(chambreService.retrieveChambre(chambreId)).thenReturn(chambre1);

        // Act
        Chambre actualChambre = chambreRestController.retrieveChambre(chambreId);

        // Assert
        assertEquals(chambre1, actualChambre);
        verify(chambreService, times(1)).retrieveChambre(chambreId);
    }

    @Test
    void testAddChambre() {
        // Arrange
        when(chambreService.addChambre(chambre1)).thenReturn(chambre1);

        // Act
        Chambre actualChambre = chambreRestController.addChambre(chambre1);

        // Assert
        assertEquals(chambre1, actualChambre);
        verify(chambreService, times(1)).addChambre(chambre1);
    }

    @Test
    void testRemoveChambre() {
        // Arrange
        Long chambreId = 1L;

        // Act
        chambreRestController.removeChambre(chambreId);

        // Assert
        verify(chambreService, times(1)).removeChambre(chambreId);
    }

    @Test
    void testModifyChambre() {
        // Arrange
        Chambre modifiedChambre = new Chambre(1L, 101L, TypeChambre.SUITE, new HashSet<>(), bloc);
        when(chambreService.modifyChambre(modifiedChambre)).thenReturn(modifiedChambre);

        // Act
        Chambre actualChambre = chambreRestController.modifyChambre(modifiedChambre);

        // Assert
        assertEquals(modifiedChambre, actualChambre);
        verify(chambreService, times(1)).modifyChambre(modifiedChambre);
    }

    @Test
    void testTrouverChambresSelonTypeChambre() {
        // Arrange
        TypeChambre type = TypeChambre.SINGLE;
        when(chambreService.recupererChambresSelonTyp(type)).thenReturn(Arrays.asList(chambre1));

        // Act
        Set<Chambre> actualChambres = new HashSet<>(chambreRestController.trouverChSelonTC(type));

        // Assert
        assertTrue(actualChambres.contains(chambre1));
        verify(chambreService, times(1)).recupererChambresSelonTyp(type);
    }

    @Test
    void testTrouverChambreSelonEtudiant() {
        // Arrange
        long cin = 12345L; // assuming 'cin' is a unique identifier for student
        when(chambreService.trouverchambreSelonEtudiant(cin)).thenReturn(chambre1);

        // Act
        Chambre actualChambre = chambreRestController.trouverChSelonEt(cin);

        // Assert
        assertEquals(chambre1, actualChambre);
        verify(chambreService, times(1)).trouverchambreSelonEtudiant(cin);
    }
}
