package lt.techin.catcafe.dto;

import java.time.LocalDate;

public record ReservationResponseDTO(
        long id,
        long userId,
        LocalDate dateOfReservation,
        String timeSlot,
        int numGuests
) {
}
