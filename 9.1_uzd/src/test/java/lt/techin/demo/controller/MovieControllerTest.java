package lt.techin.demo.controller;

import com.jayway.jsonpath.JsonPath;
import lt.techin.demo.model.Actor;
import lt.techin.demo.model.Movie;
import lt.techin.demo.security.SecurityConfig;
import lt.techin.demo.service.MovieService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.swing.text.AbstractDocument;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = MovieController.class)
@Import(SecurityConfig.class)
public class MovieControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private MovieService movieService;

  private static Movie movieFirst;
  private static Movie movieSecond;

  @BeforeAll
  static void createTwoMovies() {
    movieFirst = new Movie("First title", "First director", List.of(), List.of(new Actor("Actor firstname", "Actor lastname", LocalDate.of(2000, 1, 1))));
    movieSecond = new Movie("Second title", "Second director", List.of(), List.of(new Actor("Actor firstname", "Actor lastname", LocalDate.of(2000, 1, 1))));
  }

  @Test
  @WithMockUser(username = "user", authorities = {"SCOPE_ROLE_USER"})
  void getMovies_whenFindAll_thenReturnAllAnd200() throws Exception {

    List<Movie> movies = List.of(movieFirst, movieSecond);
    given(movieService.findAllMovies()).willReturn(movies);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/movies"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("length()").value(2))
            .andExpect(jsonPath("[0].id").exists())
            .andExpect(jsonPath("[0].title").value("First title"))
            .andExpect(jsonPath("[0].director").value("First director"))
            .andExpect(jsonPath("[0].screenings").isArray())
            .andExpect(jsonPath("[0].screenings", Matchers.hasSize(0)))
            .andExpect(jsonPath("[0].actors").isArray())
            .andExpect(jsonPath("[0].actors", Matchers.hasSize(1)))
            .andExpect(jsonPath("[0].actors.[0].id").exists())
            .andExpect(jsonPath("[0].actors.[0].firstName").value("Actor firstname"))
            .andExpect(jsonPath("[0].actors.[0].lastName").value("Actor lastname"))
            .andExpect(jsonPath("[0].actors.[0].birthdate").value("2000-01-01"))

            .andExpect(jsonPath("[1].id").exists())
            .andExpect(jsonPath("[1].title").value("Second title"))
            .andExpect(jsonPath("[1].director").value("Second director"))
            .andExpect(jsonPath("[1].screenings").isArray())
            .andExpect(jsonPath("[1].screenings", Matchers.hasSize(0)))
            .andExpect(jsonPath("[1].actors").isArray())
            .andExpect(jsonPath("[1].actors", Matchers.hasSize(1)))
            .andExpect(jsonPath("[1].actors.[0].id").exists())
            .andExpect(jsonPath("[1].actors.[0].firstName").value("Actor firstname"))
            .andExpect(jsonPath("[1].actors.[0].lastName").value("Actor lastname"))
            .andExpect(jsonPath("[1].actors.[0].birthdate").value("2000-01-01"));

    Mockito.verify(movieService, times(1)).findAllMovies();
  }

  @Test
  @WithMockUser(username = "user", authorities = {"SCOPE_ROLE_USER"})
  void getMovies_whenFindAllAndListIsEmpty_thenReturn200() throws Exception {

    List<Movie> movies = List.of();
    given(movieService.findAllMovies()).willReturn(movies);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/movies"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("length()").value(0))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

    Mockito.verify(movieService, times(1)).findAllMovies();
  }

  @Test
  void getMovies_whenFindAllUnauthenticated_thenRespond401() throws Exception {

    List<Movie> movies = List.of(movieFirst, movieSecond);
    given(movieService.findAllMovies()).willReturn(movies);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/movies"))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string(""))
            .andExpect(jsonPath("$").doesNotExist());

    Mockito.verify(movieService, times(0)).findAllMovies();
  }


}
