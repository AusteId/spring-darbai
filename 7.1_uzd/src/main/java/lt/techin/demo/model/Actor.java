package lt.techin.demo.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "actors")
public class Actor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String firstName;
  private String lastName;
  private LocalDate birthdate;

  public Actor() {
  }

  public Actor(LocalDate birthdate, String lastName, String firstName) {
    this.birthdate = birthdate;
    this.lastName = lastName;
    this.firstName = firstName;
  }

  public long getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public LocalDate getBirthdate() {
    return birthdate;
  }

  public void setBirthdate(LocalDate birthdate) {
    this.birthdate = birthdate;
  }
}
