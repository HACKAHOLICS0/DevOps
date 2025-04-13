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

import tn.esprit.tpfoyer.control.UniversiteRestController;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.service.IUniversiteService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class UniversiteRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IUniversiteService universiteService;

    @InjectMocks
    private UniversiteRestController universiteRestController;

    private Universite universite;
    private Foyer foyer;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(universiteRestController).build();
        
        universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("Universite A");
        universite.setAdresse("123 Rue Test");

        foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer A");
        foyer.setCapaciteFoyer(500);
        universite.setFoyer(foyer);
    }

    @Test
    void testGetUniversites() throws Exception {
        List<Universite> universites = Arrays.asList(universite);
        when(universiteService.retrieveAllUniversites()).thenReturn(universites);

        mockMvc.perform(get("/tpfoyer/universite/retrieve-all-universites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idUniversite").value(1))
                .andExpect(jsonPath("$[0].nomUniversite").value("Universite A"))
                .andExpect(jsonPath("$[0].adresse").value("123 Rue Test"));
    }

    @Test
    void testGetUniversite() throws Exception {
        when(universiteService.retrieveUniversite(1L)).thenReturn(universite);

        mockMvc.perform(get("/tpfoyer/universite/retrieve-universite/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUniversite").value(1))
                .andExpect(jsonPath("$.nomUniversite").value("Universite A"))
                .andExpect(jsonPath("$.adresse").value("123 Rue Test"));
    }

    @Test
    void testAddUniversite() throws Exception {
        when(universiteService.addUniversite(any(Universite.class))).thenReturn(universite);

        mockMvc.perform(post("/tpfoyer/universite/add-universite")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nomUniversite\":\"Universite A\",\"adresse\":\"123 Rue Test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUniversite").value(1))
                .andExpect(jsonPath("$.nomUniversite").value("Universite A"))
                .andExpect(jsonPath("$.adresse").value("123 Rue Test"));
    }

    @Test
    void testRemoveUniversite() throws Exception {
        mockMvc.perform(delete("/tpfoyer/universite/remove-universite/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testModifyUniversite() throws Exception {
        when(universiteService.modifyUniversite(any(Universite.class))).thenReturn(universite);

        mockMvc.perform(put("/tpfoyer/universite/modify-universite")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idUniversite\":1,\"nomUniversite\":\"Universite A\",\"adresse\":\"123 Rue Test\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUniversite").value(1))
                .andExpect(jsonPath("$.nomUniversite").value("Universite A"))
                .andExpect(jsonPath("$.adresse").value("123 Rue Test"));
    }

    @Test
    void testAffecterFoyerAUniversite() throws Exception {
        when(universiteService.affecterFoyerAUniversite(1L, 1L)).thenReturn(universite);

        mockMvc.perform(put("/tpfoyer/universite/1/foyer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUniversite").value(1))
                .andExpect(jsonPath("$.nomUniversite").value("Universite A"))
                .andExpect(jsonPath("$.adresse").value("123 Rue Test"));
    }
} 