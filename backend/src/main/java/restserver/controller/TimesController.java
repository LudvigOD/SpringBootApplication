package restserver.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import restserver.entity.Time;
import restserver.service.TimeService;
import shared.dto.TimeDTO;

@RestController
@RequestMapping("/api/races/{raceId}/times")
public class TimesController {

  @Autowired
  private TimeService timeService;

  @PostMapping()
  public void registerTime(
      @PathVariable("raceId") int raceId,
      @RequestBody TimeDTO timeDTO) {
    timeService.registerTime(raceId, timeDTO.getStationId(), timeDTO.getStartNbr(), timeDTO.getTime());
  }

  @GetMapping()
  public List<TimeDTO> fetchAllTimes(
      @PathVariable("raceId") int raceId,
      @RequestParam("stationId") Optional<Integer> stationId,
      @RequestParam("startNbr") Optional<String> startNbr) {
    List<Time> times = timeService.getAllTimes(raceId, stationId, startNbr);

    List<TimeDTO> timeDTOs = times.stream()
        .map(time -> new TimeDTO(time.getStationId(), time.getStartNbr(), time.getTime()))
        .collect(Collectors.toList());

    return timeDTOs;
  }
}
