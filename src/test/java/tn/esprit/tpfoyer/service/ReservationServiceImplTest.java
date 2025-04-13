package tn.esprit.tpfoyer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.InOrder;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import tn.esprit.tpfoyer.config.DuplicateReservationException;
import tn.esprit.tpfoyer.config.ReservationNotFoundException;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
 class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @Captor
    private ArgumentCaptor<Reservation> reservationCaptor;

    private Reservation testReservation;
    private Date testDate;

    @BeforeEach
    void setUp() {
        testDate = new Date();
        testReservation = new Reservation();
        testReservation.setIdReservation("RES123");
        testReservation.setEstValide(true);
        testReservation.setAnneeUniversitaire(testDate);
    }

    @Test
    void testRetrieveAllReservations_WithMultipleReservations() {
        // Arrange
        Reservation reservation2 = new Reservation();
        reservation2.setIdReservation("RES456");
        List<Reservation> reservations = Arrays.asList(testReservation, reservation2);
        when(reservationRepository.findAll()).thenReturn(reservations);

        // Act
        List<Reservation> result = reservationService.retrieveAllReservations();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(reservationRepository, times(1)).findAll();
    }

    @Test
    void testRetrieveReservation_WhenNotFound() {
        // Arrange
        String nonExistentId = "NONEXISTENT";
        when(reservationRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            reservationService.retrieveReservation(nonExistentId);
        });
        verify(reservationRepository).findById(nonExistentId);
    }

    @Test
    void testAddReservation_VerifyAllFields() {
        // Arrange
        when(reservationRepository.save(any(Reservation.class))).thenReturn(testReservation);

        // Act
        reservationService.addReservation(testReservation);

        // Assert
        verify(reservationRepository).save(reservationCaptor.capture());
        Reservation capturedReservation = reservationCaptor.getValue();
        assertEquals(testReservation.getIdReservation(), capturedReservation.getIdReservation());
        assertEquals(testReservation.isEstValide(), capturedReservation.isEstValide());
        assertEquals(testReservation.getAnneeUniversitaire(), capturedReservation.getAnneeUniversitaire());
    }

    @Test
    void testModifyReservation_WithNullValues() {
        // Arrange
        Reservation nullReservation = new Reservation();
        when(reservationRepository.save(any(Reservation.class))).thenReturn(nullReservation);

        // Act
        Reservation result = reservationService.modifyReservation(nullReservation);

        // Assert
        assertNotNull(result);
        verify(reservationRepository).save(reservationCaptor.capture());
        Reservation capturedReservation = reservationCaptor.getValue();
        assertNull(capturedReservation.getIdReservation());
        assertNull(capturedReservation.getAnneeUniversitaire());
    }

    @Test
    void testTrouverResSelonDateEtStatus_WithNoResults() {
        // Arrange
        when(reservationRepository.findAllByAnneeUniversitaireBeforeAndEstValide(any(Date.class), anyBoolean()))
                .thenReturn(Arrays.asList());

        // Act
        List<Reservation> result = reservationService.trouverResSelonDateEtStatus(testDate, true);

        // Assert
        assertTrue(result.isEmpty());
        verify(reservationRepository).findAllByAnneeUniversitaireBeforeAndEstValide(testDate, true);
    }

    @Test
    void testRemoveReservation_VerifyMethodCall() {
        // Arrange
        String reservationId = "RES123";
        doNothing().when(reservationRepository).deleteById(anyString());

        // Act
        reservationService.removeReservation(reservationId);

        // Assert
        verify(reservationRepository, times(1)).deleteById(reservationId);
        verifyNoMoreInteractions(reservationRepository);
    }

    @Test
    void testConsecutiveOperations() {
        // Arrange
        when(reservationRepository.save(any(Reservation.class))).thenReturn(testReservation);
        when(reservationRepository.findById(testReservation.getIdReservation())).thenReturn(Optional.of(testReservation));

        // Act & Assert - Test consecutive operations
        // 1. Add reservation
        Reservation added = reservationService.addReservation(testReservation);
        assertNotNull(added);

        // 2. Retrieve the same reservation
        Reservation retrieved = reservationService.retrieveReservation(added.getIdReservation());
        assertEquals(added.getIdReservation(), retrieved.getIdReservation());

        // 3. Verify order of operations
        InOrder inOrder = inOrder(reservationRepository);
        inOrder.verify(reservationRepository).save(any(Reservation.class));
        inOrder.verify(reservationRepository).findById(anyString());
    }

    @Test
    void testReservationWithSpyObject() {
        // Create a spy object
        Reservation spyReservation = spy(testReservation);
        
        // Mock behavior
        when(spyReservation.getIdReservation()).thenReturn("SPY123");
        
        // Use in service
        when(reservationRepository.save(any(Reservation.class))).thenReturn(spyReservation);
        
        // Act
        Reservation result = reservationService.addReservation(spyReservation);
        
        // Assert
        assertEquals("SPY123", result.getIdReservation());
        verify(spyReservation, atLeastOnce()).getIdReservation();
    }
    // Scenario 1: Test remove reservation when not found
    @Test
    void testRemoveReservation_WhenNotFound() {
        // Arrange
        String nonExistentId = "NONEXISTENT";
        doThrow(new RuntimeException("Reservation not found")).when(reservationRepository).deleteById(nonExistentId);

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            reservationService.removeReservation(nonExistentId);
        });
        verify(reservationRepository).deleteById(nonExistentId);
    }

    // Scenario 2: Test modify reservation with invalid data
    @Test
    void testModifyReservation_WithInvalidData() {
        // Arrange
        Reservation invalidReservation = new Reservation();
        invalidReservation.setIdReservation(null); // ID manquant
        invalidReservation.setAnneeUniversitaire(null); // Date manquante

        when(reservationRepository.save(any(Reservation.class))).thenThrow(new RuntimeException("Invalid reservation data"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> {
            reservationService.modifyReservation(invalidReservation);
        });
        verify(reservationRepository, times(1)).save(invalidReservation);
    }

    // Scenario 3: Test retrieve valid reservations by year
    @Test
    void testRetrieveValidReservationsByYear() {
        // Arrange
        Date specificYear = new Date(); // Une date spécifique pour l'année universitaire
        Reservation reservation1 = new Reservation();
        reservation1.setAnneeUniversitaire(specificYear);
        reservation1.setEstValide(true);

        Reservation reservation2 = new Reservation();
        reservation2.setAnneeUniversitaire(specificYear);
        reservation2.setEstValide(true);

        List<Reservation> reservations = Arrays.asList(reservation1, reservation2);
        when(reservationRepository.findAllByAnneeUniversitaireAndEstValide((specificYear), true)).thenReturn(reservations);

        // Act
        List<Reservation> result = reservationService.retrieveValidReservationsByYear(specificYear);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(r -> r.isEstValide()));
    }

    // Scenario 4: Test retrieve reservation with custom exception
    @Test
    void testRetrieveReservation_WithCustomException() {
        String reservationId = "NON_EXISTENT_ID";
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        assertThrows(ReservationNotFoundException.class, () -> {
            reservationService.retrieveReservation(reservationId);
        });
    }


    // Scenario 5: Test add reservation with existing data
    @Test
    void testAddReservation_WithExistingId() {
        Reservation existingReservation = new Reservation();
        existingReservation.setIdReservation("RES123");

        when(reservationRepository.existsById(existingReservation.getIdReservation())).thenReturn(true);

        assertThrows(DuplicateReservationException.class, () -> {
            reservationService.addReservation(existingReservation);
        });
    }


    // Scenario 6: Test retrieve all reservations with an empty list
    @Test
    void testRetrieveAllReservations_WithEmptyList() {
        // Arrange
        when(reservationRepository.findAll()).thenReturn(Arrays.asList());

        // Act
        List<Reservation> result = reservationService.retrieveAllReservations();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(reservationRepository).findAll();
    }

    // Scenario 7: Test multiple method calls in order
    @Test
    void testMultipleMethodCallsInOrder() {
        // Arrange
        when(reservationRepository.save(any(Reservation.class))).thenReturn(testReservation);
        when(reservationRepository.findById(testReservation.getIdReservation())).thenReturn(Optional.of(testReservation));

        // Act
        reservationService.addReservation(testReservation);
        reservationService.retrieveReservation(testReservation.getIdReservation());

        // Assert - Vérification de l'ordre des appels
        InOrder inOrder = inOrder(reservationRepository);
        inOrder.verify(reservationRepository).save(any(Reservation.class));
        inOrder.verify(reservationRepository).findById(anyString());
    }
}
