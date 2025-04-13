package tn.esprit.tpfoyer.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.tpfoyer.config.DuplicateReservationException;
import tn.esprit.tpfoyer.config.ReservationNotFoundException;
import tn.esprit.tpfoyer.entity.Reservation;
import tn.esprit.tpfoyer.repository.ReservationRepository;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements IReservationService {

    ReservationRepository reservationRepository;

    public List<Reservation> retrieveAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation retrieveReservation(String reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ReservationNotFoundException("Reservation not found for ID: " + reservationId));
    }


    public Reservation addReservation(Reservation r) {
        if (reservationRepository.existsById(r.getIdReservation())) {
            throw new DuplicateReservationException("Reservation with ID " + r.getIdReservation() + " already exists");
        }
        return reservationRepository.save(r);
    }

    public Reservation modifyReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public List<Reservation> trouverResSelonDateEtStatus(Date d, boolean b) {
        return reservationRepository.findAllByAnneeUniversitaireBeforeAndEstValide(d, b);
    }

    public void removeReservation(String reservationId) {
        reservationRepository.deleteById(reservationId);
    }
    public List<Reservation> retrieveValidReservationsByYear(Date year) {
        return reservationRepository.findAllByAnneeUniversitaireAndEstValide(year, true);
    }

}
