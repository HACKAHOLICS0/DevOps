package tn.esprit.tpfoyer.service;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import tn.esprit.tpfoyer.control.ChambreRestController;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;

import java.util.Arrays;
import java.util.List;

class ChambreRestControllerTest {

    @Mock
    private IChambreService chambreService;

    @InjectMocks
    private ChambreRestController chambreRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetChambres() {
        Chambre chambre = new Chambre();
        when(chambreService.retrieveAllChambres()).thenReturn(Arrays.asList(chambre));
        List<Chambre> chambres = chambreRestController.getChambres();
        assertNotNull(chambres);
        assertEquals(1, chambres.size());
    }

    @Test
    void testRetrieveChambre() {
        Chambre chambre = new Chambre();
        when(chambreService.retrieveChambre(1L)).thenReturn(chambre);
        Chambre result = chambreRestController.retrieveChambre(1L);
        assertNotNull(result);
    }

    @Test
    void testAddChambre() {
        Chambre chambre = new Chambre();
        when(chambreService.addChambre(any(Chambre.class))).thenReturn(chambre);
        Chambre result = chambreRestController.addChambre(chambre);
        assertNotNull(result);
    }

    @Test
    void testRemoveChambre() {
        doNothing().when(chambreService).removeChambre(1L);
        chambreRestController.removeChambre(1L);
        verify(chambreService, times(1)).removeChambre(1L);
    }

    @Test
    void testModifyChambre() {
        Chambre chambre = new Chambre();
        when(chambreService.modifyChambre(any(Chambre.class))).thenReturn(chambre);
        Chambre result = chambreRestController.modifyChambre(chambre);
        assertNotNull(result);
    }

    @Test
    void testTrouverChambresSelonTyp() {
        Chambre chambre = new Chambre();
        when(chambreService.recupererChambresSelonTyp(TypeChambre.SIMPLE)).thenReturn(Arrays.asList(chambre));
        List<Chambre> chambres = chambreRestController.trouverChSelonTC(TypeChambre.SIMPLE);
        assertNotNull(chambres);
    }
}
