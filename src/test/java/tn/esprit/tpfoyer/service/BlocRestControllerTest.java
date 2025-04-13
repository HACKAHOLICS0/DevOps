package tn.esprit.tpfoyer.service;


import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import tn.esprit.tpfoyer.control.BlocRestController;
import tn.esprit.tpfoyer.entity.Bloc;

import java.util.Arrays;
import java.util.List;

class BlocRestControllerTest {

    @Mock
    private IBlocService blocService;

    @InjectMocks
    private BlocRestController blocRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBlocs() {
        Bloc bloc = new Bloc();
        when(blocService.retrieveAllBlocs()).thenReturn(Arrays.asList(bloc));
        List<Bloc> blocs = blocRestController.getBlocs();
        assertNotNull(blocs);
        assertEquals(1, blocs.size());
    }

    @Test
    void testRetrieveBloc() {
        Bloc bloc = new Bloc();
        when(blocService.retrieveBloc(1L)).thenReturn(bloc);
        Bloc result = blocRestController.retrieveBloc(1L);
        assertNotNull(result);
    }

    @Test
    void testAddBloc() {
        Bloc bloc = new Bloc();
        when(blocService.addBloc(any(Bloc.class))).thenReturn(bloc);
        Bloc result = blocRestController.addBloc(bloc);
        assertNotNull(result);
    }

    @Test
    void testRemoveBloc() {
        doNothing().when(blocService).removeBloc(1L);
        blocRestController.removeBloc(1L);
        verify(blocService, times(1)).removeBloc(1L);
    }

    @Test
    void testModifyBloc() {
        Bloc bloc = new Bloc();
        when(blocService.modifyBloc(any(Bloc.class))).thenReturn(bloc);
        Bloc result = blocRestController.modifyBloc(bloc);
        assertNotNull(result);
    }

    @Test
    void testGetBlocswirhoutFoyer() {
        Bloc bloc = new Bloc();
        when(blocService.trouverBlocsSansFoyer()).thenReturn(Arrays.asList(bloc));
        List<Bloc> blocs = blocRestController.getBlocswirhoutFoyer();
        assertNotNull(blocs);
    }
}
