package restserver.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restserver.entity.Race;
import restserver.repository.RaceRepository;

@Service
public class RaceService {
    @Autowired
    private RaceRepository repo;

    public Optional<Race> fetchRace(int raceId) {
        return repo.findById(raceId);
    }
}
