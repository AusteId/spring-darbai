package lt.techin.rental.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CarRequestDTO(
        @NotNull
        @Size(max = 255, message = "Brand should be maximum 255 character long")
        String brand,
        @NotNull
        @Size(max = 255, message = "Model should be maximum 255 character long")
        String model,
        int year
) {
}
