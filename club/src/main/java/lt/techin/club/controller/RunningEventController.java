package lt.techin.club.controller;

import jakarta.validation.Valid;
import lt.techin.club.dto.*;
import lt.techin.club.model.Registration;
import lt.techin.club.model.RunningEvent;
import lt.techin.club.model.User;
import lt.techin.club.service.RegistrationService;
import lt.techin.club.service.RunningEventService;
import lt.techin.club.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RunningEventController {

  private RunningEventService runningEventService;
  private UserService userService;
  private RegistrationService registrationService;

  @Autowired
  public RunningEventController(RunningEventService runningEventService, UserService userService, RegistrationService registrationService) {
    this.runningEventService = runningEventService;
    this.userService = userService;
    this.registrationService = registrationService;
  }

  @GetMapping("/events")
  public ResponseEntity<List<RunningEventResponseDTO>> getAllRunningEvents() {

    List<RunningEvent> runningEvents = runningEventService.findAllRunningEvents();
    List<RunningEventResponseDTO> runningEventsDTO = runningEvents.stream()
            .map(event -> RunningEventMapper.toRunningEventResponseDTO(event))
            .toList();

    return ResponseEntity.ok(runningEventsDTO);
  }

  @GetMapping("/events/{eventId}/participants")
  public ResponseEntity<List<ParticipantResponseDTO>> getParticipants(@PathVariable Long eventId) {

    Optional<RunningEvent> runningEvent = runningEventService.findRunningEventById(eventId);

    if (runningEvent.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    List<ParticipantResponseDTO> participants = runningEvent.get().getRegistrations().stream()
            .map(a -> new ParticipantResponseDTO(a.getUser().getId(), a.getUser().getUsername()))
            .toList();
    return ResponseEntity.ok(participants);
  }

  @PostMapping("/events")
  public ResponseEntity<RunningEventResponseDTO> createRunningEvent(@Valid @RequestBody RunningEventRequestDTO runningEventRequestDTO,
                                                                    @AuthenticationPrincipal User user) {

    if (user == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    if (!user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    RunningEvent runningEvent = new RunningEvent();
    runningEvent.setName(runningEventRequestDTO.name());
    runningEvent.setCalendarDate(runningEventRequestDTO.calendarDate());
    runningEvent.setLocation(runningEventRequestDTO.location());
    runningEvent.setMaxParticipants(runningEventRequestDTO.maxParticipants());

    RunningEvent savedRunningEvent = runningEventService.saveRunningEvent(runningEvent);

    RunningEventResponseDTO runningEventResponseDTO = new RunningEventResponseDTO(
            savedRunningEvent.getId(),
            savedRunningEvent.getName(),
            savedRunningEvent.getCalendarDate(),
            savedRunningEvent.getLocation(),
            savedRunningEvent.getMaxParticipants()
    );

    return ResponseEntity.status(HttpStatus.CREATED).body(runningEventResponseDTO);
  }

  @PostMapping("/events/{eventId}/register")
  public ResponseEntity<RegistrationResponseDTO> createRegistrationForEvent(@PathVariable long eventId,
                                                                            @Valid @RequestBody RegistrationRequestDTO registrationRequestDTO,
                                                                            @AuthenticationPrincipal User user) {

    if (user == null) {
      return ResponseEntity.notFound().build();
    }

    Optional<RunningEvent> runningEvent = runningEventService.findRunningEventById(eventId);

    if (runningEvent.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    Registration registration = registrationService.registerUserForEvent(user, runningEvent.get());
    RegistrationResponseDTO registrationResponseDTO = RegistrationMapper.toRegistrationResponseDTO(registration);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(registration.getId())
                            .toUri())
            .body(registrationResponseDTO);
  }

  @DeleteMapping("/events/{eventId}")
  public ResponseEntity<Void> deleteRunningEvent(@PathVariable long id, @AuthenticationPrincipal User user) {

    if (!user.getRoles().stream().anyMatch(role -> role.getName().equals("ROLE_ADMIN"))) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    Optional<RunningEvent> runningEvent = runningEventService.findRunningEventById(id);

    if (runningEvent.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    runningEventService.deleteRunningEvent(id);

    return ResponseEntity.noContent().build();
  }
}
