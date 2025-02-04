package lt.techin.demo.controller;

import jakarta.validation.Valid;
import lt.techin.demo.model.Movie;
import lt.techin.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class MovieController {

  private final MovieService movieService;

  @Autowired
  public MovieController(MovieService movieService) {
    this.movieService = movieService;
  }

  @GetMapping("/movies")
  public ResponseEntity<List<Movie>> getMovies() {
    return ResponseEntity.ok(movieService.findAllMovies());
  }

  @GetMapping("/movies/{id}")
  public ResponseEntity<?> getMovie(@PathVariable long id) {

    Optional<Movie> foundMovie = movieService.findMovieById(id);

    if (foundMovie.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(foundMovie.get());

  }

  @PostMapping("/movies")
  public ResponseEntity<?> saveMovie(@Valid @RequestBody Movie movie) {

//    if (movie.getTitle() == null || movie.getTitle().isEmpty()) {
//      return ResponseEntity.badRequest().body("Movie title field can't be empty");
//    }
//
//    if (movie.getDirector() == null || movie.getDirector().isEmpty()) {
//      return ResponseEntity.badRequest().body("Movie director field can't be empty");
//    }

    if (movieService.existsMovieByTitle(movie.getTitle())
            && movieService.existsMovieByDirector(movie.getDirector())) {
      return ResponseEntity.badRequest().body("A movie with such title and director already exist");
    }

    Movie savedMovie = movieService.saveMovie(movie);

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedMovie.getId())
                    .toUri())
            .body(savedMovie);
  }

  @PutMapping("/movies/{id}")
  public ResponseEntity<?> updateMovie(@PathVariable long id, @Valid @RequestBody Movie movie) {

//    if (movie.getTitle() == null || movie.getTitle().isEmpty()) {
//      return ResponseEntity.badRequest().body("Movie title field can't be empty");
//    }
//
//    if (movie.getDirector() == null || movie.getDirector().isEmpty()) {
//      return ResponseEntity.badRequest().body("Movie director field can't be empty");
//    }

    if (movieService.existMovieById(id)) {
      Movie movieToUpdate = movieService.findMovieById(id).get();
      movieToUpdate.setTitle(movie.getTitle());
      movieToUpdate.setDirector(movie.getDirector());
      movieToUpdate.setScreenings(movie.getScreenings());
      return ResponseEntity.ok(movieService.saveMovie(movieToUpdate));
    }

    Movie savedMovie = movieService.saveMovie(movie);

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .replacePath("/api/movies/{id}")
                    .buildAndExpand(savedMovie.getId())
                    .toUri())
            .body(savedMovie);
  }

  @DeleteMapping("/movies/{id}")
  public ResponseEntity<Void> deleteMovie(@PathVariable long id) {

    if (!movieService.existMovieById(id)) {
      return ResponseEntity.notFound().build();
    }

    movieService.deleteMovieById(id);
    return ResponseEntity.noContent().build();
  }
}
