package lt.techin.rental.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.Year;

public record CarRequestDTO(
        @NotBlank(message = "Car brand is required")
        @Size(max = 255, message = "Brand should be maximum 255 character long")
        String brand,
        @NotBlank(message = "Car model is required")
        @Size(max = 255, message = "Model should be maximum 255 character long")
        String model,
        @NotNull(message = "Manufacturing year is required")
        @Max(value = 2026, message = "Year can't be in the future")
        int year
) {
}
