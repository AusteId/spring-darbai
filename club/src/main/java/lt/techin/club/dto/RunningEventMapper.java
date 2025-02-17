package lt.techin.club.dto;

import lt.techin.club.model.RunningEvent;

public class RunningEventMapper {

  public static RunningEventResponseDTO toRunningEventResponseDTO(RunningEvent runningEvent) {
    return new RunningEventResponseDTO(
            runningEvent.getId(),
            runningEvent.getName(),
            runningEvent.getCalendarDate(),
            runningEvent.getLocation(),
            runningEvent.getMaxParticipants());
  }

  public static RunningEvent toRunningEvent(RunningEventRequestDTO runningEventRequestDTO) {
    RunningEvent runningEvent = new RunningEvent();
    runningEvent.setName(runningEventRequestDTO.name());
    runningEvent.setCalendarDate(runningEventRequestDTO.calendarDate());
    runningEvent.setLocation(runningEventRequestDTO.location());
    runningEvent.setMaxParticipants(runningEventRequestDTO.maxParticipants());
    return runningEvent;
  }


}
