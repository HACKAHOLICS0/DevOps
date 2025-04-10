package tn.esprit.tpfoyer.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.service.IUniversiteService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UniversiteRestControllerTest {

    @Mock
    private IUniversiteService universiteService;

    @InjectMocks
    private UniversiteRestController universiteRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUniversites() {
        // Arrange
        Universite universite1 = new Universite();
        Universite universite2 = new Universite();
        List<Universite> expectedUniversites = Arrays.asList(universite1, universite2);

        when(universiteService.retrieveAllUniversites()).thenReturn(expectedUniversites);

        // Act
        List<Universite> actualUniversites = universiteRestController.getUniversites();

        // Assert
        assertEquals(expectedUniversites, actualUniversites);
        verify(universiteService, times(1)).retrieveAllUniversites();
    }

    @Test
    void testRetrieveUniversite() {
        // Arrange
        Long universiteId = 1L;
        Universite expectedUniversite = new Universite();
        expectedUniversite.setId(universiteId);

        when(universiteService.retrieveUniversite(universiteId)).thenReturn(expectedUniversite);

        // Act
        Universite actualUniversite = universiteRestController.retrieveUniversite(universiteId);

        // Assert
        assertEquals(expectedUniversite, actualUniversite);
        verify(universiteService, times(1)).retrieveUniversite(universiteId);
    }

    @Test
    void testAddUniversite() {
        // Arrange
        Universite universiteToAdd = new Universite();
        Universite expectedUniversite = new Universite();
        expectedUniversite.setId(1L);

        when(universiteService.addUniversite(universiteToAdd)).thenReturn(expectedUniversite);

        // Act
        Universite actualUniversite = universiteRestController.addUniversite(universiteToAdd);

        // Assert
        assertEquals(expectedUniversite, actualUniversite);
        verify(universiteService, times(1)).addUniversite(universiteToAdd);
    }

    @Test
    void testRemoveUniversite() {
        // Arrange
        Long universiteId = 1L;

        // Act
        universiteRestController.removeUniversite(universiteId);

        // Assert
        verify(universiteService, times(1)).removeUniversite(universiteId);
    }

    @Test
    void testModifyUniversite() {
        // Arrange
        Universite universiteToModify = new Universite();
        universiteToModify.setId(1L);
        Universite expectedUniversite = new Universite();
        expectedUniversite.setId(1L);

        when(universiteService.modifyUniversite(universiteToModify)).thenReturn(expectedUniversite);

        // Act
        Universite actualUniversite = universiteRestController.modifyUniversite(universiteToModify);

        // Assert
        assertEquals(expectedUniversite, actualUniversite);
        verify(universiteService, times(1)).modifyUniversite(universiteToModify);
    }
}
