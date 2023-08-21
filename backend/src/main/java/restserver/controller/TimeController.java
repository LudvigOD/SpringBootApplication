package restserver.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import restserver.dto.TimeDTO;
import restserver.entity.Time;
import restserver.service.TimeService;

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
  public ResponseEntity<List<TimeDTO>> fetchTimeByStartNbr(
      @PathVariable("startNbr") String startNbr) {

    List<Time> times = timeService.getTimesForStartNbr(startNbr);
    if (times == null) {
      // There are several response types, I don't know which one is the most
      // appropriate
      // ResponseEntity.badRequest()
      // ResponseEntity.noContent()
      return ResponseEntity.notFound().build();
    }
    List<TimeDTO> timeDTOs = times.stream()
        .map(TimeDTO::new)
        .collect(Collectors.toList());

    return ResponseEntity.ok(timeDTOs);
  }

}
