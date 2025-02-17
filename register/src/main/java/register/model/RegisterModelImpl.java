package register.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import register.view.RegisterView;
import shared.dto.TimeDTO;

public class RegisterModelImpl implements RegisterModel {

  private final List<RegisterView> views;

  private final WebClient webClient;

  private int raceID;

  public RegisterModelImpl(WebClient webClient) {
    this.views = new ArrayList<>();

    this.webClient = webClient;

    this.raceID = 1; // denna måste ändras till att bli dynamisk
  }

  @Override
  public void addListener(RegisterView view) {
    this.views.add(view);
  }

  @Override
  public void removeListener(RegisterView view) {
    this.views.remove(view);
  }

  @Override
  public void registerTime(String startNbr, Integer stationId) {

    sendPostRequest(new TimeDTO(stationId, startNbr, Instant.now(), Long.valueOf(1)), raceID);
    for (RegisterView view : views) {
      view.timeWasRegistered();
    }

  }

  public void asyncReloadTimes(
      Consumer<List<TimeDTO>> responseHandler, int stationId) {
    webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/races/{raceId}/times")
            .queryParam("stationId", stationId)
            .build(raceID))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<TimeDTO>>() {
        })
        .subscribe(responseHandler);
  }

  public List<TimeDTO> syncReloadTimes(Optional<Integer> stationId) {
    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/races/{raceId}/times")
            .queryParam("stationId", stationId)
            .build(raceID))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<TimeDTO>>() {
        })
        .block();
  }

  @Override
  public void updateTime(TimeDTO timeDTO, int raceID) {
    sendPutRequest(timeDTO, raceID);
  }

  private void sendPostRequest(TimeDTO dto, int raceId) {
    webClient.post()
        .uri("/races/{raceId}/times", raceId)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(dto)
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  private void sendPutRequest(TimeDTO dto, int raceId) {
    webClient.put()
        .uri("/races/{raceId}/times", raceId)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(dto)
        .retrieve()
        .toBodilessEntity()
        .subscribe(response -> {
          System.out.println("PUT Response Status: " + response.getStatusCode());
        });
  }

  @Override
  public void setRaceID(int raceId) {
    this.raceID = raceId;
  }

}
