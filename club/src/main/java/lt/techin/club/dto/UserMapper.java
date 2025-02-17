package lt.techin.club.dto;

import lt.techin.club.model.User;

public class UserMapper {

  public static UserResponseDTO toCreateUserResponseDTO(User user) {
    return new UserResponseDTO(user.getId(), user.getUsername(), user.getRoles());
  }

  public static User toUser(UserRequestDTO createUserRequestDTO) {
    User user = new User();
    user.setUsername(createUserRequestDTO.username());
    user.setPassword(createUserRequestDTO.password());
    user.setRoles(createUserRequestDTO.roles());
    return user;
  }
}
