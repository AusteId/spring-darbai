package lt.techin.catcafe.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lt.techin.catcafe.model.Role;

import java.util.List;

public record UserRequestDTO(
        @NotNull
        @Size(min = 4, max = 255, message = "Username input should be 4-255 characters long")
        String username,
        @NotNull
        @Size(min = 10, max = 255, message = "Password input should be 10-255 characters long")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{10,255}$",
                message = "Password must contain at least one uppercase letter, " +
                        "one lowercase letter, and one digit")
        String password,
        @NotNull
        List<Role> roles) {
}
