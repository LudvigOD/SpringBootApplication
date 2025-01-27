package restserver.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restserver.entity.Time;
import restserver.repository.TimeRepository;

import org.springframework.data.domain.Sort;

@Service
public class TimeService {

  @Autowired
  private TimeRepository timeRepository;

  public Time registerTime(int raceId, int stationId, String startNbr, Instant time) {
    Time timeEntity = new Time(raceId, stationId, startNbr, time);
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
}
