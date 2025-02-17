package lt.techin.catcafe.service;

import lt.techin.catcafe.model.Reservation;
import lt.techin.catcafe.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

  private final ReservationRepository reservationRepository;

  @Autowired
  public ReservationService(ReservationRepository reservationRepository) {
    this.reservationRepository = reservationRepository;
  }

  public Reservation saveReservation(Reservation reservation) {
    return reservationRepository.save(reservation);
  }

  public List<Reservation> findAllReservations() {
    return reservationRepository.findAll();
  }

  public Optional<Reservation> findReservationById(long id) {
    return reservationRepository.findById(id);
  }

  public List<Reservation> findReservationsByUserId(long userId) {
    return reservationRepository.findByUserId(userId);
  }

  public void deleteReservation(long id) {
    reservationRepository.deleteById(id);
  }
}
