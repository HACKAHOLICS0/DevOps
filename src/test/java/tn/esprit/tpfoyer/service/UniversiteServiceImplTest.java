package tn.esprit.tpfoyer.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.repository.UniversiteRepository;

import java.util.List;

 class UniversiteServiceImplTest {

    @Mock
    private UniversiteRepository universiteRepository;

    private UniversiteServiceImpl universiteService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        universiteService = new UniversiteServiceImpl(universiteRepository);
    }

    @Test
     void testRetrieveAllUniversites() {
        Universite universite1 = new Universite();
        Universite universite2 = new Universite();
        when(universiteRepository.findAll()).thenReturn(List.of(universite1, universite2));

        List<Universite> result = universiteService.retrieveAllUniversites();
        assertEquals(2, result.size());
        verify(universiteRepository, times(1)).findAll();
    }

    @Test
    void testAddUniversite() {
        Universite universite = new Universite();
        when(universiteRepository.save(universite)).thenReturn(universite);

        Universite result = universiteService.addUniversite(universite);
        assertNotNull(result);
        verify(universiteRepository, times(1)).save(universite);
    }

    @Test
    void testRetrieveUniversite() {
        Long universiteId = 1L;
        Universite universite = new Universite();
        when(universiteRepository.findById(universiteId)).thenReturn(java.util.Optional.of(universite));

        Universite result = universiteService.retrieveUniversite(universiteId);
        assertNotNull(result);
        verify(universiteRepository, times(1)).findById(universiteId);
    }
}
