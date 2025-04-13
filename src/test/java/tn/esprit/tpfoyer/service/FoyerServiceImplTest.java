package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.repository.FoyerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FoyerServiceImplTest {

    @Mock
    private FoyerRepository foyerRepository;

    @InjectMocks
    private FoyerServiceImpl foyerService;

    private Foyer foyer;
    private Bloc bloc;

    @BeforeEach
    void setUp() {
        foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer A");
        foyer.setCapaciteFoyer(500);

        bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc A");
        bloc.setCapaciteBloc(100);
        bloc.setFoyer(foyer);
    }

    @Test
    void testAddFoyer() {
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        Foyer savedFoyer = foyerService.addFoyer(foyer);

        assertNotNull(savedFoyer);
        assertEquals(1L, savedFoyer.getIdFoyer());
        assertEquals("Foyer A", savedFoyer.getNomFoyer());
        assertEquals(500, savedFoyer.getCapaciteFoyer());

        verify(foyerRepository, times(1)).save(any(Foyer.class));
    }

    @Test
    void testRetrieveAllFoyers() {
        List<Foyer> foyers = Arrays.asList(foyer);
        when(foyerRepository.findAll()).thenReturn(foyers);

        List<Foyer> retrievedFoyers = foyerService.retrieveAllFoyers();

        assertNotNull(retrievedFoyers);
        assertEquals(1, retrievedFoyers.size());
        assertEquals(foyer, retrievedFoyers.get(0));

        verify(foyerRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveFoyer() {
        when(foyerRepository.findById(1L)).thenReturn(Optional.of(foyer));

        Foyer retrievedFoyer = foyerService.retrieveFoyer(1L);

        assertNotNull(retrievedFoyer);
        assertEquals(1L, retrievedFoyer.getIdFoyer());
        assertEquals("Foyer A", retrievedFoyer.getNomFoyer());
        assertEquals(500, retrievedFoyer.getCapaciteFoyer());

        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveFoyer_NotFound() {
        when(foyerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> foyerService.retrieveFoyer(1L));

        verify(foyerRepository, times(1)).findById(1L);
    }

    @Test
    void testRemoveFoyer() {
        doNothing().when(foyerRepository).deleteById(1L);

        foyerService.removeFoyer(1L);

        verify(foyerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testModifyFoyer() {
        when(foyerRepository.save(any(Foyer.class))).thenReturn(foyer);

        Foyer modifiedFoyer = foyerService.modifyFoyer(foyer);

        assertNotNull(modifiedFoyer);
        assertEquals(1L, modifiedFoyer.getIdFoyer());
        assertEquals("Foyer A", modifiedFoyer.getNomFoyer());
        assertEquals(500, modifiedFoyer.getCapaciteFoyer());

        verify(foyerRepository, times(1)).save(any(Foyer.class));
    }
} 