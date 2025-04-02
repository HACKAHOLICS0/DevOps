package tn.esprit.tpfoyer.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;
import tn.esprit.tpfoyer.service.ReservationServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRetrieveAllReservations() {
        // Arrange
        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation1 = new Reservation("R1", new Date(), true, null);
        Reservation reservation2 = new Reservation("R2", new Date(), false, null);
        reservations.add(reservation1);
        reservations.add(reservation2);
        when(reservationRepository.findAll()).thenReturn(reservations);

        // Act
        List<Reservation> result = reservationService.retrieveAllReservations();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveReservation() {
        // Arrange
        String reservationId = "R1";
        Reservation reservation = new Reservation("R1", new Date(), true, null);
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        // Act
        Reservation result = reservationService.retrieveReservation(reservationId);

        // Assert
        assertNotNull(result);
        assertEquals("R1", result.getIdReservation());
    }

    @Test
    void testAddReservation() {
        // Arrange
        Reservation reservation = new Reservation("R1", new Date(), true, null);
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        // Act
        Reservation result = reservationService.addReservation(reservation);

        // Assert
        assertNotNull(result);
        assertEquals("R1", result.getIdReservation());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void testModifyReservation() {
        // Arrange
        Reservation reservation = new Reservation("R1", new Date(), true, null);
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        // Act
        Reservation result = reservationService.modifyReservation(reservation);

        // Assert
        assertNotNull(result);
        assertEquals("R1", result.getIdReservation());
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void testRemoveReservation() {
        // Arrange
        String reservationId = "R1";
        doNothing().when(reservationRepository).deleteById(reservationId);

        // Act
        reservationService.removeReservation(reservationId);

        // Assert
        verify(reservationRepository, times(1)).deleteById(reservationId);
    }

    @Test
    void testTrouverResSelonDateEtStatus() {
        // Arrange
        Date date = new Date();
        boolean status = true;
        List<Reservation> reservations = new ArrayList<>();
        Reservation reservation = new Reservation("R1", date, status, null);
        reservations.add(reservation);
        when(reservationRepository.findAllByAnneeUniversitaireBeforeAndEstValide(date, status)).thenReturn(reservations);

        // Act
        List<Reservation> result = reservationService.trouverResSelonDateEtStatus(date, status);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reservationRepository, times(1)).findAllByAnneeUniversitaireBeforeAndEstValide(date, status);
    }
}