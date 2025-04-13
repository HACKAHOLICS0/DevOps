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
import tn.esprit.tpfoyer.control.FoyerRestController;
import tn.esprit.tpfoyer.entity.Foyer;
import tn.esprit.tpfoyer.service.IFoyerService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
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
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(foyerRestController).build();
        objectMapper = new ObjectMapper();

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
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].idFoyer").value(1))
                .andExpect(jsonPath("$[0].nomFoyer").value("Foyer A"))
                .andExpect(jsonPath("$[0].capaciteFoyer").value(200));

        verify(foyerService, times(1)).retrieveAllFoyers();
    }

    @Test
    void testRetrieveFoyer() throws Exception {
        when(foyerService.retrieveFoyer(1L)).thenReturn(foyer);

        mockMvc.perform(get("/foyer/retrieve-foyer/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.idFoyer").value(1))
                .andExpect(jsonPath("$.nomFoyer").value("Foyer A"));

        verify(foyerService, times(1)).retrieveFoyer(1L);
    }

    @Test
    void testAddFoyer() throws Exception {
        when(foyerService.addFoyer(any(Foyer.class))).thenReturn(foyer);

        mockMvc.perform(post("/foyer/add-foyer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(foyer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFoyer").value(1))
                .andExpect(jsonPath("$.nomFoyer").value("Foyer A"));

        verify(foyerService, times(1)).addFoyer(any(Foyer.class));
    }

    @Test
    void testModifyFoyer() throws Exception {
        when(foyerService.modifyFoyer(any(Foyer.class))).thenReturn(foyer);

        mockMvc.perform(put("/foyer/modify-foyer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(foyer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idFoyer").value(1));

        verify(foyerService, times(1)).modifyFoyer(any(Foyer.class));
    }

    @Test
    void testRemoveFoyer() throws Exception {
        doNothing().when(foyerService).removeFoyer(anyLong());

        mockMvc.perform(delete("/foyer/remove-foyer/{id}", 1))
                .andExpect(status().isOk());

        verify(foyerService, times(1)).removeFoyer(1L);
    }

    @Test
    void testGetFoyers_EmptyList() throws Exception {
        when(foyerService.retrieveAllFoyers()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/foyer/retrieve-all-foyers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());

        verify(foyerService, times(1)).retrieveAllFoyers();
    }

    @Test
    void testRetrieveFoyer_NotFound() throws Exception {
        when(foyerService.retrieveFoyer(999L)).thenReturn(null);

        mockMvc.perform(get("/foyer/retrieve-foyer/{id}", 999))
                .andExpect(status().isOk());

        verify(foyerService, times(1)).retrieveFoyer(999L);
    }

    @Test
    void testAddFoyer_WithNullValues() throws Exception {
        Foyer emptyFoyer = new Foyer();
        when(foyerService.addFoyer(any(Foyer.class))).thenReturn(emptyFoyer);

        mockMvc.perform(post("/foyer/add-foyer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(emptyFoyer)))
                .andExpect(status().isOk());

        verify(foyerService, times(1)).addFoyer(any(Foyer.class));
    }
} 