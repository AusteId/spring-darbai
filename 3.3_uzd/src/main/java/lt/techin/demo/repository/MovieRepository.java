package lt.techin.demo.repository;

import lt.techin.demo.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {

  boolean existsByTitle(String title);

  boolean existsByDirector(String director);
}
