package lt.techin.demo.service;

import lt.techin.demo.model.Actor;
import lt.techin.demo.model.Movie;
import lt.techin.demo.repository.ActorRepository;
import lt.techin.demo.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

  private final MovieRepository movieRepository;

  @Autowired
  public MovieService(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  public List<Movie> findAllMovies() {
    return movieRepository.findAll();
  }

  public boolean existMovieById(long id) {
    return movieRepository.existsById(id);
  }

  public Optional<Movie> findMovieById(long id) {
    return movieRepository.findById(id);
  }

  public Movie saveMovie(Movie movie) {
    return movieRepository.save(movie);
  }

  public void deleteMovieById(long id) {
    movieRepository.deleteById(id);
  }

  public boolean existsMovieByTitle(String title) {
    return movieRepository.existsByTitle(title);
  }

  public boolean existsMovieByDirector(String director) {
    return movieRepository.existsByDirector(director);
  }

  public Page<Movie> findAllMoviesPage(int page, int size, String sort) {

    if (sort == null) {
      Pageable pageable = PageRequest.of(page, size);
      return movieRepository.findAll(pageable);
    }

    Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
    return movieRepository.findAll(pageable);
  }

}
