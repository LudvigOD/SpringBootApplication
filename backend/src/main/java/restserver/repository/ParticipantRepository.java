package restserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import restserver.entity.Participant;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    public List<Participant> findByRaceId(int raceId);
}
