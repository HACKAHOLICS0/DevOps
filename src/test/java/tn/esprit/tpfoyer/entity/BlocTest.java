package tn.esprit.tpfoyer.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.control.BlocRestController;
import tn.esprit.tpfoyer.service.IBlocService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlocTest {

    @Mock
    private IBlocService blocService;

    @InjectMocks
    private BlocRestController blocRestController;

    private Bloc bloc1;
    private Bloc bloc2;
    private Foyer foyer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setting up the test data
        foyer = new Foyer();
        foyer.setIdFoyer(1L);  // Setting the ID for the foyer

        bloc1 = new Bloc(1L, "Bloc A", 100L, foyer, new HashSet<>());
        bloc2 = new Bloc(2L, "Bloc B", 50L, foyer, new HashSet<>());
    }

    @Test
    void testGetBlocs() {
        // Arrange
        when(blocService.retrieveAllBlocs()).thenReturn(Arrays.asList(bloc1, bloc2));

        // Act
        Set<Bloc> actualBlocs = new HashSet<>(blocRestController.getBlocs());

        // Assert
        assertTrue(actualBlocs.contains(bloc1));
        assertTrue(actualBlocs.contains(bloc2));
        verify(blocService, times(1)).retrieveAllBlocs();
    }

    @Test
    void testRetrieveBloc() {
        // Arrange
        Long blocId = 1L;
        when(blocService.retrieveBloc(blocId)).thenReturn(bloc1);

        // Act
        Bloc actualBloc = blocRestController.retrieveBloc(blocId);

        // Assert
        assertEquals(bloc1, actualBloc);
        verify(blocService, times(1)).retrieveBloc(blocId);
    }

    @Test
    void testAddBloc() {
        // Arrange
        when(blocService.addBloc(bloc1)).thenReturn(bloc1);

        // Act
        Bloc actualBloc = blocRestController.addBloc(bloc1);

        // Assert
        assertEquals(bloc1, actualBloc);
        verify(blocService, times(1)).addBloc(bloc1);
    }

    @Test
    void testRemoveBloc() {
        // Arrange
        Long blocId = 1L;

        // Act
        blocRestController.removeBloc(blocId);

        // Assert
        verify(blocService, times(1)).removeBloc(blocId);
    }

    @Test
    void testModifyBloc() {
        // Arrange
        Bloc modifiedBloc = new Bloc(1L, "Modified Bloc", 200L, foyer, new HashSet<>());
        when(blocService.modifyBloc(modifiedBloc)).thenReturn(modifiedBloc);

        // Act
        Bloc actualBloc = blocRestController.modifyBloc(modifiedBloc);

        // Assert
        assertEquals(modifiedBloc, actualBloc);
        verify(blocService, times(1)).modifyBloc(modifiedBloc);
    }

    @Test
    void testGetBlocsWithoutFoyer() {
        // Arrange
        when(blocService.trouverBlocsSansFoyer()).thenReturn(Arrays.asList(bloc1));

        // Act
        Set<Bloc> actualBlocs = new HashSet<>(blocRestController.getBlocswirhoutFoyer());

        // Assert
        assertTrue(actualBlocs.contains(bloc1));
        verify(blocService, times(1)).trouverBlocsSansFoyer();
    }

    @Test
    void testRecuperBlocsParNomEtCap() {
        // Arrange
        String nom = "Bloc A";
        long capacite = 100L;
        when(blocService.trouverBlocsParNomEtCap(nom, capacite)).thenReturn(Arrays.asList(bloc1));

        // Act
        Set<Bloc> actualBlocs = new HashSet<>(blocRestController.recuperBlocsParNomEtCap(nom, capacite));

        // Assert
        assertTrue(actualBlocs.contains(bloc1));
        verify(blocService, times(1)).trouverBlocsParNomEtCap(nom, capacite);
    }
}
