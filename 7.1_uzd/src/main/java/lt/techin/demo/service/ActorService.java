package lt.techin.demo.service;

import lt.techin.demo.dto.ActorDTO;
import lt.techin.demo.dto.ActorMapper;
import lt.techin.demo.model.Actor;
import lt.techin.demo.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActorService {

  private final ActorRepository actorRepository;

  @Autowired
  public ActorService(ActorRepository actorRepository) {
    this.actorRepository = actorRepository;
  }

  public ActorDTO saveActor(ActorDTO actorDTO) {
    Actor actor = ActorMapper.toActor(actorDTO);
    Actor saveActorToDB = actorRepository.save(actor);
    return ActorMapper.toActorDTO(saveActorToDB);
  }
}
