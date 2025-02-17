package lt.techin.catcafe.controller;

import jakarta.validation.Valid;
import lt.techin.catcafe.dto.ReservationMapper;
import lt.techin.catcafe.dto.ReservationRequestDTO;
import lt.techin.catcafe.dto.ReservationResponseDTO;
import lt.techin.catcafe.model.Reservation;
import lt.techin.catcafe.model.User;
import lt.techin.catcafe.service.ReservationService;
import lt.techin.catcafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

  private final ReservationService reservationService;
  private final UserService userService;

  @Autowired
  public ReservationController(ReservationService reservationService, UserService userService) {
    this.reservationService = reservationService;
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<ReservationResponseDTO> createReservation(@Valid @RequestBody ReservationRequestDTO requestDTO,
                                                                  @AuthenticationPrincipal User user) {

    if (user == null) {
      return ResponseEntity.notFound().build();
    }

    Reservation reservation = ReservationMapper.toReservation(requestDTO, user);
    Reservation savedReservation = reservationService.saveReservation(reservation);

    ReservationResponseDTO responseDTO = ReservationMapper.toReservationResponseDTO(savedReservation);

    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

  @GetMapping
  public ResponseEntity<List<ReservationResponseDTO>> getAllReservations() {
    List<Reservation> reservations = reservationService.findAllReservations();
    List<ReservationResponseDTO> reservationDTOs = reservations.stream()
            .map(ReservationMapper::toReservationResponseDTO)
            .toList();

    return ResponseEntity.ok(reservationDTOs);
  }

  @GetMapping("/{id}")
  public ResponseEntity<ReservationResponseDTO> getReservationById(@PathVariable long id) {

    Optional<Reservation> reservation = reservationService.findReservationById(id);

    if (reservation.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    ReservationResponseDTO responseDTO = ReservationMapper.toReservationResponseDTO(reservation.get());
    return ResponseEntity.ok(responseDTO);
  }

  @GetMapping("/users/{userId}/reservations")
  public ResponseEntity<List<ReservationResponseDTO>> getReservationsByUserId(@PathVariable long userId) {
    List<Reservation> reservations = reservationService.findReservationsByUserId(userId);
    List<ReservationResponseDTO> responseDTOs = reservations.stream()
            .map(ReservationMapper::toReservationResponseDTO)
            .toList();

    return ResponseEntity.ok(responseDTOs);
  }

  @PutMapping("/{id}")
  public ResponseEntity<ReservationResponseDTO> updateReservation(@PathVariable long id,
                                                                  @Valid @RequestBody ReservationRequestDTO requestDTO) {

    Optional<Reservation> existingReservation = reservationService.findReservationById(id);

    if (existingReservation.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    Reservation reservation = existingReservation.get();
    reservation.setDateOfReservation(requestDTO.dateOfReservation());
    reservation.setTimeSlot(requestDTO.timeSlot());
    reservation.setNumGuests(requestDTO.numGuests());

    Reservation updatedReservation = reservationService.saveReservation(reservation);
    ReservationResponseDTO responseDTO = ReservationMapper.toReservationResponseDTO(updatedReservation);

    return ResponseEntity.ok(responseDTO);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteReservation(@PathVariable long id, @AuthenticationPrincipal User user) {

    if (!user.getRoles().stream().anyMatch(a -> a.getName().equals("ROLE_ADMIN"))) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    Optional<Reservation> reservation = reservationService.findReservationById(id);

    if (reservation.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    reservationService.deleteReservation(id);

    return ResponseEntity.ok().build();
  }
}
