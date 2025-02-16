package lt.techin.rental.service;

import lt.techin.rental.model.Role;
import lt.techin.rental.model.User;
import lt.techin.rental.repository.RoleRepository;
import lt.techin.rental.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RolesLoader implements CommandLineRunner {

  private final RoleRepository roleRepository;
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public RolesLoader(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void run(String... args) throws Exception {

//    if (roleRepository.findByName("USER").isEmpty()) {
//      roleRepository.save(new Role("USER"));
//    }
//
//    if (roleRepository.findByName("ADMIN").isEmpty()) {
//      roleRepository.save(new Role("ADMIN"));
//    }

    Role userRole = roleRepository.findByName("USER")
            .orElseGet(() -> roleRepository.save(new Role("USER")));

    Role adminRole = roleRepository.findByName("ADMIN")
            .orElseGet(() -> roleRepository.save(new Role("ADMIN")));

    if (userRepository.findByUsername("User").isEmpty()) {
      User user = new User();
      user.setUsername("User");
      user.setPassword(passwordEncoder.encode("User123"));
      user.setRoles(List.of(userRole));
      userRepository.save(user);
    }

    if (userRepository.findByUsername("Admin").isEmpty()) {
      User admin = new User();
      admin.setUsername("Admin");
      admin.setPassword(passwordEncoder.encode("Admin123"));
      admin.setRoles(List.of(userRole, adminRole));
      userRepository.save(admin);
    }
  }
}
