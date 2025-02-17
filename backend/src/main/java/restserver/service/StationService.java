package restserver.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restserver.entity.Station;
import restserver.repository.StationRepository;

@Service
public class StationService {
    @Autowired
    private StationRepository repo;

    public void addStation(int raceId, String name) {
        repo.save(new Station(raceId, name));
    }

    public List<Station> fetchAllStations(int raceId) {
        return repo.findByRaceId(raceId);
    }
}
