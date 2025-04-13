package tn.esprit.tpfoyer.service;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import tn.esprit.tpfoyer.control.FoyerRestController;
import tn.esprit.tpfoyer.entity.Foyer;

import java.util.Arrays;
import java.util.List;

class FoyerRestControllerTest {

    @Mock
    private IFoyerService foyerService;

    @InjectMocks
    private FoyerRestController foyerRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetFoyers() {
        Foyer foyer = new Foyer();
        when(foyerService.retrieveAllFoyers()).thenReturn(Arrays.asList(foyer));
        List<Foyer> foyers = foyerRestController.getFoyers();
        assertNotNull(foyers);
        assertEquals(1, foyers.size());
    }

    @Test
    void testRetrieveFoyer() {
        Foyer foyer = new Foyer();
        when(foyerService.retrieveFoyer(1L)).thenReturn(foyer);
        Foyer result = foyerRestController.retrieveFoyer(1L);
        assertNotNull(result);
    }

    @Test
    void testAddFoyer() {
        Foyer foyer = new Foyer();
        when(foyerService.addFoyer(any(Foyer.class))).thenReturn(foyer);
        Foyer result = foyerRestController.addFoyer(foyer);
        assertNotNull(result);
    }

    @Test
    void testRemoveFoyer() {
        doNothing().when(foyerService).removeFoyer(1L);
        foyerRestController.removeFoyer(1L);
        verify(foyerService, times(1)).removeFoyer(1L);
    }

    @Test
    void testModifyFoyer() {
        Foyer foyer = new Foyer();
        when(foyerService.modifyFoyer(any(Foyer.class))).thenReturn(foyer);
        Foyer result = foyerRestController.modifyFoyer(foyer);
        assertNotNull(result);
    }
}
