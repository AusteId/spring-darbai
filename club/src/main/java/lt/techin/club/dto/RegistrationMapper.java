package lt.techin.club.dto;

import lt.techin.club.model.Registration;
import lt.techin.club.model.RunningEvent;
import lt.techin.club.model.User;

public class RegistrationMapper {

  public static Registration toRegistration(RegistrationRequestDTO registrationRequestDTO, User user, RunningEvent runningEvent) {
    Registration registration = new Registration();
    registration.setUser(user);
    registration.setRunningEvent(runningEvent);
    registration.setRegistrationDate(registrationRequestDTO.);
  }


}
