package lt.techin.demo.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "screenings")
public class Screening {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String theater;
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
