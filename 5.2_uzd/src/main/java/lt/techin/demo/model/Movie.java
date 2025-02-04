package lt.techin.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotNull
  @Size(max = 100, message = "Movie title input should be maximum 100 character long")
  @Pattern(regexp = "^[A-Z][A-Za-z0-9\\s\\-]*$", message = "Title input should start from uppercase letter")
  private String title;

  @NotNull
  @Size(min = 2, max = 100, message = "Movie director input should be 2-100 character long")
  @Pattern(regexp = "[A-Z][A-Za-z\\s\\-]*$", message = "Director input should start " +
          "from uppercase letter and can't contain numbers")
  private String director;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "movie_id")
  private List<Screening> screenings;

  public Movie() {
  }

  public Movie(String title, String director, List<Screening> screenings) {
    this.title = title;
    this.director = director;
    this.screenings = screenings;
  }

  public long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDirector() {
    return director;
  }

  public void setDirector(String director) {
    this.director = director;
  }

  public List<Screening> getScreenings() {
    return screenings;
  }

  public void setScreenings(List<Screening> screenings) {
    this.screenings = screenings;
  }
}
