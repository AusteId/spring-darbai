package lt.techin.rental.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RentalResponseDTO(
        long id,
        long userId,
        String username,
        long carId,
        LocalDate rentalStart,
        LocalDate rentalEnd,
        BigDecimal price,
        String brand,
        String model,
        int year
) {
}
