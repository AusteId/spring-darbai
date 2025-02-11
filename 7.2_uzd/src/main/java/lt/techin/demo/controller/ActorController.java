package lt.techin.demo.controller;

import jakarta.validation.Valid;
import lt.techin.demo.dto.ActorDTO;
import lt.techin.demo.model.Actor;
import lt.techin.demo.service.ActorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api")
public class ActorController {

  private final ActorService actorService;

  public ActorController(ActorService actorService) {
    this.actorService = actorService;
  }

  @PostMapping("/actors")
  public ResponseEntity<?> addActor(@Valid @RequestBody ActorDTO actorDTO) {

    if (actorDTO.lastName().isEmpty() && actorDTO.firstName().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Firstname and lastname can't be empty");
    }

    ActorDTO actorDTOToAdd = actorService.saveActor(actorDTO);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(actorDTOToAdd.id())
                            .toUri())
            .body(actorDTOToAdd);
  }
}
