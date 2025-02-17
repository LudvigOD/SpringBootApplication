package restserver.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restserver.entity.Race;
import shared.dto.ParticipantDTO;
import shared.dto.RaceConfigurationDTO;
import shared.dto.StationDTO;

@Service
public class RaceConfigService {
  @Autowired
  private RaceService raceService;

  @Autowired
  private StationService stationService;

  @Autowired
  private ParticipantService participantService;

  public Optional<RaceConfigurationDTO> fetchRaceConfig(int raceID) {
    Optional<Race> race = raceService.fetchRace(raceID);

    return race.map(r -> {
      List<StationDTO> stations = stationService.fetchAllStations(raceID).stream()
          .map(station -> new StationDTO(station.getId(), station.getName()))
          .toList();

      List<ParticipantDTO> participants = participantService.fetchAllParticipants(raceID).stream().map(
          participant -> new ParticipantDTO(participant.getStartNbr(),
              participant.getName()))
          .toList();

      return new RaceConfigurationDTO(
          r.getTeamToken(),
          stations,
          participants);
    });
  }
}
