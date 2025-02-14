package lt.techin.rental.repository;

import lt.techin.rental.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental, Long> {


}
