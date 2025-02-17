package lt.techin.catcafe.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record ReservationRequestDTO(
        @NotNull(message = "Date of reservation cannot be null")
        @FutureOrPresent(message = "Reservation date must be in the future or present")
        LocalDate dateOfReservation,
        @NotNull(message = "Time slot cannot be null")
        @Size(max = 50, message = "Time slot cannot exceed 50 characters")
        String timeSlot,
        @NotNull(message = "Number of guests cannot be null")
        @Min(value = 1, message = "There must be at least 1 guest")
        @Max(value = 4, message = "There must be no more then 4 guests")
        int numGuests
) {
}