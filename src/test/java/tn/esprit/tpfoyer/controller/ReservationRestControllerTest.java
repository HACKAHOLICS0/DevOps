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
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.service.IReservationService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(reservationRestController).build();
        
        reservation = new Reservation();
        reservation.setIdReservation("RES-2024-001");
        reservation.setAnneeUniversitaire(new Date());
        reservation.setEstValide(true);
    }

    @Test
    void testGetReservations() throws Exception {
        List<Reservation> reservations = Arrays.asList(reservation);
        when(reservationService.retrieveAllReservations()).thenReturn(reservations);

        mockMvc.perform(get("/tpfoyer/reservation/retrieve-all-reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].idReservation").value("RES-2024-001"))
                .andExpect(jsonPath("$[0].estValide").value(true));
    }

    @Test
    void testGetReservation() throws Exception {
        when(reservationService.retrieveReservation("RES-2024-001")).thenReturn(reservation);

        mockMvc.perform(get("/tpfoyer/reservation/retrieve-reservation/RES-2024-001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value("RES-2024-001"))
                .andExpect(jsonPath("$.estValide").value(true));
    }

    @Test
    void testAddReservation() throws Exception {
        when(reservationService.addReservation(any(Reservation.class))).thenReturn(reservation);

        mockMvc.perform(post("/tpfoyer/reservation/add-reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idReservation\":\"RES-2024-001\",\"estValide\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value("RES-2024-001"))
                .andExpect(jsonPath("$.estValide").value(true));
    }

    @Test
    void testRemoveReservation() throws Exception {
        mockMvc.perform(delete("/tpfoyer/reservation/remove-reservation/RES-2024-001"))
                .andExpect(status().isOk());
    }

    @Test
    void testModifyReservation() throws Exception {
        when(reservationService.modifyReservation(any(Reservation.class))).thenReturn(reservation);

        mockMvc.perform(put("/tpfoyer/reservation/modify-reservation")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"idReservation\":\"RES-2024-001\",\"estValide\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value("RES-2024-001"))
                .andExpect(jsonPath("$.estValide").value(true));
    }

    @Test
    void testAjouterReservation() throws Exception {
        when(reservationService.addReservation(reservation)).thenReturn(reservation);

        mockMvc.perform(post("/tpfoyer/reservation/ajouter-reservation/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idReservation").value("RES-2024-001"))
                .andExpect(jsonPath("$.estValide").value(true));
    }
} 