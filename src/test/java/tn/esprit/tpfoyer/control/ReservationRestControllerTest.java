package tn.esprit.tpfoyer.control;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.service.IReservationService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationRestControllerTest {

    @Mock
    private IReservationService reservationService;

    @InjectMocks
    private ReservationRestController reservationRestController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetReservations() {
        // Arrange
        Reservation reservation1 = new Reservation();
        Reservation reservation2 = new Reservation();
        List<Reservation> expectedReservations = Arrays.asList(reservation1, reservation2);

        when(reservationService.retrieveAllReservations()).thenReturn(expectedReservations);

        // Act
        List<Reservation> actualReservations = reservationRestController.getReservations();

        // Assert
        assertEquals(expectedReservations, actualReservations);
        verify(reservationService, times(1)).retrieveAllReservations();
    }

    @Test
    void testRetrieveReservation() {
        // Arrange
        String reservationId = "1";
        Reservation expectedReservation = new Reservation();
        expectedReservation.setIdReservation(reservationId);

        when(reservationService.retrieveReservation(reservationId)).thenReturn(expectedReservation);

        // Act
        Reservation actualReservation = reservationRestController.retrieveReservation(reservationId);

        // Assert
        assertEquals(expectedReservation, actualReservation);
        verify(reservationService, times(1)).retrieveReservation(reservationId);
    }

    @Test
    void testRetrieveReservationByDateAndStatus() {
        // Arrange
        Date date = new Date();
        boolean status = true;
        Reservation reservation1 = new Reservation();
        List<Reservation> expectedReservations = Arrays.asList(reservation1);

        when(reservationService.trouverResSelonDateEtStatus(date, status)).thenReturn(expectedReservations);

        // Act
        List<Reservation> actualReservations = reservationRestController.retrieveReservationParDateEtStatus(date, status);

        // Assert
        assertEquals(expectedReservations, actualReservations);
        verify(reservationService, times(1)).trouverResSelonDateEtStatus(date, status);
    }

    @Test
    void testAddReservation() {
        // Arrange
        Reservation reservationToAdd = new Reservation();
        Reservation expectedReservation = new Reservation();
        expectedReservation.setIdReservation("1");

        when(reservationService.addReservation(reservationToAdd)).thenReturn(expectedReservation);

        // Act
        Reservation actualReservation = reservationRestController.addReservation(reservationToAdd);

        // Assert
        assertEquals(expectedReservation, actualReservation);
        verify(reservationService, times(1)).addReservation(reservationToAdd);
    }

    @Test
    void testRemoveReservation() {
        // Arrange
        String reservationId = "1";

        // Act
        reservationRestController.removeReservation(reservationId);

        // Assert
        verify(reservationService, times(1)).removeReservation(reservationId);
    }

    @Test
    void testModifyReservation() {
        // Arrange
        Reservation reservationToModify = new Reservation();
        reservationToModify.setIdReservation("1");
        Reservation expectedReservation = new Reservation();
        expectedReservation.setIdReservation("1");

        when(reservationService.modifyReservation(reservationToModify)).thenReturn(expectedReservation);

        // Act
        Reservation actualReservation = reservationRestController.modifyReservation(reservationToModify);

        // Assert
        assertEquals(expectedReservation, actualReservation);
        verify(reservationService, times(1)).modifyReservation(reservationToModify);
    }
}
