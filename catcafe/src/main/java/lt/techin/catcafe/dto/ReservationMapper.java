package lt.techin.catcafe.dto;

import lt.techin.catcafe.model.Reservation;
import lt.techin.catcafe.model.User;

public class ReservationMapper {

  public static Reservation toReservation(ReservationRequestDTO dto, User user) {
    Reservation reservation = new Reservation();
    reservation.setDateOfReservation(dto.dateOfReservation());
    reservation.setTimeSlot(dto.timeSlot());
    reservation.setNumGuests(dto.numGuests());
    reservation.setUser(user);
    return reservation;
  }

  public static ReservationResponseDTO toReservationResponseDTO(Reservation reservation) {
    return new ReservationResponseDTO(
            reservation.getId(),
            reservation.getUser().getId(),
            reservation.getDateOfReservation(),
            reservation.getTimeSlot(),
            reservation.getNumGuests()
    );
  }
}
