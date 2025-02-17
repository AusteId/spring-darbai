package lt.techin.club.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record RunningEventRequestDTO(
        @NotNull
        @Size(min = 5, max = 255, message = "Name input should be 5-255 characters long")
        String name,
        @NotNull
        @Future(message = "Running event must be in the future")
        LocalDate calendarDate,
        @NotNull
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Location input can contain only letters and numbers")
        String location,
        @Min(value = 1, message = "Must be at least 1 participant")
        int maxParticipants
) {
}
