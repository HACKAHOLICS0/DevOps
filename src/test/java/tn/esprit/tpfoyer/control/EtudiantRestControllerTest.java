package tn.esprit.tpfoyer.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.service.IEtudiantService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
        // Arrange
        Etudiant e1 = new Etudiant();
        Etudiant e2 = new Etudiant();
        List<Etudiant> expected = Arrays.asList(e1, e2);

        when(etudiantService.retrieveAllEtudiants()).thenReturn(expected);

        // Act
        List<Etudiant> actual = etudiantRestController.getEtudiants();

        // Assert
        assertEquals(expected, actual);
        verify(etudiantService, times(1)).retrieveAllEtudiants();
    }

    @Test
    void testRetrieveEtudiantParCin() {
        // Arrange
        Long cin = 12345678L;
        Etudiant etu = new Etudiant();
        etu.setCinEtudiant(cin);

        when(etudiantService.recupererEtudiantParCin(cin)).thenReturn(etu);

        // Act
        Etudiant result = etudiantRestController.retrieveEtudiantParCin(cin);

        // Assert
        assertEquals(etu, result);
        verify(etudiantService, times(1)).recupererEtudiantParCin(cin);
    }

    @Test
    void testRetrieveEtudiant() {
        // Arrange
        Long id = 1L;
        Etudiant etu = new Etudiant();
        etu.setIdEtudiant(id);

        when(etudiantService.retrieveEtudiant(id)).thenReturn(etu);

        // Act
        Etudiant result = etudiantRestController.retrieveEtudiant(id);

        // Assert
        assertEquals(etu, result);
        verify(etudiantService, times(1)).retrieveEtudiant(id);
    }

    @Test
    void testAddEtudiant() {
        // Arrange
        Etudiant toAdd = new Etudiant();
        Etudiant saved = new Etudiant();
        saved.setIdEtudiant(1L);

        when(etudiantService.addEtudiant(toAdd)).thenReturn(saved);

        // Act
        Etudiant result = etudiantRestController.addEtudiant(toAdd);

        // Assert
        assertEquals(saved, result);
        verify(etudiantService, times(1)).addEtudiant(toAdd);
    }

    @Test
    void testRemoveEtudiant() {
        // Arrange
        Long id = 2L;

        // Act
        etudiantRestController.removeEtudiant(id);

        // Assert
        verify(etudiantService, times(1)).removeEtudiant(id);
    }

    @Test
    void testModifyEtudiant() {
        // Arrange
        Etudiant toUpdate = new Etudiant();
        toUpdate.setIdEtudiant(1L);
        Etudiant updated = new Etudiant();
        updated.setIdEtudiant(1L);

        when(etudiantService.modifyEtudiant(toUpdate)).thenReturn(updated);

        // Act
        Etudiant result = etudiantRestController.modifyEtudiant(toUpdate);

        // Assert
        assertEquals(updated, result);
        verify(etudiantService, times(1)).modifyEtudiant(toUpdate);
    }
}
