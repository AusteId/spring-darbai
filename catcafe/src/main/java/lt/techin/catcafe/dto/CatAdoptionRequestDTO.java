package lt.techin.catcafe.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CatAdoptionRequestDTO(
        @NotNull
        @Size(max = 50, message = "Cat name must be less than 50 characters")
        String catName)
//        @NotNull
//        @Size(max = 50, message = "Status must be less than 50 characters")
//        String status)
{
}
