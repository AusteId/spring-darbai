package lt.techin.club.controller;

import jakarta.validation.Valid;
import lt.techin.club.dto.RunningEventMapper;
import lt.techin.club.dto.RunningEventRequestDTO;
import lt.techin.club.dto.RunningEventResponseDTO;
import lt.techin.club.dto.UserResponseDTO;
import lt.techin.club.model.RunningEvent;
import lt.techin.club.model.User;
import lt.techin.club.service.RunningEventService;
import lt.techin.club.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RunningEventController {

  private RunningEventService runningEventService;
  private UserService userService;

  @Autowired
  public RunningEventController(RunningEventService runningEventService, UserService userService) {
    this.runningEventService = runningEventService;
    this.userService = userService;
  }

  @GetMapping("/events")
  public ResponseEntity<List<RunningEventResponseDTO>> getAllRunningEvents() {

    List<RunningEvent> runningEvents = runningEventService.findAllRunningEvents();
    List<RunningEventResponseDTO> runningEventsDTO = runningEvents.stream()
            .map(event -> RunningEventMapper.toRunningEventResponseDTO(event))
            .toList();

    return ResponseEntity.ok(runningEventsDTO);
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

    return ResponseEntity.ok().build();
  }

  //  @GetMapping("/events/{eventId}/participants")
//  public ResponseEntity<List<UserResponseDTO>> getRegisteredUsersByEventId(@PathVariable long eventId) {
//    List<User> users = userService.findRegisteredUsersByEventId(eventId);
//  }
}
