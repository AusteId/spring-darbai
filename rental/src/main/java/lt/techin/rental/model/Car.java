package lt.techin.rental.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cars")
public class Car {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false)
  private String brand;

  @Column(nullable = false)
  private String model;

  @Column(nullable = false)
  private int year;

  @Column(nullable = false)
  private String status;

  @OneToMany
  private List<Rental> rentals;

  public Car() {
  }

  public Car(String brand, String model, int year) {
    this.brand = brand;
    this.model = model;
    this.year = year;
    this.status = "AVAILABLE";
  }

  public long getId() {
    return id;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    status = status.toUpperCase();
    if (!status.equals("AVAILABLE") && !status.equals("RENTED")) {
      throw new IllegalArgumentException("Status must be AVAILABLE or RENTED");
    }
    this.status = status;
  }
}
