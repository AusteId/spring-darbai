package lt.techin.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "screenings")
public class Screening {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  @Size(max = 100, message = "Theater input should be maximum 100 character long")
  @Pattern(regexp = "^[A-Z][A-Za-z0-9\\s\\-]*$", message = "Theater input should start from uppercase letter")
  private String theater;

  @NotNull
  @FutureOrPresent(message = "The date must be in the present or future")
  private LocalDateTime dateAndTime;

  public Screening() {
  }

  public Screening(String theater, LocalDateTime dateAndTime) {
    this.theater = theater;
    this.dateAndTime = dateAndTime;
  }

  public long getId() {
    return id;
  }

  public String getTheater() {
    return theater;
  }

  public void setTheater(String theater) {
    this.theater = theater;
  }

  public LocalDateTime getDateAndTime() {
    return dateAndTime;
  }

  public void setDateAndTime(LocalDateTime dateAndTime) {
    this.dateAndTime = dateAndTime;
  }

}
