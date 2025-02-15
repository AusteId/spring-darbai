package lt.techin.rental.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lt.techin.rental.model.Role;

import java.util.List;

public record CreateUserRequestDTO(
        @NotNull
        @Size(max = 255, message = "Username should be maximum 255 character long")
        String username,
        @NotNull
        @Size(max = 255, message = "Password should be maximum 255 character long")
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$")
        String password,
        List<Role> roles) {
}
