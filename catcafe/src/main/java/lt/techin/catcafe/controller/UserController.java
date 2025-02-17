package lt.techin.catcafe.controller;

import jakarta.validation.Valid;
import lt.techin.catcafe.dto.UserRequestDTO;
import lt.techin.catcafe.dto.UserResponseDTO;
import lt.techin.catcafe.dto.UserMapper;
import lt.techin.catcafe.model.User;
import lt.techin.catcafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

  private final UserService userService;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public UserController(UserService userService, PasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserResponseDTO>> getUsers() {
    List<User> users = userService.findAllUsers();
    List<UserResponseDTO> usersDTO = users.stream()
            .map(a -> UserMapper.toCreateUserResponseDTO(a))
            .toList();

    return ResponseEntity.ok(usersDTO);
  }

  @GetMapping("/users/{id}")
  public ResponseEntity<UserResponseDTO> getUserById(@PathVariable long id) {
    return userService.findUserById(id)
            .map(a -> ResponseEntity.ok(UserMapper.toCreateUserResponseDTO(a)))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
  }


  @PostMapping("/auth/register")
  public ResponseEntity<UserResponseDTO> addUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {

    if (userService.findUserByUsername(userRequestDTO.username()).isPresent()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    User user = UserMapper.toUser(userRequestDTO);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User savedUser = userService.saveUser(user);

    UserResponseDTO savedUserDTO = UserMapper.toCreateUserResponseDTO(savedUser);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedUser.getId())
                            .toUri())
            .body(savedUserDTO);
  }

  @PutMapping("/{userId}/roles/{roleName}")
  public ResponseEntity<User> addRoleToUser(@PathVariable long userId, @PathVariable String roleName) {

    if (roleName == null || roleName.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    User updatedUser = userService.addRoleToUser(userId, roleName);

    return ResponseEntity.ok(updatedUser);
  }


}

