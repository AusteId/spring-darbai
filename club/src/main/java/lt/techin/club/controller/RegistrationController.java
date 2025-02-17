package lt.techin.club.controller;

import jakarta.validation.Valid;
import lt.techin.club.dto.RegistrationMapper;
import lt.techin.club.dto.RegistrationRequestDTO;
import lt.techin.club.dto.RegistrationResponseDTO;
import lt.techin.club.model.Registration;
import lt.techin.club.model.RunningEvent;
import lt.techin.club.model.User;
import lt.techin.club.repository.RegistrationRepository;
import lt.techin.club.service.RegistrationService;
import lt.techin.club.service.RunningEventService;
import lt.techin.club.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RegistrationController {

  private final RegistrationService registrationService;
  private final UserService userService;
  private final RunningEventService runningEventService;

  @Autowired
  public RegistrationController(RegistrationService registrationService, UserService userService, RunningEventService runningEventService) {
    this.registrationService = registrationService;
    this.userService = userService;
    this.runningEventService = runningEventService;
  }

  @PostMapping("/events/{eventId}/register")
  public ResponseEntity<RegistrationResponseDTO> createRegistrationForEvent(@Valid @RequestBody RegistrationRequestDTO registrationRequestDTO,
                                                                            @AuthenticationPrincipal User user) {

    if (user == null) {
      return ResponseEntity.notFound().build();
    }

    Registration registration = RegistrationMapper.


  }



}
