package lt.techin.rental.dto;

import lt.techin.rental.model.Car;

public class CarMapper {

  public static CarResponseDTO toCarResponseDTO(Car car) {
    return new CarResponseDTO(car.getId(), car.getBrand(), car.getModel(), car.getYear(), car.getStatus());
  }

  public static Car toCar(CarRequestDTO carRequestDTO) {
    Car car = new Car();
    car.setBrand(carRequestDTO.brand());
    car.setModel(carRequestDTO.model());
    car.setYear(carRequestDTO.year());
    return car;
  }

}
