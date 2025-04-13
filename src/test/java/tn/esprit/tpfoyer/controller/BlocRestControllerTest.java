package tn.esprit.tpfoyer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tn.esprit.tpfoyer.control.BlocRestController;
import tn.esprit.tpfoyer.entity.Bloc;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.service.IBlocService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class BlocRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IBlocService blocService;

    @InjectMocks
    private BlocRestController blocRestController;

    private Bloc bloc;
    private Foyer foyer;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(blocRestController).build();
        objectMapper = new ObjectMapper();

        foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer A");
        foyer.setCapaciteFoyer(200);

        bloc = new Bloc();
        bloc.setIdBloc(1L);
        bloc.setNomBloc("Bloc A");
        bloc.setCapaciteBloc(100);
        bloc.setFoyer(foyer);
    }

    @Test
    void testGetBlocs() throws Exception {
        List<Bloc> blocs = Arrays.asList(bloc);
        when(blocService.retrieveAllBlocs()).thenReturn(blocs);

        mockMvc.perform(get("/bloc/retrieve-all-blocs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idBloc").value(1))
                .andExpect(jsonPath("$[0].nomBloc").value("Bloc A"))
                .andExpect(jsonPath("$[0].capaciteBloc").value(100));

        verify(blocService, times(1)).retrieveAllBlocs();
    }

    @Test
    void testRetrieveBloc() throws Exception {
        when(blocService.retrieveBloc(1L)).thenReturn(bloc);

        mockMvc.perform(get("/bloc/retrieve-bloc/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idBloc").value(1))
                .andExpect(jsonPath("$.nomBloc").value("Bloc A"));

        verify(blocService, times(1)).retrieveBloc(1L);
    }

    @Test
    void testAddBloc() throws Exception {
        when(blocService.addBloc(any(Bloc.class))).thenReturn(bloc);

        mockMvc.perform(post("/bloc/add-bloc")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bloc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBloc").value(1))
                .andExpect(jsonPath("$.nomBloc").value("Bloc A"));

        verify(blocService, times(1)).addBloc(any(Bloc.class));
    }

    @Test
    void testModifyBloc() throws Exception {
        when(blocService.modifyBloc(any(Bloc.class))).thenReturn(bloc);

        mockMvc.perform(put("/bloc/modify-bloc")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bloc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBloc").value(1));

        verify(blocService, times(1)).modifyBloc(any(Bloc.class));
    }

    @Test
    void testRemoveBloc() throws Exception {
        doNothing().when(blocService).removeBloc(anyLong());

        mockMvc.perform(delete("/bloc/remove-bloc/{id}", 1))
                .andExpect(status().isOk());

        verify(blocService, times(1)).removeBloc(1L);
    }

    @Test
    void testGetBlocsWithoutFoyer() throws Exception {
        Bloc blocWithoutFoyer = new Bloc();
        blocWithoutFoyer.setIdBloc(2L);
        blocWithoutFoyer.setNomBloc("Bloc B");
        blocWithoutFoyer.setCapaciteBloc(150);

        List<Bloc> blocsWithoutFoyer = Arrays.asList(blocWithoutFoyer);
        when(blocService.trouverBlocsSansFoyer()).thenReturn(blocsWithoutFoyer);

        mockMvc.perform(get("/bloc/trouver-blocs-sans-foyer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idBloc").value(2))
                .andExpect(jsonPath("$[0].nomBloc").value("Bloc B"))
                .andExpect(jsonPath("$[0].foyer").isEmpty());

        verify(blocService, times(1)).trouverBlocsSansFoyer();
    }

    @Test
    void testRecuperBlocsParNomEtCap() throws Exception {
        List<Bloc> blocs = Arrays.asList(bloc);
        when(blocService.trouverBlocsParNomEtCap("Bloc A", 100L)).thenReturn(blocs);

        mockMvc.perform(get("/bloc/get-bloc-nb-c/{nb}/{c}", "Bloc A", 100))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idBloc").value(1))
                .andExpect(jsonPath("$[0].nomBloc").value("Bloc A"))
                .andExpect(jsonPath("$[0].capaciteBloc").value(100));

        verify(blocService, times(1)).trouverBlocsParNomEtCap("Bloc A", 100L);
    }

    @Test
    void testGetBlocs_EmptyList() throws Exception {
        when(blocService.retrieveAllBlocs()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/bloc/retrieve-all-blocs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(blocService, times(1)).retrieveAllBlocs();
    }

    @Test
    void testRetrieveBloc_NotFound() throws Exception {
        when(blocService.retrieveBloc(999L)).thenReturn(null);

        mockMvc.perform(get("/bloc/retrieve-bloc/{id}", 999))
                .andExpect(status().isOk());

        verify(blocService, times(1)).retrieveBloc(999L);
    }

    @Test
    void testGetBlocsWithoutFoyer_EmptyList() throws Exception {
        when(blocService.trouverBlocsSansFoyer()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/bloc/trouver-blocs-sans-foyer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(blocService, times(1)).trouverBlocsSansFoyer();
    }

    @Test
    void testRecuperBlocsParNomEtCap_NotFound() throws Exception {
        when(blocService.trouverBlocsParNomEtCap("NonExistent", 0L)).thenReturn(Arrays.asList());

        mockMvc.perform(get("/bloc/get-bloc-nb-c/{nb}/{c}", "NonExistent", 0))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(blocService, times(1)).trouverBlocsParNomEtCap("NonExistent", 0L);
    }
} 