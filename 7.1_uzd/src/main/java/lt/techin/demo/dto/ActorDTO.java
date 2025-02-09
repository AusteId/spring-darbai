package lt.techin.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record ActorDTO(long id,
                       @NotNull
                       @Size(max = 100, message = "Firstname should be maximum 100 character long")
                       @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Firstname should start from uppercase letter")
                       String firstName,
                       @NotNull
                       @Size(max = 100, message = "Lastname should be maximum 100 character long")
                       @Pattern(regexp = "^[A-Z][a-zA-Z]*$", message = "Lastname should start from uppercase letter")
                       String lastName,
                       @NotNull
                       @Past
                       @JsonFormat(pattern = "yyyy-MM-dd")
                       LocalDate birthdate
) {
}
