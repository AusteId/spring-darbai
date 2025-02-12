package lt.techin.demo.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lt.techin.demo.model.Role;

import java.util.List;

public record CreateUserRequestDTO(long id,
                                   @NotNull
                                   @Size(max = 100, message = "Username should be maximum 100 character long")
                                   String username,
                                   @NotNull
                                   @Size(max = 255, message = "Password should be maximum 250 character long")
                                   @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d).+$")
                                   String password,
                                   List<Role> roles) {

}
