package tn.esprit.tpfoyer.service;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;
import tn.esprit.tpfoyer.service.BlocServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class BlocServiceImplTest {

    @Mock
    private BlocRepository blocRepository;

    @InjectMocks
    private BlocServiceImpl blocService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllBlocs() {
        // Arrange
        List<Bloc> blocs = new ArrayList<>();
        Bloc bloc1 = new Bloc(1L, "Bloc A", 100, null, null);
        Bloc bloc2 = new Bloc(2L, "Bloc B", 200, null, null);
        blocs.add(bloc1);
        blocs.add(bloc2);
        when(blocRepository.findAll()).thenReturn(blocs);

        // Act
        List<Bloc> result = blocService.retrieveAllBlocs();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveBlocsSelonCapacite() {
        // Arrange
        List<Bloc> blocs = new ArrayList<>();
        Bloc bloc1 = new Bloc(1L, "Bloc A", 100, null, null);
        Bloc bloc2 = new Bloc(2L, "Bloc B", 200, null, null);
        blocs.add(bloc1);
        blocs.add(bloc2);
        when(blocRepository.findAll()).thenReturn(blocs);

        // Act
        List<Bloc> result = blocService.retrieveBlocsSelonCapacite(150);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Bloc B", result.get(0).getNomBloc());
    }

    @Test
    void testRetrieveBloc() {
        // Arrange
        Long blocId = 1L;
        Bloc bloc = new Bloc(1L, "Bloc A", 100, null, null);
        when(blocRepository.findById(blocId)).thenReturn(Optional.of(bloc));

        // Act
        Bloc result = blocService.retrieveBloc(blocId);

        // Assert
        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
    }

    @Test
    void testAddBloc() {
        // Arrange
        Bloc bloc = new Bloc(1L, "Bloc A", 100, null, null);
        when(blocRepository.save(bloc)).thenReturn(bloc);

        // Act
        Bloc result = blocService.addBloc(bloc);

        // Assert
        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testModifyBloc() {
        // Arrange
        Bloc bloc = new Bloc(1L, "Bloc A", 100, null, null);
        when(blocRepository.save(bloc)).thenReturn(bloc);

        // Act
        Bloc result = blocService.modifyBloc(bloc);

        // Assert
        assertNotNull(result);
        assertEquals("Bloc A", result.getNomBloc());
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
    void testRemoveBloc() {
        // Arrange
        Long blocId = 1L;
        doNothing().when(blocRepository).deleteById(blocId);

        // Act
        blocService.removeBloc(blocId);

        // Assert
        verify(blocRepository, times(1)).deleteById(blocId);
    }

    @Test
    void testTrouverBlocsSansFoyer() {
        // Arrange
        List<Bloc> blocs = new ArrayList<>();
        Bloc bloc1 = new Bloc(1L, "Bloc A", 100, null, null);
        blocs.add(bloc1);
        when(blocRepository.findAllByFoyerIsNull()).thenReturn(blocs);

        // Act
        List<Bloc> result = blocService.trouverBlocsSansFoyer();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Bloc A", result.get(0).getNomBloc());
    }

    @Test
    void testTrouverBlocsParNomEtCap() {
        // Arrange
        List<Bloc> blocs = new ArrayList<>();
        Bloc bloc1 = new Bloc(1L, "Bloc A", 100, null, null);
        blocs.add(bloc1);
        when(blocRepository.findAllByNomBlocAndCapaciteBloc("Bloc A", 100)).thenReturn(blocs);

        // Act
        List<Bloc> result = blocService.trouverBlocsParNomEtCap("Bloc A", 100);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Bloc A", result.get(0).getNomBloc());
    }
}
