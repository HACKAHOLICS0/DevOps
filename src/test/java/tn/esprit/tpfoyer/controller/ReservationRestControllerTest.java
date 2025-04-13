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
import tn.esprit.tpfoyer.control.ReservationRestController;
import tn.esprit.tpfoyer.entity.Etudiant;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.service.IReservationService;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ReservationRestControllerTest {

    private MockMvc mockMvc;

    @Mock
    private IReservationService reservationService;

    @InjectMocks
    private ReservationRestController reservationRestController;

    private Reservation reservation;
    private Etudiant etudiant;
    private Set<Etudiant> etudiants;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reservationRestController)
                .build();
        
        etudiant = new Etudiant();
        etudiant.setIdEtudiant(1L);
        etudiant.setNomEtudiant("Doe");
        etudiant.setPrenomEtudiant("John");
        etudiant.setCinEtudiant(12345678L);

        etudiants = new HashSet<>();
        etudiants.add(etudiant);

        reservation = new Reservation();
        reservation.setIdReservation("RES-2024-001");
        reservation.setAnneeUniversitaire(new Date());
        reservation.setEstValide(true);
        reservation.setEtudiants(etudiants);
    }

    @Test
    void testGetReservations() throws Exception {
        List<Reservation> reservations = Arrays.asList(reservation);
        when(reservationService.retrieveAllReservations()).thenReturn(reservations);

        mockMvc.perform(get("/reservation/retrieve-all-reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idReservation").value("RES-2024-001"))
                .andExpect(jsonPath("$[0].estValide").value(true))
                .andExpect(jsonPath("$[0].etudiants[0].idEtudiant").value(1))
                .andExpect(jsonPath("$[0].etudiants[0].nomEtudiant").value("Doe"));
    }

    @Test
    void testGetReservation() throws Exception {
        when(reservationService.retrieveReservation("RES-2024-001")).thenReturn(reservation);

        mockMvc.perform(get("/reservation/retrieve-reservation/RES-2024-001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value("RES-2024-001"))
                .andExpect(jsonPath("$.estValide").value(true))
                .andExpect(jsonPath("$.etudiants[0].idEtudiant").value(1))
                .andExpect(jsonPath("$.etudiants[0].nomEtudiant").value("Doe"));
    }

    @Test
    void testAddReservation() throws Exception {
        when(reservationService.addReservation(any(Reservation.class))).thenReturn(reservation);

        mockMvc.perform(post("/reservation/add-reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idReservation\":\"RES-2024-001\",\"estValide\":true,\"etudiants\":[{\"idEtudiant\":1}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value("RES-2024-001"))
                .andExpect(jsonPath("$.estValide").value(true))
                .andExpect(jsonPath("$.etudiants[0].idEtudiant").value(1))
                .andExpect(jsonPath("$.etudiants[0].nomEtudiant").value("Doe"));
    }

    @Test
    void testRemoveReservation() throws Exception {
        mockMvc.perform(delete("/reservation/remove-reservation/RES-2024-001"))
                .andExpect(status().isOk());
    }

    @Test
    void testModifyReservation() throws Exception {
        when(reservationService.modifyReservation(any(Reservation.class))).thenReturn(reservation);

        mockMvc.perform(put("/reservation/modify-reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idReservation\":\"RES-2024-001\",\"estValide\":true,\"etudiants\":[{\"idEtudiant\":1}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value("RES-2024-001"))
                .andExpect(jsonPath("$.estValide").value(true))
                .andExpect(jsonPath("$.etudiants[0].idEtudiant").value(1))
                .andExpect(jsonPath("$.etudiants[0].nomEtudiant").value("Doe"));
    }
} 