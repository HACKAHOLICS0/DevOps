package tn.esprit.tpfoyer.service;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import tn.esprit.tpfoyer.control.EtudiantRestController;
import tn.esprit.tpfoyer.entity.Etudiant;

import java.util.Arrays;
import java.util.List;

class EtudiantRestControllerTest {

    @Mock
    private IEtudiantService etudiantService;

    @InjectMocks
    private EtudiantRestController etudiantRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetEtudiants() {
        Etudiant etudiant = new Etudiant();
        when(etudiantService.retrieveAllEtudiants()).thenReturn(Arrays.asList(etudiant));
        List<Etudiant> etudiants = etudiantRestController.getEtudiants();
        assertNotNull(etudiants);
        assertEquals(1, etudiants.size());
    }

    @Test
    void testRetrieveEtudiant() {
        Etudiant etudiant = new Etudiant();
        when(etudiantService.retrieveEtudiant(1L)).thenReturn(etudiant);
        Etudiant result = etudiantRestController.retrieveEtudiant(1L);
        assertNotNull(result);
    }

    @Test
    void testAddEtudiant() {
        Etudiant etudiant = new Etudiant();
        when(etudiantService.addEtudiant(any(Etudiant.class))).thenReturn(etudiant);
        Etudiant result = etudiantRestController.addEtudiant(etudiant);
        assertNotNull(result);
    }

    @Test
    void testRemoveEtudiant() {
        doNothing().when(etudiantService).removeEtudiant(1L);
        etudiantRestController.removeEtudiant(1L);
        verify(etudiantService, times(1)).removeEtudiant(1L);
    }

    @Test
    void testModifyEtudiant() {
        Etudiant etudiant = new Etudiant();
        when(etudiantService.modifyEtudiant(any(Etudiant.class))).thenReturn(etudiant);
        Etudiant result = etudiantRestController.modifyEtudiant(etudiant);
        assertNotNull(result);
    }
}
