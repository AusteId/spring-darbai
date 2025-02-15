package lt.techin.rental.controller;

import jakarta.validation.Valid;
import lt.techin.rental.dto.CreateUserRequestDTO;
import lt.techin.rental.dto.CreateUserResponseDTO;
import lt.techin.rental.dto.UserMapper;
import lt.techin.rental.model.User;
import lt.techin.rental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
  public ResponseEntity<List<CreateUserResponseDTO>> getUsers() {
    List<User> users = userService.findAllUsers();
    List<CreateUserResponseDTO> usersDTO = users.stream()
            .map(a -> UserMapper.toCreateUserResponseDTO(a))
            .toList();

    return ResponseEntity.ok(usersDTO);
  }

  @PostMapping("/users")
  public ResponseEntity<CreateUserResponseDTO> addUser(@Valid @RequestBody CreateUserRequestDTO createUserRequestDTO) {

    User user = UserMapper.toUser(createUserRequestDTO);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User savedUser = userService.saveUser(user);

    CreateUserResponseDTO savedUserDTO = UserMapper.toCreateUserResponseDTO(savedUser);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedUser.getId())
                            .toUri())
            .body(savedUserDTO);
  }


}

