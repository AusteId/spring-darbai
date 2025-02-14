package lt.techin.rental.controller;

import jakarta.validation.Valid;
import lt.techin.rental.dto.CarMapper;
import lt.techin.rental.dto.CarRequestDTO;
import lt.techin.rental.dto.CarResponseDTO;
import lt.techin.rental.model.Car;
import lt.techin.rental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CarController {

  private CarService carService;

  @Autowired
  public CarController(CarService carService) {
    this.carService = carService;
  }

  @GetMapping("cars")
  public ResponseEntity<List<CarResponseDTO>> getCars() {
    List<Car> cars = carService.findAllCars();
    List<CarResponseDTO> carsDTO = cars.stream()
            .map(a -> CarMapper.toCarResponseDTO(a))
            .toList();

    return ResponseEntity.ok(carsDTO);
  }

  @GetMapping("cars/{id}")
  public ResponseEntity<CarResponseDTO> getCar(@PathVariable long id) {
    Optional<Car> foundedCar = carService.findCarByID(id);

    if (foundedCar.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(CarMapper.toCarResponseDTO(foundedCar.get()));
  }

  @PostMapping("/cars")
  public ResponseEntity<CarResponseDTO> addCar(@Valid @RequestBody CarRequestDTO carRequestDTO) {

    Car car = CarMapper.toCar(carRequestDTO);
    Car savedCar = carService.saveCar(car);

    CarResponseDTO savedCarDTO = CarMapper.toCarResponseDTO(savedCar);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedCar.getId())
                            .toUri())
            .body(savedCarDTO);
  }

  @PutMapping("/cars/{id}")
  public ResponseEntity<CarResponseDTO> updateCar(@PathVariable long id, @Valid @RequestBody CarRequestDTO carRequestDTO) {

    if (carService.existsCarById(id)) {
      Car carToUpdate = carService.findCarByID(id).get();
      carToUpdate.setBrand(carRequestDTO.brand());
      carToUpdate.setModel(carRequestDTO.model());
      carToUpdate.setYear(carRequestDTO.year());
      Car updatedCar = carService.saveCar(carToUpdate);
      CarResponseDTO updatedCarResponseDTO = CarMapper.toCarResponseDTO(updatedCar);

      return ResponseEntity.ok(updatedCarResponseDTO);
    }

    Car car = CarMapper.toCar(carRequestDTO);
    Car savedCar = carService.saveCar(car);

    CarResponseDTO savedCarDTO = CarMapper.toCarResponseDTO(savedCar);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .replacePath("/api/cars/{id}")
                            .buildAndExpand(savedCar.getId())
                            .toUri())
            .body(savedCarDTO);
  }

  @DeleteMapping("/cars/{id}")
  public ResponseEntity<Void> deleteCar(@PathVariable long id) {

    if (!carService.existsCarById(id)) {
      return ResponseEntity.notFound().build();
    }

    carService.deleteCar(id);
    return ResponseEntity.noContent().build();
  }
}
