package lt.techin.rental.dto;

public record CarResponseDTO(
        long id,
        String brand,
        String model,
        int year,
        String status
) {

}
