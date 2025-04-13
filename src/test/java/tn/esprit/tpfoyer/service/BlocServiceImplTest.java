package tn.esprit.tpfoyer.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.repository.BlocRepository;

import java.util.Arrays;
import java.util.List;

 class BlocServiceImplTest {

    @Mock
    private BlocRepository blocRepository;

    private BlocServiceImpl blocService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        blocService = new BlocServiceImpl(blocRepository);
    }

    @Test
    void testRetrieveAllBlocs() {
        Bloc bloc1 = new Bloc();
        Bloc bloc2 = new Bloc();
        when(blocRepository.findAll()).thenReturn(Arrays.asList(bloc1, bloc2));

        List<Bloc> result = blocService.retrieveAllBlocs();
        assertEquals(2, result.size());
        verify(blocRepository, times(1)).findAll();
    }

    @Test
     void testAddBloc() {
        Bloc bloc = new Bloc();
        when(blocRepository.save(bloc)).thenReturn(bloc);

        Bloc result = blocService.addBloc(bloc);
        assertNotNull(result);
        verify(blocRepository, times(1)).save(bloc);
    }

    @Test
     void testRetrieveBloc() {
        Long blocId = 1L;
        Bloc bloc = new Bloc();
        when(blocRepository.findById(blocId)).thenReturn(java.util.Optional.of(bloc));

        Bloc result = blocService.retrieveBloc(blocId);
        assertNotNull(result);
        verify(blocRepository, times(1)).findById(blocId);
    }
}
