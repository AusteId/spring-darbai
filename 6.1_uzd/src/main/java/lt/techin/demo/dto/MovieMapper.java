package lt.techin.demo.dto;

import lt.techin.demo.model.Movie;

import java.util.List;

public class MovieMapper {

  public static List<MovieDTO> toMovieDTOList(List<Movie> movies) {
    List<MovieDTO> result = movies.stream()
            .map(movie -> new MovieDTO(movie.getId(), movie.getTitle(), movie.getDirector(), movie.getScreenings()))
            .toList();

    return result;
  }

  public static MovieDTO toMovieDTO(Movie movie) {
    return new MovieDTO(movie.getId(), movie.getTitle(), movie.getDirector(), movie.getScreenings());
  }


}
