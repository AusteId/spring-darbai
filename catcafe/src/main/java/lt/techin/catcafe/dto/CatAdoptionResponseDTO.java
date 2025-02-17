package lt.techin.catcafe.dto;

import java.sql.Timestamp;

public record CatAdoptionResponseDTO(
        Long id,
        String catName,
        String status,
        Timestamp applicationDate
) {
}
