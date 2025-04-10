package tn.esprit.tpfoyer.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.service.IBlocService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BlocRestControllerTest {

    @Mock
    private IBlocService blocService;

    @InjectMocks
    private BlocRestController blocRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBlocs() {
        // Arrange
        Bloc bloc1 = new Bloc();
        Bloc bloc2 = new Bloc();
        List<Bloc> expectedBlocs = Arrays.asList(bloc1, bloc2);

        when(blocService.retrieveAllBlocs()).thenReturn(expectedBlocs);

        // Act
        List<Bloc> actualBlocs = blocRestController.getBlocs();

        // Assert
        assertEquals(expectedBlocs, actualBlocs);
        verify(blocService, times(1)).retrieveAllBlocs();
    }

    @Test
    void testRetrieveBloc() {
        // Arrange
        Long blocId = 1L;
        Bloc expectedBloc = new Bloc();
        expectedBloc.setIdBloc(blocId);

        when(blocService.retrieveBloc(blocId)).thenReturn(expectedBloc);

        // Act
        Bloc actualBloc = blocRestController.retrieveBloc(blocId);

        // Assert
        assertEquals(expectedBloc, actualBloc);
        verify(blocService, times(1)).retrieveBloc(blocId);
    }

    @Test
    void testAddBloc() {
        // Arrange
        Bloc blocToAdd = new Bloc();
        Bloc expectedBloc = new Bloc();
        expectedBloc.setIdBloc(1L);

        when(blocService.addBloc(blocToAdd)).thenReturn(expectedBloc);

        // Act
        Bloc actualBloc = blocRestController.addBloc(blocToAdd);

        // Assert
        assertEquals(expectedBloc, actualBloc);
        verify(blocService, times(1)).addBloc(blocToAdd);
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
        Bloc blocToModify = new Bloc();
        blocToModify.setIdBloc(1L);
        Bloc expectedBloc = new Bloc();
        expectedBloc.setIdBloc(1L);

        when(blocService.modifyBloc(blocToModify)).thenReturn(expectedBloc);

        // Act
        Bloc actualBloc = blocRestController.modifyBloc(blocToModify);

        // Assert
        assertEquals(expectedBloc, actualBloc);
        verify(blocService, times(1)).modifyBloc(blocToModify);
    }

    @Test
    void testGetBlocsWithoutFoyer() {
        // Arrange
        Bloc bloc1 = new Bloc();
        Bloc bloc2 = new Bloc();
        List<Bloc> expectedBlocs = Arrays.asList(bloc1, bloc2);

        when(blocService.trouverBlocsSansFoyer()).thenReturn(expectedBlocs);

        // Act
        List<Bloc> actualBlocs = blocRestController.getBlocsWithoutFoyer();

        // Assert
        assertEquals(expectedBlocs, actualBlocs);
        verify(blocService, times(1)).trouverBlocsSansFoyer();
    }

    @Test
    void testRecuperBlocsParNomEtCap() {
        // Arrange
        String nom = "Bloc A";
        long capacite = 100;
        Bloc bloc1 = new Bloc();
        List<Bloc> expectedBlocs = Arrays.asList(bloc1);

        when(blocService.trouverBlocsParNomEtCap(nom, capacite)).thenReturn(expectedBlocs);

        // Act
        List<Bloc> actualBlocs = blocRestController.recuperBlocsParNomEtCap(nom, capacite);

        // Assert
        assertEquals(expectedBlocs, actualBlocs);
        verify(blocService, times(1)).trouverBlocsParNomEtCap(nom, capacite);
    }
}