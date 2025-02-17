package lt.techin.club.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lt.techin.club.model.Role;

import java.util.List;

public record UserRequestDTO(
        @NotNull
        @Size(min = 4, max = 100, message = "Username input should be 4-100 characters long")
        @Pattern(regexp = "^[a-z0-9]+$",
                message = "Username must contain only lowercase letters and numbers")
        String username,
        @NotNull
        @Size(min = 6, max = 150, message = "Password input should be 6-150 characters long")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{10,255}$",
                message = "Password must contain at least one uppercase letter, " +
                        "one lowercase letter, and one digit")
        String password,
        @NotNull
        List<Role> roles) {
}
