package restserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import restserver.entity.Station;

public interface StationRepository extends JpaRepository<Station, Long> {
      public List<Station> findByRaceId(int raceId);
}
