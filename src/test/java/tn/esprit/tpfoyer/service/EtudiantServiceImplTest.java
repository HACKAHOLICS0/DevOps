package tn.esprit.tpfoyer.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;

import java.util.List;

 class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    private EtudiantServiceImpl etudiantService;

    @BeforeEach
     void setUp() {
        MockitoAnnotations.openMocks(this);
        etudiantService = new EtudiantServiceImpl(etudiantRepository);
    }

    @Test
     void testRetrieveAllEtudiants() {
        Etudiant etudiant1 = new Etudiant();
        Etudiant etudiant2 = new Etudiant();
        when(etudiantRepository.findAll()).thenReturn(List.of(etudiant1, etudiant2));

        List<Etudiant> result = etudiantService.retrieveAllEtudiants();
        assertEquals(2, result.size());
        verify(etudiantRepository, times(1)).findAll();
    }

    @Test
     void testAddEtudiant() {
        Etudiant etudiant = new Etudiant();
        when(etudiantRepository.save(etudiant)).thenReturn(etudiant);

        Etudiant result = etudiantService.addEtudiant(etudiant);
        assertNotNull(result);
        verify(etudiantRepository, times(1)).save(etudiant);
    }

    @Test
     void testRetrieveEtudiant() {
        Long etudiantId = 1L;
        Etudiant etudiant = new Etudiant();
        when(etudiantRepository.findById(etudiantId)).thenReturn(java.util.Optional.of(etudiant));

        Etudiant result = etudiantService.retrieveEtudiant(etudiantId);
        assertNotNull(result);
        verify(etudiantRepository, times(1)).findById(etudiantId);
    }
}

