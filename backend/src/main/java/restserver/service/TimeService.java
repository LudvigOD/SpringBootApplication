package restserver.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import shared.dto.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restserver.entity.Time;
import restserver.repository.TimeRepository;

@Service
public class TimeService {

  @Autowired
  private TimeRepository timeRepository;

  public Time registerTime(int raceId, int stationId, String startNbr, Instant time) {
    Time timeEntity = new Time(raceId, stationId, this.cleanStartNbr(startNbr), time);
    return timeRepository.save(timeEntity);
  }

  public List<Time> getAllTimes(int raceId, Optional<Integer> stationId, Optional<String> startNbr) {
    if (stationId.isPresent() && startNbr.isPresent()) {
      return timeRepository.findByRaceIdAndStationIdAndStartNbr(raceId, stationId.get(), startNbr.get());
    }

    if (stationId.isPresent()) {
      return timeRepository.findByRaceIdAndStationId(raceId, stationId.get());
    }

    if (startNbr.isPresent()) {
      return timeRepository.findByRaceIdAndStartNbr(raceId, startNbr.get());
    }

    return timeRepository.findByRaceId(raceId);
  }

  // TODO: handle bad ID
  public void updateTimeEntity(int raceId, int stationId, String startNbr, Instant time, long id) {
    Optional<Time> tmpOp = timeRepository.findById(id);
    if (tmpOp.isPresent()) {
      Time tmp = tmpOp.get();
      tmp.setRaceId(raceId);
      tmp.setStartNbr(startNbr);
      tmp.setStationId(stationId);
      tmp.setTime(time);
      timeRepository.save(tmp);
    } else {
      System.out.println("No entity of " + id + "was found (nothing was changed)");
    }
  }

  private String cleanStartNbr(String startNbr) {
    int desiredLength = 2;

    String cleanedStartNbr = Integer.toString(Integer.parseInt(startNbr));

    int missingZeros = desiredLength - startNbr.length();

    for (int i = 0; i < missingZeros; i++) {
      cleanedStartNbr = "0" + cleanedStartNbr;
    }

    return cleanedStartNbr.toString();
  }
}
