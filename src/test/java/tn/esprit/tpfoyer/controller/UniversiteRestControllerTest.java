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
import tn.esprit.tpfoyer.control.UniversiteRestController;
import tn.esprit.tpfoyer.entity.Universite;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.service.IUniversiteService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
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
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(universiteRestController).build();
        objectMapper = new ObjectMapper();

        foyer = new Foyer();
        foyer.setIdFoyer(1L);
        foyer.setNomFoyer("Foyer A");
        foyer.setCapaciteFoyer(200);

        universite = new Universite();
        universite.setIdUniversite(1L);
        universite.setNomUniversite("University A");
        universite.setAdresse("123 Main St");
        universite.setFoyer(foyer);
    }

    @Test
    void testGetUniversites() throws Exception {
        List<Universite> universites = Arrays.asList(universite);
        when(universiteService.retrieveAllUniversites()).thenReturn(universites);

        mockMvc.perform(get("/universite/retrieve-all-universites"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idUniversite").value(1))
                .andExpect(jsonPath("$[0].nomUniversite").value("University A"))
                .andExpect(jsonPath("$[0].adresse").value("123 Main St"));

        verify(universiteService, times(1)).retrieveAllUniversites();
    }

    @Test
    void testRetrieveUniversite() throws Exception {
        when(universiteService.retrieveUniversite(1L)).thenReturn(universite);

        mockMvc.perform(get("/universite/retrieve-universite/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idUniversite").value(1))
                .andExpect(jsonPath("$.nomUniversite").value("University A"));

        verify(universiteService, times(1)).retrieveUniversite(1L);
    }

    @Test
    void testAddUniversite() throws Exception {
        when(universiteService.addUniversite(any(Universite.class))).thenReturn(universite);

        mockMvc.perform(post("/universite/add-universite")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(universite)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUniversite").value(1))
                .andExpect(jsonPath("$.nomUniversite").value("University A"));

        verify(universiteService, times(1)).addUniversite(any(Universite.class));
    }

    @Test
    void testModifyUniversite() throws Exception {
        when(universiteService.modifyUniversite(any(Universite.class))).thenReturn(universite);

        mockMvc.perform(put("/universite/modify-universite")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(universite)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUniversite").value(1));

        verify(universiteService, times(1)).modifyUniversite(any(Universite.class));
    }

    @Test
    void testRemoveUniversite() throws Exception {
        doNothing().when(universiteService).removeUniversite(anyLong());

        mockMvc.perform(delete("/universite/remove-universite/{id}", 1))
                .andExpect(status().isOk());

        verify(universiteService, times(1)).removeUniversite(1L);
    }

    @Test
    void testAffecterFoyerAUniversite() throws Exception {
        when(universiteService.affecterFoyerAUniversite(1L, 1L)).thenReturn(universite);

        mockMvc.perform(put("/universite/{universite-id}/foyer/{foyer-id}", 1, 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idUniversite").value(1))
                .andExpect(jsonPath("$.foyer.idFoyer").value(1));

        verify(universiteService, times(1)).affecterFoyerAUniversite(1L, 1L);
    }

    @Test
    void testGetUniversites_EmptyList() throws Exception {
        when(universiteService.retrieveAllUniversites()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/universite/retrieve-all-universites"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(universiteService, times(1)).retrieveAllUniversites();
    }

    @Test
    void testRetrieveUniversite_NotFound() throws Exception {
        when(universiteService.retrieveUniversite(999L)).thenReturn(null);

        mockMvc.perform(get("/universite/retrieve-universite/{id}", 999))
                .andExpect(status().isOk());

        verify(universiteService, times(1)).retrieveUniversite(999L);
    }

    @Test
    void testAffecterFoyerAUniversite_NotFound() throws Exception {
        when(universiteService.affecterFoyerAUniversite(999L, 999L)).thenReturn(null);

        mockMvc.perform(put("/universite/{universite-id}/foyer/{foyer-id}", 999, 999))
                .andExpect(status().isOk());

        verify(universiteService, times(1)).affecterFoyerAUniversite(999L, 999L);
    }
} 