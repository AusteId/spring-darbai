package lt.techin.catcafe.repository;

import lt.techin.catcafe.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

  Optional<Reservation> findById(long id);

  List<Reservation> findByUserId(long userId);
}
