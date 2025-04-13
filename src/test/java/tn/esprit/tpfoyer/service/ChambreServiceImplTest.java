package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.ChambreRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChambreServiceImplTest {

    @Mock
    private ChambreRepository chambreRepository;

    @InjectMocks
    private ChambreServiceImpl chambreService;

    private Chambre chambre;

    @BeforeEach
    void setUp() {
        chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(101);
        chambre.setTypeC(TypeChambre.SIMPLE);
    }

    @Test
    void testAddChambre() {
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        Chambre savedChambre = chambreService.addChambre(chambre);

        assertNotNull(savedChambre);
        assertEquals(1L, savedChambre.getIdChambre());
        assertEquals(101, savedChambre.getNumeroChambre());
        assertEquals(TypeChambre.SIMPLE, savedChambre.getTypeC());

        verify(chambreRepository, times(1)).save(any(Chambre.class));
    }

    @Test
    void testRetrieveAllChambres() {
        List<Chambre> chambres = Arrays.asList(chambre);
        when(chambreRepository.findAll()).thenReturn(chambres);

        List<Chambre> retrievedChambres = chambreService.retrieveAllChambres();

        assertNotNull(retrievedChambres);
        assertEquals(1, retrievedChambres.size());
        assertEquals(chambre, retrievedChambres.get(0));

        verify(chambreRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveChambre() {
        when(chambreRepository.findById(1L)).thenReturn(Optional.of(chambre));

        Chambre retrievedChambre = chambreService.retrieveChambre(1L);

        assertNotNull(retrievedChambre);
        assertEquals(1L, retrievedChambre.getIdChambre());
        assertEquals(101, retrievedChambre.getNumeroChambre());
        assertEquals(TypeChambre.SIMPLE, retrievedChambre.getTypeC());

        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveChambre_NotFound() {
        when(chambreRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> chambreService.retrieveChambre(1L));

        verify(chambreRepository, times(1)).findById(1L);
    }

    @Test
    void testRemoveChambre() {
        doNothing().when(chambreRepository).deleteById(1L);

        chambreService.removeChambre(1L);

        verify(chambreRepository, times(1)).deleteById(1L);
    }

    @Test
    void testModifyChambre() {
        when(chambreRepository.save(any(Chambre.class))).thenReturn(chambre);

        Chambre modifiedChambre = chambreService.modifyChambre(chambre);

        assertNotNull(modifiedChambre);
        assertEquals(1L, modifiedChambre.getIdChambre());
        assertEquals(101, modifiedChambre.getNumeroChambre());
        assertEquals(TypeChambre.SIMPLE, modifiedChambre.getTypeC());

        verify(chambreRepository, times(1)).save(any(Chambre.class));
    }
} 