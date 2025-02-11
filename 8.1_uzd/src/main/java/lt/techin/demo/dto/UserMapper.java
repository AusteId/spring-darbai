package lt.techin.demo.dto;

import lt.techin.demo.model.User;

public class UserMapper {

  public static CreateUserResponseDTO toCreateUserResponseDTO(User user) {
    return new CreateUserResponseDTO(user.getUsername(), user.getRoles());
  }

  public static User toUser(CreateUserRequestDTO createUserRequestDTO) {
    User user = new User();
    user.setUsername(createUserRequestDTO.username());
    user.setPassword(createUserRequestDTO.password());
    user.setRoles(createUserRequestDTO.roles());
    return user;
  }
}


