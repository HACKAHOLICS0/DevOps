package tpfoyer.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.UniversiteRepository;
import tn.esprit.tpfoyer.service.UniversiteServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class UniversiteServiceImplTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllUniversites() {
        // Arrange
        List<Universite> universites = new ArrayList<>();
        Universite universite1 = new Universite(1L, "University A", "Address A", null);
        Universite universite2 = new Universite(2L, "University B", "Address B", null);
        universites.add(universite1);
        universites.add(universite2);
        when(universiteRepository.findAll()).thenReturn(universites);

        // Act
        List<Universite> result = universiteService.retrieveAllUniversites();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveUniversite() {
        // Arrange
        Long universiteId = 1L;
        Universite universite = new Universite(1L, "University A", "Address A", null);
        when(universiteRepository.findById(universiteId)).thenReturn(Optional.of(universite));

        // Act
        Universite result = universiteService.retrieveUniversite(universiteId);

        // Assert
        assertNotNull(result);
        assertEquals("University A", result.getNomUniversite());
    }

    @Test
    void testAddUniversite() {
        // Arrange
        Universite universite = new Universite(1L, "University A", "Address A", null);
        when(universiteRepository.save(universite)).thenReturn(universite);

        // Act
        Universite result = universiteService.addUniversite(universite);

        // Assert
        assertNotNull(result);
        assertEquals("University A", result.getNomUniversite());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testModifyUniversite() {
        // Arrange
        Universite universite = new Universite(1L, "University A", "Address A", null);
        when(universiteRepository.save(universite)).thenReturn(universite);

        // Act
        Universite result = universiteService.modifyUniversite(universite);

        // Assert
        assertNotNull(result);
        assertEquals("University A", result.getNomUniversite());
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testRemoveUniversite() {
        // Arrange
        Long universiteId = 1L;
        doNothing().when(universiteRepository).deleteById(universiteId);

        // Act
        universiteService.removeUniversite(universiteId);

        // Assert
        verify(universiteRepository, times(1)).deleteById(universiteId);
    }
}