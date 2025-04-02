package tpfoyer.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.repository.EtudiantRepository;
import tn.esprit.tpfoyer.service.EtudiantServiceImpl;
import tn.esprit.tpfoyer.service.IEtudiantService;

import static org.junit.jupiter.api.Assertions.*;


import java.util.Date;
import java.util.Optional;

public class EtudiantServiceImplTest {

    @Mock
    private EtudiantRepository etudiantRepository;

    private IEtudiantService etudiantService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        etudiantService = new EtudiantServiceImpl(etudiantRepository);
    }

    @Test
    void testRetrieveEtudiant() {
        // Arrange
        Long etudiantId = 1L;
        Etudiant etudiant = new Etudiant(etudiantId, "John", "Doe", 123456L, new Date());
        when(etudiantRepository.findById(etudiantId)).thenReturn(Optional.of(etudiant));

        // Act
        Etudiant result = etudiantService.retrieveEtudiant(etudiantId);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getNomEtudiant());
    }

    @Test
    void testAddEtudiant() {
        // Arrange
        Etudiant etudiant = new Etudiant(null, "Jane", "Doe", 654321L, new Date());
        when(etudiantRepository.save(etudiant)).thenReturn(new Etudiant(1L, "Jane", "Doe", 654321L, new Date()));

        // Act
        Etudiant savedEtudiant = etudiantService.addEtudiant(etudiant);

        // Assert
        assertNotNull(savedEtudiant);
        assertEquals("Jane", savedEtudiant.getNomEtudiant());
    }

    @Test
    void testRemoveEtudiant() {
        // Arrange
        Long etudiantId = 1L;
        doNothing().when(etudiantRepository).deleteById(etudiantId);

        // Act
        etudiantService.removeEtudiant(etudiantId);

        // Assert
        verify(etudiantRepository, times(1)).deleteById(etudiantId);
    }
}
