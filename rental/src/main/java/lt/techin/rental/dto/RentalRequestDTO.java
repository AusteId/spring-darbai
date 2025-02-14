package lt.techin.rental.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RentalRequestDTO(
        @NotNull(message = "User ID is required")
        Long userId,
        @NotNull(message = "Car ID is required")
        Long carId,
        @NotNull(message = "Rental start date is required")
        @FutureOrPresent(message = "Rental start date must be in the present or future")
        LocalDate rentalStart,
        @NotNull(message = "Rental end date is required")
        @Future(message = "Rental end date must be in the future")
        LocalDate rentalEnd,
        @NotNull(message = "Price is required")
        @Positive(message = "Price must be positive number")
        BigDecimal price
) {
}
