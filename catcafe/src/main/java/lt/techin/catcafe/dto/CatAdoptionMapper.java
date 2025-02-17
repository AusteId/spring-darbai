package lt.techin.catcafe.dto;

import lt.techin.catcafe.model.CatAdoption;
import lt.techin.catcafe.model.User;

public class CatAdoptionMapper {

  public static CatAdoptionResponseDTO toCatAdoptionResponseDTO(CatAdoption catAdoption) {
    return new CatAdoptionResponseDTO(
            catAdoption.getId(),
            catAdoption.getCatName(),
            catAdoption.getStatus(),
            catAdoption.getApplicationDate()
    );
  }

  public static CatAdoption toCatAdoption(CatAdoptionRequestDTO requestDTO, User user) {
    CatAdoption catAdoption = new CatAdoption();
    catAdoption.setUser(user);
    catAdoption.setCatName(requestDTO.catName());
    catAdoption.setStatus("PENDING");
    catAdoption.setApplicationDate(new Timestamp(System.currentTimeMillis()));
    return catAdoption;
  }
}
