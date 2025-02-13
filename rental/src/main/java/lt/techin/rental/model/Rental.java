package lt.techin.rental.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "rentals")
public class Rental {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "car_id", nullable = false)
  private Car car;

  @Column(nullable = false)
  private LocalDate rentalStart;

  @Column(nullable = false)
  private LocalDate rentalEnd;

  @Column(nullable = false)
  private BigDecimal price;

  public Rental() {
  }

  public Rental(User user, Car car, LocalDate rentalStart, LocalDate rentalEnd, BigDecimal price) {

//    if(rentalStart.isAfter(rentalEnd)) {
//      throw new IllegalArgumentException("Rental start date must be before rental end date");
//    }
//
//    if(rentalStart.equals(rentalEnd)) {
//      throw new IllegalArgumentException("Rental duration must be at least one day");
//    }

    this.user = user;
    this.car = car;
    this.rentalStart = rentalStart;
    this.rentalEnd = rentalEnd;
    this.price = price;
  }

  public long getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Car getCar() {
    return car;
  }

  public void setCar(Car car) {
    this.car = car;
  }

  public LocalDate getRentalStart() {
    return rentalStart;
  }

  public void setRentalStart(LocalDate rentalStart) {
    this.rentalStart = rentalStart;
  }

  public LocalDate getRentalEnd() {
    return rentalEnd;
  }

  public void setRentalEnd(LocalDate rentalEnd) {
    this.rentalEnd = rentalEnd;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
