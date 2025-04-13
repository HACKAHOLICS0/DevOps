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
import tn.esprit.tpfoyer.control.EtudiantRestController;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.service.IEtudiantService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EtudiantRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IEtudiantService etudiantService;

    @InjectMocks
    private EtudiantRestController etudiantRestController;

    private Etudiant etudiant;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(etudiantRestController)
                .build();
        
        etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEtudiant("Doe");
        etudiant.setPrenomEtudiant("John");
        etudiant.setCinEtudiant(12345678L);
    }

    @Test
    void testGetEtudiants() throws Exception {
        List<Etudiant> etudiants = Arrays.asList(etudiant);
        when(etudiantService.retrieveAllEtudiants()).thenReturn(etudiants);

        mockMvc.perform(get("/etudiant/retrieve-all-etudiants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idEtudiant").value(1))
                .andExpect(jsonPath("$[0].nomEtudiant").value("Doe"))
                .andExpect(jsonPath("$[0].prenomEtudiant").value("John"))
                .andExpect(jsonPath("$[0].cinEtudiant").value(12345678));
    }

    @Test
    void testGetEtudiant() throws Exception {
        when(etudiantService.retrieveEtudiant(1L)).thenReturn(etudiant);

        mockMvc.perform(get("/etudiant/retrieve-etudiant/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEtudiant").value(1))
                .andExpect(jsonPath("$.nomEtudiant").value("Doe"))
                .andExpect(jsonPath("$.prenomEtudiant").value("John"))
                .andExpect(jsonPath("$.cinEtudiant").value(12345678));
    }

    @Test
    void testAddEtudiant() throws Exception {
        when(etudiantService.addEtudiant(any(Etudiant.class))).thenReturn(etudiant);

        mockMvc.perform(post("/etudiant/add-etudiant")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nomEtudiant\":\"Doe\",\"prenomEtudiant\":\"John\",\"cinEtudiant\":12345678}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEtudiant").value(1))
                .andExpect(jsonPath("$.nomEtudiant").value("Doe"))
                .andExpect(jsonPath("$.prenomEtudiant").value("John"))
                .andExpect(jsonPath("$.cinEtudiant").value(12345678));
    }

    @Test
    void testRemoveEtudiant() throws Exception {
        mockMvc.perform(delete("/etudiant/remove-etudiant/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testModifyEtudiant() throws Exception {
        when(etudiantService.modifyEtudiant(any(Etudiant.class))).thenReturn(etudiant);

        mockMvc.perform(put("/etudiant/modify-etudiant")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idEtudiant\":1,\"nomEtudiant\":\"Doe\",\"prenomEtudiant\":\"John\",\"cinEtudiant\":12345678}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEtudiant").value(1))
                .andExpect(jsonPath("$.nomEtudiant").value("Doe"))
                .andExpect(jsonPath("$.prenomEtudiant").value("John"))
                .andExpect(jsonPath("$.cinEtudiant").value(12345678));
    }

    @Test
    void testGetEtudiantParCin() throws Exception {
        when(etudiantService.recupererEtudiantParCin(12345678L)).thenReturn(etudiant);

        mockMvc.perform(get("/etudiant/retrieve-etudiant-cin/12345678"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idEtudiant").value(1))
                .andExpect(jsonPath("$.cinEtudiant").value(12345678));
    }

    @Test
    void testGetEtudiants_EmptyList() throws Exception {
        when(etudiantService.retrieveAllEtudiants()).thenReturn(Arrays.asList());

        mockMvc.perform(get("/etudiant/retrieve-all-etudiants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void testGetEtudiant_NotFound() throws Exception {
        when(etudiantService.retrieveEtudiant(999L)).thenReturn(null);

        mockMvc.perform(get("/etudiant/retrieve-etudiant/999"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetEtudiantParCin_NotFound() throws Exception {
        when(etudiantService.recupererEtudiantParCin(99999999L)).thenReturn(null);

        mockMvc.perform(get("/etudiant/retrieve-etudiant-cin/99999999"))
                .andExpect(status().isOk());
    }
} 