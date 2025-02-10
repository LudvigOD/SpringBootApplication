package result.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public class AdminModel  {
    private List<TimeDTO> times = new ArrayList<>();
    private List<ParticipantDTO> participants = new ArrayList<>();
    private List<AdminModelObserver> observers = new ArrayList<>();

    private WebClient webClient;

    public AdminModel(WebClient webClient) {
        this.webClient = webClient;
        times = new ArrayList<>();
    }

    public void addListener(AdminModelObserver observer) {
        this.observers.add(observer);

        observer.onDataUpdated(this.times, this.participants);
    }

    public void removeListener(AdminModelObserver observer) {
        this.observers.remove(observer);
    }

    private void notifyObservers() {
        for (AdminModelObserver observer : this.observers) {
            observer.onDataUpdated(this.times, this.participants);
        }
    }

    public List<TimeDTO> getAllTimes() {
        return this.times;
    }

    public List<ParticipantDTO> getAllParticipants() {
        return this.participants;
    }

    public void fetchUpdates() {
        this.times = syncGetAllTimesFromServer(1);
        this.participants = syncGetAllParticipantsFromServer(1);

        notifyObservers();
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

    public List<ParticipantDTO> syncGetAllParticipantsFromServer(int raceID) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/races/{raceID}/participants")
                        .build(raceID))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ParticipantDTO>>() {
                })
                .block();

    }

    public void updateTime(TimeDTO timeDTO, int raceID) {
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

    public TimeDTO getTimeDTOForRow(int row) {
        return (row >= 0 && row < times.size()) ? times.get(row) : null;
    }
}
