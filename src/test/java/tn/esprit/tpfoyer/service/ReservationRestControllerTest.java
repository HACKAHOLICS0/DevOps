package tn.esprit.tpfoyer.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.control.ReservationRestController;
import tn.esprit.tpfoyer.entity.Reservation;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class ReservationRestControllerTest {

    @InjectMocks
    ReservationRestController controller;

    @Mock
    IReservationService reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetReservations() {
        Reservation r1 = new Reservation();
        when(reservationService.retrieveAllReservations()).thenReturn(Arrays.asList(r1));

        List<Reservation> result = controller.getReservations();
        assertEquals(1, result.size());
        verify(reservationService).retrieveAllReservations();
    }

    @Test
    void testRetrieveReservation() {
        Reservation r = new Reservation();
        when(reservationService.retrieveReservation("res1")).thenReturn(r);

        Reservation result = controller.retrieveReservation("res1");
        assertEquals(r, result);
        verify(reservationService).retrieveReservation("res1");
    }

    @Test
    void testRetrieveReservationParDateEtStatus() {
        Date date = new Date();
        when(reservationService.trouverResSelonDateEtStatus(date, true)).thenReturn(List.of(new Reservation()));

        List<Reservation> result = controller.retrieveReservationParDateEtStatus(date, true);
        assertEquals(1, result.size());
    }

    @Test
    void testAddReservation() {
        Reservation r = new Reservation();
        when(reservationService.addReservation(r)).thenReturn(r);

        Reservation result = controller.addReservation(r);
        assertEquals(r, result);
    }

    @Test
    void testRemoveReservation() {
        controller.removeReservation("res1");
        verify(reservationService).removeReservation("res1");
    }

    @Test
    void testModifyReservation() {
        Reservation r = new Reservation();
        when(reservationService.modifyReservation(r)).thenReturn(r);

        Reservation result = controller.modifyReservation(r);
        assertEquals(r, result);
    }
}
