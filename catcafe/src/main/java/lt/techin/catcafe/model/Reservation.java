package lt.techin.catcafe.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "reservations")
public class Reservation {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private LocalDate dateOfReservation;

  @Column(nullable = false)
  private String timeSlot;

  @Column(nullable = false)
  private int numGuests;

  public Reservation() {
  }

  public Reservation(User user, LocalDate dateOfReservation, String timeSlot, int numGuests) {
    this.user = user;
    this.dateOfReservation = dateOfReservation;
    this.timeSlot = timeSlot;
    this.numGuests = numGuests;
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

  public LocalDate getDateOfReservation() {
    return dateOfReservation;
  }

  public void setDateOfReservation(LocalDate dateOfReservation) {
    this.dateOfReservation = dateOfReservation;
  }

  public String getTimeSlot() {
    return timeSlot;
  }

  public void setTimeSlot(String timeSlot) {
    this.timeSlot = timeSlot;
  }

  public int getNumGuests() {
    return numGuests;
  }

  public void setNumGuests(int numGuests) {
    this.numGuests = numGuests;
  }
}
