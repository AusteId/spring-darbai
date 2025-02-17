package lt.techin.club.dto;

import lt.techin.club.model.Registration;
import lt.techin.club.model.RunningEvent;
import lt.techin.club.model.User;

import java.time.LocalDateTime;

public class RegistrationMapper {

  public static Registration toRegistration(RegistrationRequestDTO registrationRequestDTO, User user, RunningEvent runningEvent) {
    Registration registration = new Registration();
    registration.setRegistrationDate(LocalDateTime.now());
    registration.setUser(user);
    registration.setRunningEvent(runningEvent);

    return registration;
  }

  public static RegistrationResponseDTO toRegistrationResponseDTO(Registration registration) {
    return new RegistrationResponseDTO(
            registration.getId(),
            registration.getUser().getId(),
            registration.getRunningEvent().getName(),
            registration.getRegistrationDate()
    );
  }


}



