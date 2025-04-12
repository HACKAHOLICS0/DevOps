package tn.esprit.tpfoyer.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.service.IFoyerService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FoyerRestControllerTest {

    @Mock
    private IFoyerService foyerService;

    @InjectMocks
    private FoyerRestController foyerRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFoyers() {
        // Arrange
        Foyer foyer1 = new Foyer();
        Foyer foyer2 = new Foyer();
        List<Foyer> expectedFoyers = Arrays.asList(foyer1, foyer2);

        when(foyerService.retrieveAllFoyers()).thenReturn(expectedFoyers);

        // Act
        List<Foyer> actualFoyers = foyerRestController.getFoyers();

        // Assert
        assertEquals(expectedFoyers, actualFoyers);
        verify(foyerService, times(1)).retrieveAllFoyers();
    }

    @Test
    void testRetrieveFoyer() {
        // Arrange
        Long foyerId = 1L;
        Foyer expectedFoyer = new Foyer();
        expectedFoyer.setIdFoyer(foyerId);

        when(foyerService.retrieveFoyer(foyerId)).thenReturn(expectedFoyer);

        // Act
        Foyer actualFoyer = foyerRestController.retrieveFoyer(foyerId);

        // Assert
        assertEquals(expectedFoyer, actualFoyer);
        verify(foyerService, times(1)).retrieveFoyer(foyerId);
    }

    @Test
    void testAddFoyer() {
        // Arrange
        Foyer foyerToAdd = new Foyer();
        Foyer expectedFoyer = new Foyer();
        expectedFoyer.setIdFoyer(1L);

        when(foyerService.addFoyer(foyerToAdd)).thenReturn(expectedFoyer);

        // Act
        Foyer actualFoyer = foyerRestController.addFoyer(foyerToAdd);

        // Assert
        assertEquals(expectedFoyer, actualFoyer);
        verify(foyerService, times(1)).addFoyer(foyerToAdd);
    }

    @Test
    void testRemoveFoyer() {
        // Arrange
        Long foyerId = 1L;

        // Act
        foyerRestController.removeFoyer(foyerId);

        // Assert
        verify(foyerService, times(1)).removeFoyer(foyerId);
    }

    @Test
    void testModifyFoyer() {
        // Arrange
        Foyer foyerToModify = new Foyer();
        foyerToModify.setIdFoyer(1L);
        Foyer expectedFoyer = new Foyer();
        expectedFoyer.setIdFoyer(1L);

        when(foyerService.modifyFoyer(foyerToModify)).thenReturn(expectedFoyer);

        // Act
        Foyer actualFoyer = foyerRestController.modifyFoyer(foyerToModify);

        // Assert
        assertEquals(expectedFoyer, actualFoyer);
        verify(foyerService, times(1)).modifyFoyer(foyerToModify);
    }
}
