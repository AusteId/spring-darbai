package lt.techin.club.service;

import lt.techin.club.model.Role;
import lt.techin.club.model.User;
import lt.techin.club.repository.RoleRepository;
import lt.techin.club.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

   

    return userRepository.save(user);
  }

  public Optional<User> findUserByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public Optional<User> findUserById(long id) {
    return userRepository.findById(id);
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
