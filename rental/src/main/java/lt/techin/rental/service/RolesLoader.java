package lt.techin.rental.service;

import lt.techin.rental.model.Role;
import lt.techin.rental.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RolesLoader implements CommandLineRunner {

  private final RoleRepository roleRepository;

  @Autowired
  public RolesLoader(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public void run(String... args) throws Exception {

    if (roleRepository.findByName("USER").isEmpty()) {
      roleRepository.save(new Role("USER"));
    }

    if (roleRepository.findByName("ADMIN").isEmpty()) {
      roleRepository.save(new Role("ADMIN"));
    }
  }
}
