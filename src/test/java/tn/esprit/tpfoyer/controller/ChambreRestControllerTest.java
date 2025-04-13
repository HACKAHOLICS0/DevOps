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

import tn.esprit.tpfoyer.control.ChambreRestController;
import tn.esprit.tpfoyer.entity.Chambre;
import tn.esprit.tpfoyer.entity.TypeChambre;
import tn.esprit.tpfoyer.service.IChambreService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ChambreRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IChambreService chambreService;

    @InjectMocks
    private ChambreRestController chambreRestController;

    private Chambre chambre;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(chambreRestController).build();
        
        chambre = new Chambre();
        chambre.setIdChambre(1L);
        chambre.setNumeroChambre(101);
        chambre.setTypeC(TypeChambre.SIMPLE);
    }

    @Test
    void testGetChambres() throws Exception {
        List<Chambre> chambres = Arrays.asList(chambre);
        when(chambreService.retrieveAllChambres()).thenReturn(chambres);

        mockMvc.perform(get("/tpfoyer/chambre/retrieve-all-chambres"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idChambre").value(1))
                .andExpect(jsonPath("$[0].numeroChambre").value(101))
                .andExpect(jsonPath("$[0].typeC").value("SIMPLE"));
    }

    @Test
    void testGetChambre() throws Exception {
        when(chambreService.retrieveChambre(1L)).thenReturn(chambre);

        mockMvc.perform(get("/tpfoyer/chambre/retrieve-chambre/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idChambre").value(1))
                .andExpect(jsonPath("$.numeroChambre").value(101))
                .andExpect(jsonPath("$.typeC").value("SIMPLE"));
    }

    @Test
    void testAddChambre() throws Exception {
        when(chambreService.addChambre(any(Chambre.class))).thenReturn(chambre);

        mockMvc.perform(post("/tpfoyer/chambre/add-chambre")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"numeroChambre\":101,\"typeC\":\"SIMPLE\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idChambre").value(1))
                .andExpect(jsonPath("$.numeroChambre").value(101))
                .andExpect(jsonPath("$.typeC").value("SIMPLE"));
    }

    @Test
    void testRemoveChambre() throws Exception {
        mockMvc.perform(delete("/tpfoyer/chambre/remove-chambre/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testModifyChambre() throws Exception {
        when(chambreService.modifyChambre(any(Chambre.class))).thenReturn(chambre);

        mockMvc.perform(put("/tpfoyer/chambre/modify-chambre")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idChambre\":1,\"numeroChambre\":101,\"typeC\":\"SIMPLE\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idChambre").value(1))
                .andExpect(jsonPath("$.numeroChambre").value(101))
                .andExpect(jsonPath("$.typeC").value("SIMPLE"));
    }
} 