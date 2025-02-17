package lt.techin.club.dto;

import java.time.LocalDate;

public record RunningEventResponseDTO(
        long id,
        String name,
        LocalDate calendarDate,
        String location,
        int maxParticipants
) {
}
