package lt.techin.rental.controller;

import jakarta.validation.Valid;
import lt.techin.rental.dto.CarRequestDTO;
import lt.techin.rental.dto.RentalMapper;
import lt.techin.rental.dto.RentalRequestDTO;
import lt.techin.rental.dto.RentalResponseDTO;
import lt.techin.rental.model.Car;
import lt.techin.rental.model.Rental;
import lt.techin.rental.model.User;
import lt.techin.rental.service.CarService;
import lt.techin.rental.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RentalController {

  private RentalService rentalService;
  private CarService carService;

  @Autowired
  public RentalController(RentalService rentalService, CarService carService) {
    this.rentalService = rentalService;
    this.carService = carService;
  }

  @GetMapping("/rentals/history")
  public ResponseEntity<List<RentalResponseDTO>> getAllRentals() {
    List<Rental> rentals = rentalService.findAllRentals();
    List<RentalResponseDTO> rentalsDTO = rentals.stream()
            .map(a -> RentalMapper.toRentalResponseDTO(a)).toList();

    return ResponseEntity.ok(rentalsDTO);
  }

//  @GetMapping("/rentals/my")
//  public ResponseEntity<List<RentalResponseDTO>> getRentalsByOwner(@PathVariable long id) {
//    List<Rental> rentals = rentalService.findAllRentals();
//    List<RentalResponseDTO> rentalsDTOByOwner = rentals.stream()
//            .filter(a -> a.getUser().getId() == id)
//            .map(a -> RentalMapper.toRentalResponseDTO(a))
//            .toList();
//
//    return ResponseEntity.ok(rentalsDTOByOwner);
//  }

  @GetMapping("/rentals/my")
  public ResponseEntity<List<RentalResponseDTO>> getRentalsByOwner(@AuthenticationPrincipal User user) {
    List<Rental> rentals = rentalService.findRentalsByUser(user);
    List<RentalResponseDTO> rentalsDTOByOwner = rentals.stream()
            .map(a -> RentalMapper.toRentalResponseDTO(a))
            .toList();

    return ResponseEntity.ok(rentalsDTOByOwner);
  }

  @PostMapping("/rentals")
  public ResponseEntity<RentalResponseDTO> addRental(@Valid @RequestBody RentalRequestDTO rentalRequestDTO, @AuthenticationPrincipal User user, @RequestParam long carId) {

    Optional<Car> carOptional = carService.findCarByID(carId);
    if (carOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Car car = carOptional.get();

    if (!car.getStatus().equals("AVAILABLE")) {
      throw new IllegalStateException("Car is already rented");
    }

    if (user.getRentals().size() >= 2) {
      throw new IllegalStateException("User can't rent more than 2 cars");
    }

    long daysBetween = ChronoUnit.DAYS.between(rentalRequestDTO.rentalStart(), rentalRequestDTO.rentalEnd());
    if (daysBetween < 1) {
      throw new IllegalArgumentException("Rental period must be at least 1 day");
    }

    BigDecimal totalPrice = rentalRequestDTO.price().multiply(BigDecimal.valueOf(daysBetween));

    Rental rental = RentalMapper.toRental(rentalRequestDTO, user, car);
    rental.setPrice(totalPrice);

    Rental savedRental = rentalService.saveRental(rental);
    RentalResponseDTO savedRentalDTO = RentalMapper.toRentalResponseDTO(savedRental);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedRental.getId())
                            .toUri())
            .body(savedRentalDTO);
  }

  @PostMapping("/rentals/return/{rentalId}")
  public ResponseEntity<RentalResponseDTO> returnRental(@PathVariable long rentalId, @AuthenticationPrincipal User user) {

    Optional<Rental> rentalOptional = rentalService.findRentalByID(rentalId);

    if (rentalOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Rental rental = rentalOptional.get();

    if (!rental.getUser().equals(user)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    if (!rental.getCar().getStatus().equals("RENTED")) {
      throw new IllegalStateException("Car was never rented");
    }

    rentalService.returnRental(rental);

    RentalResponseDTO rentalResponseDTO = RentalMapper.toRentalResponseDTO(rental);
    return ResponseEntity.ok(rentalResponseDTO);
  }

  @PutMapping("/rentals/{id}")
  public ResponseEntity<RentalResponseDTO> updateRental(@PathVariable long id, @Valid @RequestBody RentalRequestDTO rentalRequestDTO,
                                                        @AuthenticationPrincipal User user) {

    Optional<Rental> rentalOptional = rentalService.findRentalByID(id);

    if (rentalOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Rental rental = rentalOptional.get();

    if (!rental.getUser().equals(user)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    long daysBetween = ChronoUnit.DAYS.between(rentalRequestDTO.rentalStart(), rentalRequestDTO.rentalEnd());
    if (daysBetween < 1) {
      throw new IllegalArgumentException("Rental period must be at least 1 day");
    }

    BigDecimal totalPrice = rentalRequestDTO.price().multiply(BigDecimal.valueOf(daysBetween));

    rental.setRentalStart(rentalRequestDTO.rentalStart());
    rental.setRentalEnd(rentalRequestDTO.rentalEnd());
    rental.setPrice(totalPrice);

    Rental updatedRental = rentalService.saveRental(rental);
    RentalResponseDTO updatedRentalDTO = RentalMapper.toRentalResponseDTO(updatedRental);

    return ResponseEntity.ok(updatedRentalDTO);
  }

  @DeleteMapping("/rentals/{id}")
  public ResponseEntity<Void> deleteRental(@PathVariable long id, @AuthenticationPrincipal User user) {

    Optional<Rental> rentalOptional = rentalService.findRentalByID(id);

    if (rentalOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Rental rental = rentalOptional.get();

    if (!rental.getUser().equals(user)) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    rentalService.deleteRental(id);
    return ResponseEntity.noContent().build();
  }
}
