package result.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public class AdminModelImpl implements AdminModel {
    private List<TimeDTO> times = new ArrayList<>();
    private List<ParticipantDTO> participants = new ArrayList<>();
    private List<AdminView> views = new ArrayList<>();

    private WebClient webClient;

    public AdminModelImpl(WebClient webClient) {
        this.webClient = webClient;
        times = new ArrayList<>();
    }

    @Override
    public void addListener(AdminView view) {
        this.views.add(view);

        view.onDataUpdated(this.times, this.participants);
    }

    @Override
    public void removeListener(AdminView view) {
        this.views.remove(view);
    }

    private void notifyListeners() {
        for (AdminView view : this.views) {
            view.onDataUpdated(this.times, this.participants);
        }
    }

    @Override
    public List<TimeDTO> getAllTimes() {
        return this.times;
    }

    @Override
    public List<ParticipantDTO> getAllParticipants() {
        return this.participants;
    }

    public void fetchUpdates() {
        this.times = syncGetAllTimesFromServer(1);
        this.participants = syncGetAllParticipantsFromServer(1);

        notifyListeners();
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

}
