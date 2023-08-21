package register.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import register.util.TimeTuple;
import register.view.RegisterView;
import shared.dto.TimeDTO;

public class RegisterModelImpl implements RegisterModel {
  private final List<TimeTuple> timeTuples;

  private final List<RegisterView> views;

  private final WebClient webClient;

  private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

  public RegisterModelImpl() {
    this.timeTuples = new ArrayList<>();
    this.views = new ArrayList<>();

    // The url should be defined as a constant somewhere
    webClient = WebClient.builder()
        .baseUrl("http://localhost:8080/api")
        .build();
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
  public void registerTime(String startNbr) {
    TimeTuple timeTuple = new TimeTuple(startNbr, LocalTime.now());
    this.timeTuples.add(timeTuple);
    for (RegisterView view : this.views) {
      view.timeWasRegistered(timeTuple);
    }

    // Test sending a GET request to the server
    sendNonBlockingGetRequest(times -> {
      System.out.println("Received " + times.size() + " time times from server");
      for (TimeDTO time : times) {
        System.out.println(time);
      }
    });

    // Test sending a POST request to the server
    sendPostRequest(new TimeDTO(timeTuple.getStartNbr(),
        timeTuple.getTime().format(formatter)));
  }

  public List<TimeDTO> sendBlockingGetRequest() {

    return webClient.get()
        .uri("/time/startNbr/01")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToFlux(TimeDTO.class)
        .collectList()
        .block();
  }

  public void sendNonBlockingGetRequest(
      Consumer<List<TimeDTO>> responseHandler) {

    webClient.get()
        .uri("/time/startNbr/01")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToFlux(TimeDTO.class)
        .collectList()
        .subscribe(responseHandler);
  }

  public void sendPostRequest(TimeDTO dto) {

    webClient.post()
        .uri("/time/register")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(dto)
        .retrieve()
        .toBodilessEntity()
        .subscribe(response -> {
          System.out.println("POST Response Status: " + response.getStatusCode());
        });
  }

}
