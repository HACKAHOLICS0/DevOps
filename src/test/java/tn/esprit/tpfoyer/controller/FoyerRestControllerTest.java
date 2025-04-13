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
import tn.esprit.tpfoyer.control.FoyerRestController;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.service.IFoyerService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class FoyerRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IFoyerService foyerService;

    @InjectMocks
    private FoyerRestController foyerRestController;

    private Foyer foyer;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(foyerRestController)
                .build();
        
        foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer A");
        foyer.setCapaciteFoyer(200);
    }

    @Test
    void testGetFoyers() throws Exception {
        List<Foyer> foyers = Arrays.asList(foyer);
        when(foyerService.retrieveAllFoyers()).thenReturn(foyers);

        mockMvc.perform(get("/foyer/retrieve-all-foyers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idFoyer").value(1))
                .andExpect(jsonPath("$[0].nomFoyer").value("Foyer A"))
                .andExpect(jsonPath("$[0].capaciteFoyer").value(200));
    }

    @Test
    void testGetFoyer() throws Exception {
        when(foyerService.retrieveFoyer(1L)).thenReturn(foyer);

        mockMvc.perform(get("/foyer/retrieve-foyer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFoyer").value(1))
                .andExpect(jsonPath("$.nomFoyer").value("Foyer A"))
                .andExpect(jsonPath("$.capaciteFoyer").value(200));
    }

    @Test
    void testAddFoyer() throws Exception {
        when(foyerService.addFoyer(any(Foyer.class))).thenReturn(foyer);

        mockMvc.perform(post("/foyer/add-foyer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nomFoyer\":\"Foyer A\",\"capaciteFoyer\":200}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFoyer").value(1))
                .andExpect(jsonPath("$.nomFoyer").value("Foyer A"))
                .andExpect(jsonPath("$.capaciteFoyer").value(200));
    }

    @Test
    void testRemoveFoyer() throws Exception {
        mockMvc.perform(delete("/foyer/remove-foyer/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testModifyFoyer() throws Exception {
        when(foyerService.modifyFoyer(any(Foyer.class))).thenReturn(foyer);

        mockMvc.perform(put("/foyer/modify-foyer")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idFoyer\":1,\"nomFoyer\":\"Foyer A\",\"capaciteFoyer\":200}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFoyer").value(1))
                .andExpect(jsonPath("$.nomFoyer").value("Foyer A"))
                .andExpect(jsonPath("$.capaciteFoyer").value(200));
    }
} 