package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.repository.BlocRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BlocServiceImplTest {

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private BlocServiceImpl blocService;

    private Bloc bloc;
    private Chambre chambre;

    @BeforeEach
    void setUp() {
        bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc A");
        bloc.setCapaciteBloc(100);

        chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(101);
        chambre.setTypeC(TypeChambre.SIMPLE);
        chambre.setBloc(bloc);
    }

    @Test
    void testAddBloc() {
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        Bloc savedBloc = blocService.addBloc(bloc);

        assertNotNull(savedBloc);
        assertEquals(1L, savedBloc.getIdBloc());
        assertEquals("Bloc A", savedBloc.getNomBloc());
        assertEquals(100, savedBloc.getCapaciteBloc());

        verify(blocRepository, times(1)).save(any(Bloc.class));
    }

    @Test
    void testRetrieveAllBlocs() {
        List<Bloc> blocs = Arrays.asList(bloc);
        when(blocRepository.findAll()).thenReturn(blocs);

        List<Bloc> retrievedBlocs = blocService.retrieveAllBlocs();

        assertNotNull(retrievedBlocs);
        assertEquals(1, retrievedBlocs.size());
        assertEquals(bloc, retrievedBlocs.get(0));

        verify(blocRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveBloc() {
        when(blocRepository.findById(1L)).thenReturn(Optional.of(bloc));

        Bloc retrievedBloc = blocService.retrieveBloc(1L);

        assertNotNull(retrievedBloc);
        assertEquals(1L, retrievedBloc.getIdBloc());
        assertEquals("Bloc A", retrievedBloc.getNomBloc());
        assertEquals(100, retrievedBloc.getCapaciteBloc());

        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    void testRetrieveBloc_NotFound() {
        when(blocRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> blocService.retrieveBloc(1L));

        verify(blocRepository, times(1)).findById(1L);
    }

    @Test
    void testRemoveBloc() {
        doNothing().when(blocRepository).deleteById(1L);

        blocService.removeBloc(1L);

        verify(blocRepository, times(1)).deleteById(1L);
    }

    @Test
    void testModifyBloc() {
        when(blocRepository.save(any(Bloc.class))).thenReturn(bloc);

        Bloc modifiedBloc = blocService.modifyBloc(bloc);

        assertNotNull(modifiedBloc);
        assertEquals(1L, modifiedBloc.getIdBloc());
        assertEquals("Bloc A", modifiedBloc.getNomBloc());
        assertEquals(100, modifiedBloc.getCapaciteBloc());

        verify(blocRepository, times(1)).save(any(Bloc.class));
    }
} 