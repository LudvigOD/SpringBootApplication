package restserver.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<List<TimeDTO>> fetchTimeByStartNbr(
      @PathVariable("startNbr") String startNbr) {

    List<Time> times = timeService.getTimesForStartNbr(startNbr);
    if (times == null) {
      // There are several response types, I don't know which one is the most
      // appropriate.
      // Edit: It seems even if an invalid start number is entered, you still
      // get a non-null result, i.e. an empty list. So I guess it is null only
      // if there is an error on the server side. In that case, I think it is
      // appropriate to return a server error?
      // ResponseEntity.notFound()
      // ResponseEntity.badRequest()
      // ResponseEntity.noContent()
      return ResponseEntity.internalServerError().build();
    }
    List<TimeDTO> timeDTOs = times.stream()
        .map(time -> new TimeDTO(time.getStartNbr(), time.getTime()))
        .collect(Collectors.toList());

    return ResponseEntity.ok(timeDTOs);
  }

  @PostMapping("/register")
  public ResponseEntity<Void> registerTime(@RequestBody TimeDTO timeDTO) {
    timeService.registerTime(timeDTO.getStartNbr(), timeDTO.getTime());
    return ResponseEntity.ok().build();
  }

}
