package lt.techin.demo.controller;

import lt.techin.demo.model.Movie;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class MovieController {

  private List<Movie> movies = new ArrayList<>();

  @GetMapping("/movies")
  public ResponseEntity<List<Movie>> getMovies() {
    return ResponseEntity.ok(movies);
  }

  @GetMapping("/movies/{index}")
  public ResponseEntity<Movie> getMovie(@PathVariable int index) {

    if (index > movies.size() - 1) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(movies.get(index));
  }

//  @GetMapping("/movies/search")
//  public ResponseEntity<Movie> searchMovieByTitle(@RequestParam String title) {
//
//    Optional<Movie> foundMovie = movies.stream().filter(a -> a.getTitle().toLowerCase()
//            .contains(title.toLowerCase())).findFirst();
//
//    if (foundMovie.isEmpty()) {
//      return ResponseEntity.notFound().build();
//    }
//
//    return ResponseEntity.ok(foundMovie.get());
//  }

  @GetMapping("/movies/search")
  public ResponseEntity<List<Movie>> searchMoviesByTitle(@RequestParam String title) {

    List foundedMovies = movies.stream()
            .filter(a -> a.getTitle().toLowerCase().contains(title.toLowerCase()))
            .toList();

    return ResponseEntity.ok(foundedMovies);
  }

  @PostMapping("/movies")
  public ResponseEntity<Movie> saveMovie(@RequestBody Movie movie) {
    if (movie.getTitle() == null || movie.getDirector() == null
            || movie.getTitle().isEmpty() || movie.getDirector().isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    movies.add(movie);

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{index}").buildAndExpand(movies.size() - 1).toUri())
            .body(movie);
  }

  @PutMapping("/movies/{index}")
  public ResponseEntity<?> updateMovie(@PathVariable int index, @RequestBody Movie movie) {

    if (movie.getTitle() == null || movie.getDirector() == null
            || movie.getTitle().isEmpty() || movie.getDirector().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title or author cannot be empty");
    }

    if (index <= movies.size() - 1) {
      Movie movieFromList = movies.get(index);

      movieFromList.setTitle(movie.getTitle());
      movieFromList.setDirector(movie.getDirector());
      movies.add(movieFromList);
      return ResponseEntity.ok(movieFromList);
    }

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{index}")
                    .buildAndExpand(movies.size() - 1)
                    .toUri())
            .body(movie);

  }

  @DeleteMapping("/movies/{index}")
  public ResponseEntity<Void> deleteMovie(@PathVariable int index) {

    if (index > movies.size() - 1) {
      return ResponseEntity.notFound().build();
    }

    movies.remove(index);
    return ResponseEntity.noContent().build();
    
  }
}
