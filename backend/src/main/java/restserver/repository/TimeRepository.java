package restserver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import restserver.entity.Time;

public interface TimeRepository extends JpaRepository<Time, Long> {
  public List<Time> findByRaceId(int raceId);

  public Optional<Time> findById(long id);

  public List<Time> findByRaceIdAndStationId(int raceId, int stationId);

  public List<Time> findByRaceIdAndStartNbr(int raceId, String startNbr);

  public List<Time> findByRaceIdAndStationIdAndStartNbr(int raceId, int stationId, String startNbr);
}
