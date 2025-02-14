package lt.techin.rental.service;

import lt.techin.rental.model.Car;
import lt.techin.rental.model.Rental;
import lt.techin.rental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

  private final RentalRepository rentalRepository;
  private final CarService carService;

  @Autowired
  public RentalService(RentalRepository rentalRepository, CarService carService) {
    this.rentalRepository = rentalRepository;
    this.carService = carService;
  }

  public List<Rental> findAllRentals() {
    return rentalRepository.findAll();
  }

  public Optional<Rental> findRentalByID(long id) {
    return rentalRepository.findById(id);
  }

  public Rental saveRental(Rental rental) {
    Rental savedRental = rentalRepository.save(rental);
    Car car = rental.getCar();
    car.setStatus("RENTED");
    carService.saveCar(car);

    return savedRental;
  }

  public void deleteRental(long id) {

    Optional<Rental> rental = rentalRepository.findById(id);

    if (rental.isPresent()) {
      Car car = rental.get().getCar();
      car.setStatus("AVAILABLE");
      carService.saveCar(car);
      rentalRepository.deleteById(id);
    }
  }

  public boolean existsRentalById(long id) {
    return rentalRepository.existsById(id);
  }
}
