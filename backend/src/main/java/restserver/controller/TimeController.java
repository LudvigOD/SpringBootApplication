package restserver.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import restserver.entity.Time;
import restserver.service.TimeService;
import shared.dto.TimeDTO;

@RestController
@RequestMapping("/api/time")
public class TimeController {

  @Autowired
  private TimeService timeService;

  /*
   * Yes, it works!
   * Go to http://localhost:8080/api/time/startNbr/01 to see the result!
   */

  @GetMapping("/startNbr/{startNbr}")
  public List<TimeDTO> fetchTimeByStartNbr(@PathVariable("startNbr") String startNbr) {

    // Call the service to fetch the data
    List<Time> times = timeService.getTimesForStartNbr(startNbr);

    // Convert the data to DTOs
    List<TimeDTO> timeDTOs = times.stream()
        .map(time -> new TimeDTO(time.getStartNbr(), time.getTime(), time.getStation()))
        .collect(Collectors.toList());

    return timeDTOs;
  }

  @PostMapping("/register")
  public void registerTime(@RequestBody TimeDTO timeDTO) {
    timeService.registerTime(timeDTO.getStartNbr(), timeDTO.getTime(), timeDTO.getStation());
  }

  @GetMapping("/regtimes")
  public List<TimeDTO> fetchAllTimes() {
    List<Time> times = timeService.fetchAllTimes();

    // Convert the data to DTOs
    List<TimeDTO> timeDTOs = times.stream()
        .map(time -> new TimeDTO(time.getStartNbr(), time.getTime()))
        .collect(Collectors.toList());

    return timeDTOs;
  }

  @GetMapping("/regtimes/{station}")
  public List<TimeDTO> fetchAllTimes(@PathVariable("station") String station) {
    List<Time> times = timeService.fetchAllStations(station);

    // Convert the data to DTOs
    List<TimeDTO> timeDTOs = times.stream()
        .map(time -> new TimeDTO(time.getStartNbr(), time.getTime()))
        .collect(Collectors.toList());

    return timeDTOs;
  }

}
