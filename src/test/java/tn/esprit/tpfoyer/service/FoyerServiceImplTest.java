package tn.esprit.tpfoyer.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;

import java.util.List;

 class FoyerServiceImplTest {

    @Mock
    private FoyerRepository foyerRepository;

    private FoyerServiceImpl foyerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        foyerService = new FoyerServiceImpl(foyerRepository);
    }

    @Test
     void testRetrieveAllFoyers() {
        Foyer foyer1 = new Foyer();
        Foyer foyer2 = new Foyer();
        when(foyerRepository.findAll()).thenReturn(List.of(foyer1, foyer2));

        List<Foyer> result = foyerService.retrieveAllFoyers();
        assertEquals(2, result.size());
        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    void testAddFoyer() {
        Foyer foyer = new Foyer();
        when(foyerRepository.save(foyer)).thenReturn(foyer);

        Foyer result = foyerService.addFoyer(foyer);
        assertNotNull(result);
        verify(foyerRepository, times(1)).save(foyer);
    }

    @Test
    void testRetrieveFoyer() {
        Long foyerId = 1L;
        Foyer foyer = new Foyer();
        when(foyerRepository.findById(foyerId)).thenReturn(java.util.Optional.of(foyer));

        Foyer result = foyerService.retrieveFoyer(foyerId);
        assertNotNull(result);
        verify(foyerRepository, times(1)).findById(foyerId);
    }
}
