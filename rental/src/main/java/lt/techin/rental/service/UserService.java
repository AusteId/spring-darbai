package lt.techin.rental.service;

import lt.techin.rental.model.Role;
import lt.techin.rental.model.User;
import lt.techin.rental.repository.RoleRepository;
import lt.techin.rental.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private RoleRepository roleRepository;

  public UserService(UserRepository userRepository, RoleRepository roleRepository) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
  }

  public User saveUser(User user) {

    Role userRole = roleRepository.findByName("USER")
            .orElseGet(() -> roleRepository.save(new Role("USER")));
    user.setRoles(List.of(userRole));

    return userRepository.save(user);
  }

  public Optional<User> findUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public List<User> findAllUsers() {
    return userRepository.findAll();
  }

  public User addRoleToUser(long userId, String roleName) {

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

    Role role = roleRepository.findByName(roleName)
            .orElseThrow(() -> new RuntimeException("Role not found"));

    if (user.getRoles().contains(role)) {
      throw new IllegalStateException(("User already has this role"));
    }

    user.getRoles().add(role);

    return userRepository.save(user);
  }
}
