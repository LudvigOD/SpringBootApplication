package restserver.controller;

import java.util.Optional;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import restserver.service.RaceConfigService;
import shared.dto.RaceConfigurationDTO;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/races/{raceId}/configuration")
public class RaceConfigController {
  private final RaceConfigService raceConfigService;

  public RaceConfigController(RaceConfigService raceConfigService) {
    this.raceConfigService = raceConfigService;
  }

  @GetMapping()
  public Optional<RaceConfigurationDTO> fetchRaceConfig(@PathVariable("raceId") int raceID) {
    return raceConfigService.fetchRaceConfig(raceID);
  }
}
