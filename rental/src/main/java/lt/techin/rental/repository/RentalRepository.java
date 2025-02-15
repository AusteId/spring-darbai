package lt.techin.rental.repository;

import lt.techin.rental.model.Rental;
import lt.techin.rental.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental, Long> {

  List<Rental> findByUser(User user);
}
