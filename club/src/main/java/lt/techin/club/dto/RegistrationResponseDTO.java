package lt.techin.club.dto;

import lt.techin.club.model.User;

import java.time.LocalDateTime;

public record RegistrationResponseDTO(
        long id,
        long userId,
        String eventName,
        LocalDateTime registrationDate
) {
}
