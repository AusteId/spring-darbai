package lt.techin.demo.dto;

import lt.techin.demo.model.Actor;

import java.util.List;

public class ActorMapper {

  public static ActorDTO toActorDTO(Actor actor) {
    return new ActorDTO(actor.getId(), actor.getFirstName(), actor.getLastName(), actor.getBirthdate());
  }

  public static Actor toActor(ActorDTO actorDTO) {
    Actor actor = new Actor();
    actor.setFirstName(actorDTO.firstName());
    actor.setLastName(actorDTO.lastName());
    actor.setBirthdate(actorDTO.birthdate());

    return actor;
  }

  public static List<ActorDTO> toActorDTOList(List<Actor> actors) {
    List<ActorDTO> result = actors.stream()
            .map(actor -> new ActorDTO(actor.getId(), actor.getFirstName(), actor.getLastName(), actor.getBirthdate()))
            .toList();

    return result;
  }
}
