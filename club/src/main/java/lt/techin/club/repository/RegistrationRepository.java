package lt.techin.club.repository;

import lt.techin.club.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration, Long> {

  Optional<Registration> findByUserIdAndRunningEventId(Long userId, Long runningEventId);

}
