package tpfoyer.service;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;
import tn.esprit.tpfoyer.service.FoyerServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class FoyerServiceImplTest {

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private FoyerServiceImpl foyerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllFoyers() {
        // Arrange
        List<Foyer> foyers = new ArrayList<>();
        Foyer foyer1 = new Foyer(1L, "Foyer A", 200, null, null);
        Foyer foyer2 = new Foyer(2L, "Foyer B", 150, null, null);
        foyers.add(foyer1);
        foyers.add(foyer2);
        when(foyerRepository.findAll()).thenReturn(foyers);

        // Act
        List<Foyer> result = foyerService.retrieveAllFoyers();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveFoyer() {
        // Arrange
        Long foyerId = 1L;
        Foyer foyer = new Foyer(1L, "Foyer A", 200, null, null);
        when(foyerRepository.findById(foyerId)).thenReturn(Optional.of(foyer));

        // Act
        Foyer result = foyerService.retrieveFoyer(foyerId);

        // Assert
        assertNotNull(result);
        assertEquals("Foyer A", result.getNomFoyer());
    }

    @Test
    void testAddFoyer() {
        // Arrange
        Foyer foyer = new Foyer(1L, "Foyer A", 200, null, null);
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Act
        Foyer result = foyerService.addFoyer(foyer);

        // Assert
        assertNotNull(result);
        assertEquals("Foyer A", result.getNomFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void testModifyFoyer() {
        // Arrange
        Foyer foyer = new Foyer(1L, "Foyer A", 200, null, null);
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        // Act
        Foyer result = foyerService.modifyFoyer(foyer);

        // Assert
        assertNotNull(result);
        assertEquals("Foyer A", result.getNomFoyer());
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void testRemoveFoyer() {
        // Arrange
        Long foyerId = 1L;
        doNothing().when(foyerRepository).deleteById(foyerId);

        // Act
        foyerService.removeFoyer(foyerId);

        // Assert
        verify(foyerRepository, times(1)).deleteById(foyerId);
    }
}