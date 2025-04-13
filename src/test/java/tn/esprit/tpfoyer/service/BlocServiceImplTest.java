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
     @Test
     void testUpdateBloc() {
         Bloc bloc = new Bloc();
         bloc.setIdBloc(1L);
         bloc.setNomBloc("Bloc A");

         when(blocRepository.save(bloc)).thenReturn(bloc);

         Bloc updatedBloc = blocService.modifyBloc(bloc);

         assertEquals("Bloc A", updatedBloc.getNomBloc());
         verify(blocRepository, times(1)).save(bloc);
     }
     @Test
     void testRemoveBloc() {
         Long idBloc = 1L;

         blocService.removeBloc(idBloc);

         verify(blocRepository, times(1)).deleteById(idBloc);
     }
     @Test
     void testRetrieveBlocNotFound() {
         Long blocId = 99L;
         when(blocRepository.findById(blocId)).thenReturn(java.util.Optional.empty());

         RuntimeException exception = assertThrows(RuntimeException.class, () -> {
             blocService.retrieveBloc(blocId);
         });

         assertEquals("Bloc not found with id: 99", exception.getMessage());
         verify(blocRepository, times(1)).findById(blocId);
     }


 }
