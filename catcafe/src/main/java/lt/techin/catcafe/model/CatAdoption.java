package lt.techin.catcafe.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class CatAdoption {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private String catName;

  @Column(nullable = false)
  private String status;

  @Column(nullable = false)
  private Timestamp applicationDate;

  public CatAdoption() {
  }

  public CatAdoption(User user, String catName, String status, Timestamp applicationDate) {
    this.user = user;
    this.catName = catName;
    this.status = status;
    this.applicationDate = applicationDate;
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

  public String getCatName() {
    return catName;
  }

  public void setCatName(String catName) {
    this.catName = catName;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Timestamp getApplicationDate() {
    return applicationDate;
  }

  public void setApplicationDate(Timestamp applicationDate) {
    this.applicationDate = applicationDate;
  }
}
