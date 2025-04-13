package tn.esprit.tpfoyer.controller;

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
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.service.IBlocService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
    private Chambre chambre;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(blocRestController).build();
        
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
    void testGetBlocs() throws Exception {
        List<Bloc> blocs = Arrays.asList(bloc);
        when(blocService.retrieveAllBlocs()).thenReturn(blocs);

        mockMvc.perform(get("/tpfoyer/bloc/retrieve-all-blocs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idBloc").value(1))
                .andExpect(jsonPath("$[0].nomBloc").value("Bloc A"))
                .andExpect(jsonPath("$[0].capaciteBloc").value(100));
    }

    @Test
    void testGetBloc() throws Exception {
        when(blocService.retrieveBloc(1L)).thenReturn(bloc);

        mockMvc.perform(get("/tpfoyer/bloc/retrieve-bloc/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBloc").value(1))
                .andExpect(jsonPath("$.nomBloc").value("Bloc A"))
                .andExpect(jsonPath("$.capaciteBloc").value(100));
    }

    @Test
    void testAddBloc() throws Exception {
        when(blocService.addBloc(any(Bloc.class))).thenReturn(bloc);

        mockMvc.perform(post("/tpfoyer/bloc/add-bloc")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nomBloc\":\"Bloc A\",\"capaciteBloc\":100}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBloc").value(1))
                .andExpect(jsonPath("$.nomBloc").value("Bloc A"))
                .andExpect(jsonPath("$.capaciteBloc").value(100));
    }

    @Test
    void testRemoveBloc() throws Exception {
        mockMvc.perform(delete("/tpfoyer/bloc/remove-bloc/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testModifyBloc() throws Exception {
        when(blocService.modifyBloc(any(Bloc.class))).thenReturn(bloc);

        mockMvc.perform(put("/tpfoyer/bloc/modify-bloc")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idBloc\":1,\"nomBloc\":\"Bloc A\",\"capaciteBloc\":100}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBloc").value(1))
                .andExpect(jsonPath("$.nomBloc").value("Bloc A"))
                .andExpect(jsonPath("$.capaciteBloc").value(100));
    }
} 