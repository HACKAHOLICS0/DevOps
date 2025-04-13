package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.UniversiteRepository;
import tn.esprit.tpfoyer.repository.FoyerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UniversiteServiceImplTest {

    @Mock
    private UniversiteRepository universiteRepository;

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    private Universite universite;
    private Foyer foyer;

    @BeforeEach
    void setUp() {
        universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("University A");
        universite.setAdresse("123 Main St");

        foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer A");
        foyer.setCapaciteFoyer(200);
    }

    @Test
    void testAddUniversite() {
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite savedUniversite = universiteService.addUniversite(universite);

        assertNotNull(savedUniversite);
        assertEquals(1L, savedUniversite.getIdUniversite());
        assertEquals("University A", savedUniversite.getNomUniversite());
        assertEquals("123 Main St", savedUniversite.getAdresse());

        verify(universiteRepository, times(1)).save(any(Universite.class));
    }

    @Test
    void testRetrieveAllUniversites() {
        List<Universite> universites = Arrays.asList(universite);
        when(universiteRepository.findAll()).thenReturn(universites);

        List<Universite> retrievedUniversites = universiteService.retrieveAllUniversites();

        assertNotNull(retrievedUniversites);
        assertEquals(1, retrievedUniversites.size());
        assertEquals(universite, retrievedUniversites.get(0));

        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveUniversite() {
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));

        Universite retrievedUniversite = universiteService.retrieveUniversite(1L);

        assertNotNull(retrievedUniversite);
        assertEquals(1L, retrievedUniversite.getIdUniversite());
        assertEquals("University A", retrievedUniversite.getNomUniversite());
        assertEquals("123 Main St", retrievedUniversite.getAdresse());

        verify(universiteRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveUniversite_NotFound() {
        when(universiteRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> universiteService.retrieveUniversite(1L));

        verify(universiteRepository, times(1)).findById(1L);
    }

    @Test
    void testRemoveUniversite() {
        doNothing().when(universiteRepository).deleteById(1L);

        universiteService.removeUniversite(1L);

        verify(universiteRepository, times(1)).deleteById(1L);
    }

    @Test
    void testModifyUniversite() {
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite modifiedUniversite = universiteService.modifyUniversite(universite);

        assertNotNull(modifiedUniversite);
        assertEquals(1L, modifiedUniversite.getIdUniversite());
        assertEquals("University A", modifiedUniversite.getNomUniversite());
        assertEquals("123 Main St", modifiedUniversite.getAdresse());

        verify(universiteRepository, times(1)).save(any(Universite.class));
    }

    @Test
    void testAffecterFoyerAUniversite() {
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = universiteService.affecterFoyerAUniversite(1L, 1L);

        assertNotNull(result);
        assertEquals(foyer, result.getFoyer());

        verify(universiteRepository, times(1)).findById(1L);
        verify(foyerRepository, times(1)).findById(1L);
        verify(universiteRepository, times(1)).save(any(Universite.class));
    }

    @Test
    void testAffecterFoyerAUniversite_UniversiteNotFound() {
        when(universiteRepository.findById(1L)).thenReturn(Optional.empty());

        Universite result = universiteService.affecterFoyerAUniversite(1L, 1L);

        assertNull(result);

        verify(universiteRepository, times(1)).findById(1L);
        verify(foyerRepository, never()).findById(any());
        verify(universiteRepository, never()).save(any());
    }

    @Test
    void testAffecterFoyerAUniversite_FoyerNotFound() {
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));
        when(foyerRepository.findById(1L)).thenReturn(Optional.empty());

        Universite result = universiteService.affecterFoyerAUniversite(1L, 1L);

        assertNull(result);

        verify(universiteRepository, times(1)).findById(1L);
        verify(foyerRepository, times(1)).findById(1L);
        verify(universiteRepository, never()).save(any());
    }

    @Test
    void testModifyUniversite_WithNullValues() {
        Universite nullUniversite = new Universite();
        when(universiteRepository.save(any(Universite.class))).thenReturn(nullUniversite);

        Universite modifiedUniversite = universiteService.modifyUniversite(nullUniversite);

        assertNotNull(modifiedUniversite);
        verify(universiteRepository, times(1)).save(any(Universite.class));
    }

    @Test
    void testRemoveUniversite_WhenUniversiteDoesNotExist() {
        doNothing().when(universiteRepository).deleteById(999L);

        assertDoesNotThrow(() -> universiteService.removeUniversite(999L));
        
        verify(universiteRepository, times(1)).deleteById(999L);
    }

    @Test
    void testRetrieveUniversite_WithInvalidId() {
        when(universiteRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> universiteService.retrieveUniversite(999L));

        verify(universiteRepository, times(1)).findById(999L);
    }

    @Test
    void testAddUniversite_WithNullValues() {
        Universite emptyUniversite = new Universite();
        when(universiteRepository.save(any(Universite.class))).thenReturn(emptyUniversite);

        Universite savedUniversite = universiteService.addUniversite(emptyUniversite);

        assertNotNull(savedUniversite);
        verify(universiteRepository, times(1)).save(any(Universite.class));
    }

    @Test
    void testRetrieveUniversite_ThrowsException() {
        when(universiteRepository.findById(1L)).thenReturn(Optional.empty());
        
        RuntimeException exception = assertThrows(RuntimeException.class,
            () -> universiteService.retrieveUniversite(1L));
        
        verify(universiteRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveAllUniversites_EmptyList() {
        when(universiteRepository.findAll()).thenReturn(Arrays.asList());

        List<Universite> result = universiteService.retrieveAllUniversites();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void testModifyUniversite_WithNewValues() {
        Universite newUniversite = new Universite();
        newUniversite.setIdUniversite(1L);
        newUniversite.setNomUniversite("Updated University");
        newUniversite.setAdresse("456 New St");

        when(universiteRepository.save(any(Universite.class))).thenReturn(newUniversite);

        Universite result = universiteService.modifyUniversite(newUniversite);

        assertNotNull(result);
        assertEquals("Updated University", result.getNomUniversite());
        assertEquals("456 New St", result.getAdresse());
        verify(universiteRepository, times(1)).save(any(Universite.class));
    }

    @Test
    void testAffecterFoyerAUniversite_WithExistingFoyer() {
        Universite universite = new Universite();
        universite.setIdUniversite(1L);
        Foyer existingFoyer = new Foyer();
        existingFoyer.setIdFoyer(2L);
        universite.setFoyer(existingFoyer);

        Foyer newFoyer = new Foyer();
        newFoyer.setIdFoyer(3L);

        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));
        when(foyerRepository.findById(3L)).thenReturn(Optional.of(newFoyer));
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite result = universiteService.affecterFoyerAUniversite(1L, 3L);

        assertNotNull(result);
        assertEquals(newFoyer, result.getFoyer());
        verify(universiteRepository, times(1)).findById(1L);
        verify(foyerRepository, times(1)).findById(3L);
        verify(universiteRepository, times(1)).save(any(Universite.class));
    }
} 