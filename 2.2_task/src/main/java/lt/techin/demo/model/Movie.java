package lt.techin.demo.model;

import java.util.UUID;

public class Movie {

  private String id;
  private String title;
  private String director;

  public Movie() {
//    this.id = UUID.randomUUID().toString();
  }

  public Movie(String id, String title, String director) {
    this.id = id;
    this.title = title;
    this.director = director;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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
}
