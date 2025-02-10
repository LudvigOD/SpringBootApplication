package register.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import register.util.TimeTuple;
import register.view.RegisterView;
import shared.dto.TimeDTO;

public class RegisterModelImpl implements RegisterModel {
  private final List<TimeTuple> timeTuples;

  private final List<RegisterView> views;

  private final WebClient webClient;

  private int raceID;

  public RegisterModelImpl(WebClient webClient) {
    this.timeTuples = new ArrayList<>();
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
    TimeTuple timeTuple = new TimeTuple(startNbr, Instant.now());
    this.timeTuples.add(timeTuple);
    // Send a POST request to the server with the time
    sendPostRequest(new TimeDTO(stationId, timeTuple.getStartNbr(), timeTuple.getTime(), Long.valueOf(1)), raceID);

    // Test sending a GET request to the server. This is purely for testing and
    // should be removed later.
    // sendNonBlockingGetRequest(times -> {
    // System.out.println("Received " + times.size() + " time times from server");
    // for (TimeDTO time : times) {
    // System.out.println(time);
    //
    // });
  }

  public void asyncReloadTimes(
      Consumer<List<TimeDTO>> responseHandler, int stationId) {
    // Note to self: subscribe means that we make an asynchronous GET request to the
    // server. Thus, this method returns immediately (void), and the response will
    // be handled by the given consumer in the future, by some other thread. So, we
    // can call this method with a lambda expression that handles the response.
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

    // After some experimentation:
    // We can either use bodyToMono() to collect the response as a single element
    // (in this case a list of TimeDTO), or we can use a bodyToFlux() to handle each
    // element as it arrives, like a stream. We must also change the consumer
    // accordingly, i.e. Consumer<List<TimeDTO>> or Consumer<TimeDTO>.
  }

  public List<TimeDTO> syncReloadTimes(Optional<Integer> stationId) {
    // Note to self: block means that we make a synchronous GET request to the
    // server. That means that the program will be blocked and wait here until
    // the response is received. This is not recommended in a real applications,
    // but might be enough for us? I think the effect is that the program will
    // freeze briefly until the response is received, and then continue.

    return webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/races/{raceId}/times")
            .queryParam("stationId", stationId)
            .build(raceID))
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<TimeDTO>>() {
        })
        .block(); // will wait here during network request
  }

  // denna verkar fungera
  public void sendPostRequest(TimeDTO dto, int raceId) {
    webClient.post()
        .uri("/races/{raceId}/times", raceId)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(dto)
        .retrieve()
        .toBodilessEntity()
        .block();
  }

  @Override
  public void editRegisteredTime(String startNbr, TimeTuple timeTuple) {
    //Denna behöver implementeras för att skicka en PUT request.
  }

  public void sendPutRequest(TimeDTO dto, int raceId) {
    webClient.put()
        .uri("/races/{raceId}/times", raceId)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(dto)
        .retrieve()
        .toBodilessEntity()
        .subscribe(response -> {
          // Note to self: This makes an asynchronous POST request to the server meaning
          // that this method returns immediately, and the response will be handled by
          // this lambda expression in the future, by some other thread.
          System.out.println("POST Response Status: " + response.getStatusCode());
        });
  }

}
