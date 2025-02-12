package lt.techin.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lt.techin.demo.model.Actor;
import lt.techin.demo.model.Screening;

import java.util.List;

public record MovieDTO(long id,
                       @NotNull
                       @Size(max = 100, message = "Movie title input should be maximum 100 character long")
                       @Pattern(regexp = "^[A-Z][A-Za-z0-9\\s\\-]*$", message = "Title input should start from uppercase letter")
                       String title,
                       @NotNull
                       @Size(max = 100, message = "Movie director input should be maximum 100 character long")
                       @Pattern(regexp = "[A-Z][A-Za-z\\s\\-]*$", message = "Director input should start " +
                               "from uppercase letter and can't contain numbers")
                       String director,
                       List<Screening> screenings,
                       List<Actor> actors
) {
}
