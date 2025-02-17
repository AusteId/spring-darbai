package lt.techin.club.service;

import lt.techin.club.model.Registration;
import lt.techin.club.model.RunningEvent;
import lt.techin.club.model.User;
import lt.techin.club.repository.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RegistrationService {

  private final RegistrationRepository registrationRepository;

  @Autowired
  public RegistrationService(RegistrationRepository registrationRepository) {
    this.registrationRepository = registrationRepository;
  }

  public Registration registerUserForEvent(User user, RunningEvent runningEvent) {

    Optional<Registration> existingRegistration = registrationRepository.findByUserIdAndRunningEventId(user.getId(), runningEvent.getId());

    if (existingRegistration.isPresent()) {
      throw new IllegalStateException("User already registered for this event");
    }

    Registration registration = new Registration();
    registration.setUser(user);
    registration.setRunningEvent(runningEvent);
    registration.setRegistrationDate(LocalDateTime.now());

    return registrationRepository.save(registration);
  }
}
