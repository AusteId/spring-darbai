package lt.techin.demo.controller;

import jakarta.validation.Valid;
import lt.techin.demo.dto.MovieDTO;
import lt.techin.demo.dto.MovieMapper;
import lt.techin.demo.model.Movie;
import lt.techin.demo.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
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
  public ResponseEntity<List<MovieDTO>> getMovies() {
    return ResponseEntity.ok(MovieMapper.toMovieDTOList(movieService.findAllMovies()));
//    return ResponseEntity.ok(movieService.findAllMovies());
  }

  @GetMapping("/movies/{id}")
  public ResponseEntity<MovieDTO> getMovie(@PathVariable long id) {

    Optional<Movie> foundMovie = movieService.findMovieById(id);

    if (foundMovie.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

//    return ResponseEntity.ok(foundMovie.get());
    return ResponseEntity.ok(MovieMapper.toMovieDTO(foundMovie.get()));

  }

  @PostMapping("/movies")
  public ResponseEntity<?> saveMovie(@Valid @RequestBody MovieDTO movieDTO) {

//    if (movie.getTitle() == null || movie.getTitle().isEmpty()) {
//      return ResponseEntity.badRequest().body("Movie title field can't be empty");
//    }
//
//    if (movie.getDirector() == null || movie.getDirector().isEmpty()) {
//      return ResponseEntity.badRequest().body("Movie director field can't be empty");
//    }

    if (movieService.existsMovieByTitle(movieDTO.title())
            && movieService.existsMovieByDirector(movieDTO.director())) {
      return ResponseEntity.badRequest().body("A movie with such title and director already exist");
    }

    Movie savedMovie = movieService.saveMovie(MovieMapper.toMovie(movieDTO));

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedMovie.getId())
                    .toUri())
            .body(MovieMapper.toMovieDTO(savedMovie));
  }

  @PutMapping("/movies/{id}")
  public ResponseEntity<?> updateMovie(@PathVariable long id, @Valid @RequestBody MovieDTO movieDTO) {

    if (movieService.existMovieById(id)) {
//      Movie movieToUpdate = movieService.findMovieById(id).get();
//      movieToUpdate.setTitle(movie.getTitle());
//      movieToUpdate.setDirector(movie.getDirector());
//      movieToUpdate.setScreenings(movie.getScreenings());
//      movieToUpdate.setActors(movie.getActors());
//      return ResponseEntity.ok(movieService.saveMovie(movieToUpdate));

      Movie movieToUpdate = movieService.findMovieById(id).get();
      MovieMapper.updateMovieFromDTO(movieToUpdate, movieDTO);
      Movie updatedMovie = movieService.saveMovie(movieToUpdate);

      return ResponseEntity.ok(updatedMovie);
    }

    Movie savedMovie = movieService.saveMovie(MovieMapper.toMovie(movieDTO));

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

  @GetMapping("/movies/pagination")
  public ResponseEntity<Page<Movie>> getMoviesPage(@RequestParam int page,
                                                   @RequestParam int size,
                                                   @RequestParam(required = false) String sort) {
    return ResponseEntity.ok(movieService.findAllMoviesPage(page, size, sort));
  }
}
