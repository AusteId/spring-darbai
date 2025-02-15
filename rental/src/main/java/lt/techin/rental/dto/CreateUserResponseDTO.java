package lt.techin.rental.dto;

import lt.techin.rental.model.Role;

import java.util.List;

public record CreateUserResponseDTO(
        long id,
        String username,
        List<Role> roles
) {
}
