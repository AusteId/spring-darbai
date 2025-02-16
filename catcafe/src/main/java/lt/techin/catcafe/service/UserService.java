package lt.techin.catcafe.service;

import lt.techin.catcafe.model.Role;
import lt.techin.catcafe.model.User;
import lt.techin.catcafe.repository.RoleRepository;
import lt.techin.catcafe.repository.UserRepository;
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

    Role userRole = roleRepository.findByName("ROLE_USER")
            .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

    List<Role> userRoles = new ArrayList<>(user.getRoles());

    if (userRoles.isEmpty()) {
      userRoles.add(userRole);
    }

    boolean hasAdminRole = userRoles.stream()
            .anyMatch(a -> a.getName().equals("ROLE_ADMIN"));

    if (hasAdminRole && !userRoles.contains(userRole)) {
      userRoles.add(userRole);
    }

    user.setRoles(userRoles);

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
