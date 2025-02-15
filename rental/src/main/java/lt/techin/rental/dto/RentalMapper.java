package lt.techin.rental.dto;

import lt.techin.rental.model.Car;
import lt.techin.rental.model.Rental;
import lt.techin.rental.model.User;

public class RentalMapper {

  public static RentalResponseDTO toRentalResponseDTO(Rental rental) {
    return new RentalResponseDTO(rental.getId(), rental.getUser().getId(), rental.getUser().getUsername(),
            rental.getCar().getId(), rental.getRentalStart(), rental.getRentalEnd(), rental.getPrice(),
            rental.getCar().getBrand(), rental.getCar().getModel(), rental.getCar().getYear());
  }

  public static Rental toRental(RentalRequestDTO rentalRequestDTO, User user, Car car) {
    Rental rental = new Rental();
    rental.setUser(user);
    rental.setCar(car);
    rental.setRentalStart(rentalRequestDTO.rentalStart());
    rental.setRentalEnd(rentalRequestDTO.rentalEnd());
    rental.setPrice(rentalRequestDTO.price());
    return rental;
  }
}
