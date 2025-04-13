package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.UniversiteRepository;

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

    @InjectMocks
    private UniversiteServiceImpl universiteService;

    private Universite universite;
    private Foyer foyer;

    @BeforeEach
    void setUp() {
        universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Universite A");
        universite.setAdresse("123 Rue Test");

        foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer A");
        foyer.setCapaciteFoyer(500);
        universite.setFoyer(foyer);
    }

    @Test
    void testAddUniversite() {
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite savedUniversite = universiteService.addUniversite(universite);

        assertNotNull(savedUniversite);
        assertEquals(1L, savedUniversite.getIdUniversite());
        assertEquals("Universite A", savedUniversite.getNomUniversite());
        assertEquals("123 Rue Test", savedUniversite.getAdresse());

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
        assertEquals("Universite A", retrievedUniversite.getNomUniversite());
        assertEquals("123 Rue Test", retrievedUniversite.getAdresse());

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
        assertEquals("Universite A", modifiedUniversite.getNomUniversite());
        assertEquals("123 Rue Test", modifiedUniversite.getAdresse());

        verify(universiteRepository, times(1)).save(any(Universite.class));
    }

    @Test
    void testAffecterFoyerAUniversite() {
        when(universiteRepository.findById(1L)).thenReturn(Optional.of(universite));
        when(universiteRepository.save(any(Universite.class))).thenReturn(universite);

        Universite updatedUniversite = universiteService.affecterFoyerAUniversite(1L, 1L);

        assertNotNull(updatedUniversite);
        assertEquals(1L, updatedUniversite.getIdUniversite());
        assertEquals(foyer, updatedUniversite.getFoyer());

        verify(universiteRepository, times(1)).findById(1L);
        verify(universiteRepository, times(1)).save(any(Universite.class));
    }
} 