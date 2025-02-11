package lt.techin.demo.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record ScreeningDTO(long id,
                           @NotNull
                           @Size(max = 100, message = "Theater input should be maximum 100 character long")
                           @Pattern(regexp = "^[A-Z][A-Za-z0-9\\s\\-]*$", message = "Theater input should start from uppercase letter")
                           String theater,
                           @NotNull
                           @FutureOrPresent(message = "The date must be in the present or future")
                           LocalDateTime dateAndTime) {
}
