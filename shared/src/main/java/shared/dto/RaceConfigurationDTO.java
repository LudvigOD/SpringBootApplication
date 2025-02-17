package shared.dto;

import java.util.List;

public class RaceConfigurationDTO {
  private String teamToken;
  private List<StationDTO> stations;
  private List<ParticipantDTO> participants;

  public RaceConfigurationDTO() {
    // The automatic JSON conversion requires a default constructor
  }

  public RaceConfigurationDTO(String teamToken, List<StationDTO> stations, List<ParticipantDTO> participants) {
    this.teamToken = teamToken;
    this.stations = stations;
    this.participants = participants;
  }

  public String getTeamToken() {
    return teamToken;
  }

  public List<StationDTO> getStations() {
    return stations;
  }

  public List<ParticipantDTO> getParticipants() {
    return participants;
  }

  @Override
  public String toString() {
    return String.format("RaceConfigurationDTO{teamToken='%s', stations=%s, participants=%s}", teamToken, stations,
        participants);
  }
}
