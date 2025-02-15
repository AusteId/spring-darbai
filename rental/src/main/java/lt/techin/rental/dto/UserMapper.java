package lt.techin.rental.dto;

import lt.techin.rental.model.User;

public class UserMapper {

  public static CreateUserResponseDTO toCreateUserResponseDTO(User user) {
    return new CreateUserResponseDTO(user.getId(), user.getUsername(), user.getRoles());
  }

  public static User toUser(CreateUserRequestDTO createUserRequestDTO) {
    User user = new User();
    user.setUsername(createUserRequestDTO.username());
    user.setPassword(createUserRequestDTO.password());
    user.setRoles(createUserRequestDTO.roles());
    return user;
  }
}
