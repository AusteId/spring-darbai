package lt.techin.catcafe.controller;

import jakarta.validation.Valid;
import lt.techin.catcafe.dto.CatAdoptionMapper;
import lt.techin.catcafe.dto.CatAdoptionRequestDTO;
import lt.techin.catcafe.dto.CatAdoptionResponseDTO;
import lt.techin.catcafe.model.CatAdoption;
import lt.techin.catcafe.model.User;
import lt.techin.catcafe.service.CatAdoptionService;
import lt.techin.catcafe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cat-adoptions")
public class CatAdoptionController {

  private final CatAdoptionService catAdoptionService;
  private final UserService userService;

  @Autowired
  public CatAdoptionController(CatAdoptionService catAdoptionService, UserService userService) {
    this.catAdoptionService = catAdoptionService;
    this.userService = userService;
  }

  @PostMapping
  public ResponseEntity<CatAdoptionResponseDTO> createCatAdoption(@Valid @RequestBody CatAdoptionRequestDTO requestDTO,
                                                                  @AuthenticationPrincipal User user) {

    if (user == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    CatAdoption catAdoption = new CatAdoption();
    catAdoption.setUser(user);
    catAdoption.setCatName(requestDTO.catName());
    catAdoption.setStatus("PENDING");
    catAdoption.setApplicationDate(new Timestamp(System.currentTimeMillis()));

    CatAdoption savedCatAdoption = catAdoptionService.saveCatAdoption(catAdoption);

    CatAdoptionResponseDTO responseDTO = new CatAdoptionResponseDTO(
            savedCatAdoption.getId(),
            savedCatAdoption.getCatName(),
            savedCatAdoption.getStatus(),
            savedCatAdoption.getApplicationDate()
    );

    return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
  }

  @GetMapping("/{userId}")
  public ResponseEntity<List<CatAdoptionResponseDTO>> getCatAdoptionsByUserId(@PathVariable long userId) {
    List<CatAdoption> catAdoptions = catAdoptionService.findCatAdoptionsByUserId(userId);

    List<CatAdoptionResponseDTO> responseDTOs = catAdoptions.stream()
            .map(catAdoption -> new CatAdoptionResponseDTO(
                    catAdoption.getId(),
                    catAdoption.getCatName(),
                    catAdoption.getStatus(),
                    catAdoption.getApplicationDate()))
            .toList();

    return ResponseEntity.ok(responseDTOs);
  }

  @PutMapping("/{adoptionId}")
  public ResponseEntity<CatAdoptionResponseDTO> updateCatAdoptionStatus(
          @PathVariable long adoptionId,
          @RequestBody CatAdoptionRequestDTO catAdoptionRequestDTO) {

    CatAdoption existingAdoption = catAdoptionService.findById(adoptionId);

    if (existingAdoption == null) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    String newStatus = catAdoptionRequestDTO.getStatus();
    if (!(newStatus.equals("PENDING") || newStatus.equals("APPROVED") || newStatus.equals("REJECTED"))) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    existingAdoption.setStatus(newStatus);
    CatAdoption updatedCatAdoption = catAdoptionService.saveCatAdoption(existingAdoption);

    CatAdoptionResponseDTO responseDTO = new CatAdoptionResponseDTO(
            updatedCatAdoption.getId(),
            updatedCatAdoption.getCatName(),
            updatedCatAdoption.getStatus(),
            updatedCatAdoption.getApplicationDate()
    );

    return ResponseEntity.ok(responseDTO);
  }


}
