package restserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restserver.entity.Time;
import restserver.repository.TimeRepository;

import org.springframework.data.domain.Sort;

@Service
public class TimeService {

  @Autowired
  private TimeRepository timeRepository;

  public List<Time> getTimesForStartNbr(String startNbr) {
    return timeRepository.findByStartNbr(startNbr);
  }

  public Time registerTime(String startNbr, String time, String station) {
    Time timeEntity = new Time(startNbr, time, station);
    return timeRepository.save(timeEntity);
  }

  public List<Time> fetchAllTimes() {
    return timeRepository.findAll();
  }

  public List<Time> fetchAllStations(String station) {
    return timeRepository.findByStation(station);
  }

}
