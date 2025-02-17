package lt.techin.club.dto;

import lt.techin.club.model.Role;

import java.util.List;

public record UserResponseDTO(
        long id,
        String username,
        List<Role> roles
) {
}
