package lt.techin.club.dto;

import jakarta.validation.constraints.NotNull;
import lt.techin.club.model.User;

public record RegistrationRequestDTO(
        @NotNull
        User user
) {
}
