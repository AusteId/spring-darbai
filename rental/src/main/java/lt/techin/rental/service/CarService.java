package lt.techin.rental.service;

import lt.techin.rental.model.Car;
import lt.techin.rental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

  private final CarRepository carRepository;

  @Autowired
  public CarService(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  public List<Car> findAllCars() {
    return carRepository.findAll();
  }

  public Optional<Car> findCarByID(long id) {
    return carRepository.findById(id);
  }

  public Car saveCar(Car car) {
    return carRepository.save(car);
  }

  public void deleteCar(long id) {
    carRepository.deleteById(id);
  }

  public boolean existsCarById(long id) {
    return carRepository.existsById(id);
  }
}
