package result.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import shared.dto.ParticipantDTO;
import shared.dto.RaceConfigurationDTO;
import shared.dto.TimeDTO;

public class AdminModelImpl implements AdminModel {
    RaceConfigurationDTO raceConfiguration = new RaceConfigurationDTO("", List.of(), List.of());
    private List<TimeDTO> times = new ArrayList<>();
    private List<AdminModelObserver> observers = new ArrayList<>();

    private WebClient webClient;
    private int raceID;

    public AdminModelImpl(WebClient webClient) {
        this.webClient = webClient;
        times = new ArrayList<>();
        raceID = 1;
    }

    @Override
    public void addObserver(AdminModelObserver observer) {
        this.observers.add(observer);

        observer.onDataUpdated(this.raceConfiguration, this.times);
    }

    @Override
    public void removeObserver(AdminModelObserver observer) {
        this.observers.remove(observer);
    }

    private void notifyObservers() {
        for (AdminModelObserver observer : this.observers) {
            observer.onDataUpdated(this.raceConfiguration, this.times);
        }
    }

    public void fetchUpdates() {
        this.raceConfiguration = syncGetRaceConfigurationFromServer(raceID);
        this.times = syncGetAllTimesFromServer(raceID);

        notifyObservers();
    }

    public RaceConfigurationDTO syncGetRaceConfigurationFromServer(int raceID) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/races/{raceID}/configuration")
                        .build(raceID))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(RaceConfigurationDTO.class)
                .block();
    }

    public List<TimeDTO> syncGetAllTimesFromServer(int raceID) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/races/{raceID}/times")
                        .build(raceID))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<TimeDTO>>() {
                })
                .block();
    }

    public void updateTime(TimeDTO timeDTO) {
        // Since the timeDTO was mutated, notify observers to propagate the change to
        // all listeners.
        notifyObservers();

        webClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/races/{raceID}/times")
                        .build(raceID))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(timeDTO)
                .retrieve()
                .bodyToMono(Void.class)
                .block();

        fetchUpdates();
    }

    public void createParticipant(ParticipantDTO dto) {
        webClient.post()
                .uri("/races/{raceId}/participants", raceID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(dto)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    @Override
    public void setRaceID(int raceID) {
        this.raceID = raceID;
        fetchUpdates();
    }
}
