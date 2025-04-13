package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    @InjectMocks
    private EtudiantServiceImpl etudiantService;

    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEtudiant("Doe");
        etudiant.setPrenomEtudiant("John");
        etudiant.setCinEtudiant(12345678L);
        etudiant.setDateNaissance(new Date());
    }

    @Test
    void testAddEtudiant() {
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        Etudiant savedEtudiant = etudiantService.addEtudiant(etudiant);

        assertNotNull(savedEtudiant);
        assertEquals(1L, savedEtudiant.getIdEtudiant());
        assertEquals("Doe", savedEtudiant.getNomEtudiant());
        assertEquals("John", savedEtudiant.getPrenomEtudiant());
        assertEquals(12345678L, savedEtudiant.getCinEtudiant());
        assertNotNull(savedEtudiant.getDateNaissance());

        verify(etudiantRepository, times(1)).save(any(Etudiant.class));
    }

    @Test
    void testRetrieveAllEtudiants() {
        List<Etudiant> etudiants = Arrays.asList(etudiant);
        when(etudiantRepository.findAll()).thenReturn(etudiants);

        List<Etudiant> retrievedEtudiants = etudiantService.retrieveAllEtudiants();

        assertNotNull(retrievedEtudiants);
        assertEquals(1, retrievedEtudiants.size());
        assertEquals(etudiant, retrievedEtudiants.get(0));

        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveEtudiant() {
        when(etudiantRepository.findById(1L)).thenReturn(Optional.of(etudiant));

        Etudiant retrievedEtudiant = etudiantService.retrieveEtudiant(1L);

        assertNotNull(retrievedEtudiant);
        assertEquals(1L, retrievedEtudiant.getIdEtudiant());
        assertEquals("Doe", retrievedEtudiant.getNomEtudiant());
        assertEquals("John", retrievedEtudiant.getPrenomEtudiant());
        assertEquals(12345678L, retrievedEtudiant.getCinEtudiant());
        assertNotNull(retrievedEtudiant.getDateNaissance());

        verify(etudiantRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveEtudiant_NotFound() {
        when(etudiantRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> etudiantService.retrieveEtudiant(1L));

        verify(etudiantRepository, times(1)).findById(1L);
    }

    @Test
    void testRemoveEtudiant() {
        doNothing().when(etudiantRepository).deleteById(1L);

        etudiantService.removeEtudiant(1L);

        verify(etudiantRepository, times(1)).deleteById(1L);
    }

    @Test
    void testModifyEtudiant() {
        when(etudiantRepository.save(any(Etudiant.class))).thenReturn(etudiant);

        Etudiant modifiedEtudiant = etudiantService.modifyEtudiant(etudiant);

        assertNotNull(modifiedEtudiant);
        assertEquals(1L, modifiedEtudiant.getIdEtudiant());
        assertEquals("Doe", modifiedEtudiant.getNomEtudiant());
        assertEquals("John", modifiedEtudiant.getPrenomEtudiant());
        assertEquals(12345678L, modifiedEtudiant.getCinEtudiant());
        assertNotNull(modifiedEtudiant.getDateNaissance());

        verify(etudiantRepository, times(1)).save(any(Etudiant.class));
    }

    @Test
    void testRecupererEtudiantParCin() {
        when(etudiantRepository.findEtudiantByCinEtudiant(12345678L)).thenReturn(etudiant);

        Etudiant foundEtudiant = etudiantService.recupererEtudiantParCin(12345678L);

        assertNotNull(foundEtudiant);
        assertEquals(1L, foundEtudiant.getIdEtudiant());
        assertEquals("Doe", foundEtudiant.getNomEtudiant());
        assertEquals("John", foundEtudiant.getPrenomEtudiant());
        assertEquals(12345678L, foundEtudiant.getCinEtudiant());
        assertNotNull(foundEtudiant.getDateNaissance());

        verify(etudiantRepository, times(1)).findEtudiantByCinEtudiant(12345678L);
    }
} 