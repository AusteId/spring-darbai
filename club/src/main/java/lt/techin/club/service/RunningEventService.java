package lt.techin.club.service;

import lt.techin.club.model.RunningEvent;
import lt.techin.club.repository.RunningEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RunningEventService {

  private final RunningEventRepository runningEventRepository;

  @Autowired
  public RunningEventService(RunningEventRepository runningEventRepository) {
    this.runningEventRepository = runningEventRepository;
  }

  public Optional<RunningEvent> findRunningEventById(long id) {
    return runningEventRepository.findById(id);
  }

  public List<RunningEvent> findAllRunningEvents() {
    return runningEventRepository.findAll();
  }

  public RunningEvent saveRunningEvent(RunningEvent runningEvent) {
    return runningEventRepository.save(runningEvent);
  }

  public void deleteRunningEvent(long id) {
    runningEventRepository.deleteById(id);
  }
}
