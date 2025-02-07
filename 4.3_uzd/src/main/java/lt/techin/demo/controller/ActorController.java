package lt.techin.demo.controller;

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
  public ResponseEntity<?> addActor(@RequestBody Actor actor) {

    if (actor.getLastName().isEmpty() && actor.getFirstName().isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Firstname and lastname can't be empty");
    }

    Actor actorToAdd = actorService.saveActor(actor);

    return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(actorToAdd.getId())
                            .toUri())
            .body(actorToAdd);
  }
}
