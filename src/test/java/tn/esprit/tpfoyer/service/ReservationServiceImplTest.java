package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    private Reservation reservation;

    @BeforeEach
    void setUp() {
        reservation = new Reservation();
        reservation.setIdReservation("RES-2024-001");
        reservation.setAnneeUniversitaire(new Date());
        reservation.setEstValide(true);
    }

    @Test
    void testAddReservation() {
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation savedReservation = reservationService.addReservation(reservation);

        assertNotNull(savedReservation);
        assertEquals("RES-2024-001", savedReservation.getIdReservation());
        assertTrue(savedReservation.isEstValide());
        assertNotNull(savedReservation.getAnneeUniversitaire());

        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void testRetrieveAllReservation() {
        List<Reservation> reservations = Arrays.asList(reservation);
        when(reservationRepository.findAll()).thenReturn(reservations);

        List<Reservation> retrievedReservations = reservationService.retrieveAllReservations();

        assertNotNull(retrievedReservations);
        assertEquals(1, retrievedReservations.size());
        assertEquals(reservation, retrievedReservations.get(0));

        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveReservation() {
        when(reservationRepository.findById("RES-2024-001")).thenReturn(Optional.of(reservation));

        Reservation retrievedReservation = reservationService.retrieveReservation("RES-2024-001");

        assertNotNull(retrievedReservation);
        assertEquals("RES-2024-001", retrievedReservation.getIdReservation());
        assertTrue(retrievedReservation.isEstValide());
        assertNotNull(retrievedReservation.getAnneeUniversitaire());

        verify(reservationRepository, times(1)).findById("RES-2024-001");
    }

    @Test
    void testRetrieveReservation_NotFound() {
        when(reservationRepository.findById("RES-2024-001")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> reservationService.retrieveReservation("RES-2024-001"));

        verify(reservationRepository, times(1)).findById("RES-2024-001");
    }

    @Test
    void testRemoveReservation() {
        doNothing().when(reservationRepository).deleteById("RES-2024-001");

        reservationService.removeReservation("RES-2024-001");

        verify(reservationRepository, times(1)).deleteById("RES-2024-001");
    }

    @Test
    void testModifyReservation() {
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation modifiedReservation = reservationService.modifyReservation(reservation);

        assertNotNull(modifiedReservation);
        assertEquals("RES-2024-001", modifiedReservation.getIdReservation());
        assertTrue(modifiedReservation.isEstValide());
        assertNotNull(modifiedReservation.getAnneeUniversitaire());

        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }

    @Test
    void testAjouterReservation() {
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation addedReservation = reservationService.addReservation(reservation);

        assertNotNull(addedReservation);
        assertEquals("RES-2024-001", addedReservation.getIdReservation());
        assertTrue(addedReservation.isEstValide());
        assertNotNull(addedReservation.getAnneeUniversitaire());

        verify(reservationRepository, times(1)).save(any(Reservation.class));
    }
} 