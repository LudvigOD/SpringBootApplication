package restserver.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import restserver.entity.Participant;
import restserver.repository.ParticipantRepository;

public class ParticipantService {

    @Autowired
    private ParticipantRepository repo;

    public void addParticipant(int raceId, String startNbr, String name) {
        repo.save(new Participant(raceId, startNbr, name));

    }

    // if the edited participants id is present in database => replace the old with
    // updated.
    public void editParticipant(long id, Optional<String> startNbr, Optional<String> name) {
        Participant participant = repo.findById(id).get();

        startNbr.ifPresent(participant::setStartNbr);
        name.ifPresent(participant::setName);

        repo.save(participant);
    }

    public void deleteParticipant(long id) {
        repo.deleteById(id);
    }

    public List<Participant> fetchAllParticipants(int raceId) {
        return repo.findByRaceId(raceId);
    }

}
