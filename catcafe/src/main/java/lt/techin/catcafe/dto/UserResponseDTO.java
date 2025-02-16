package lt.techin.catcafe.dto;

import lt.techin.catcafe.model.Role;

import java.util.List;

public record UserResponseDTO(
        long id,
        String username,
        List<Role> roles
) {
}
