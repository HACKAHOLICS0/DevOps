package tn.esprit.tpfoyer.service;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import tn.esprit.tpfoyer.control.UniversiteRestController;
import tn.esprit.tpfoyer.entity.Universite;

import java.util.Arrays;
import java.util.List;

class UniversiteRestControllerTest {

    @Mock
    private IUniversiteService universiteService;

    @InjectMocks
    private UniversiteRestController universiteRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUniversites() {
        Universite universite = new Universite();
        when(universiteService.retrieveAllUniversites()).thenReturn(Arrays.asList(universite));
        List<Universite> universites = universiteRestController.getUniversites();
        assertNotNull(universites);
        assertEquals(1, universites.size());
    }

    @Test
    void testRetrieveUniversite() {
        Universite universite = new Universite();
        when(universiteService.retrieveUniversite(1L)).thenReturn(universite);
        Universite result = universiteRestController.retrieveUniversite(1L);
        assertNotNull(result);
    }

    @Test
    void testAddUniversite() {
        Universite universite = new Universite();
        when(universiteService.addUniversite(any(Universite.class))).thenReturn(universite);
        Universite result = universiteRestController.addUniversite(universite);
        assertNotNull(result);
    }

    @Test
    void testRemoveUniversite() {
        doNothing().when(universiteService).removeUniversite(1L);
        universiteRestController.removeUniversite(1L);
        verify(universiteService, times(1)).removeUniversite(1L);
    }

}