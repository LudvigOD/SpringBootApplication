package result.model;

//import static org.mockito.Mockito.times;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.format.datetime.DateTimeFormatAnnotationFormatterFactory;
import org.springframework.web.reactive.function.client.WebClient;

import result.view.AdminView;
import shared.dto.TimeDTO;
import result.util.Competitor;
import java.util.function.Consumer;


public class AdminModelImpl implements AdminModel {

    //Ska attributen vara final som i Register modellen?
    private List<AdminView> views;
    private Map<String, Competitor> competitors;
    private WebClient webClient;
    private List<TimeDTO> times;


    // Ändra så att man kan ha fler tävlingar i senare skede, dessa attribut får
    // flyttas dit isf.
    //private int nbrCompetitors;
    //private int nbrStations;

    //public AdminModelImpl(WebClient webClient) {
    //    this.times = new ArrayList<>();
    //    this.webClient = webClient;
    //}

    public AdminModelImpl(WebClient webClient) {
        this.views = new ArrayList<>();
        this.competitors = new HashMap<>();
        this.webClient = webClient;
        times = new ArrayList<>() ;
    }

    @Override
    public void addListener(AdminView view) {
        this.views.add(view);
    }

    @Override
    public void removeListener(AdminView view) {
        this.views.remove(view);
    }

    public void filterNewTimes(List<TimeDTO> times) {
        if(this.times.size() != times.size()){
            updateTimeTable(times.subList(this.times.size(), times.size()));
            this.times = times; 
        }
    }

    @Override
    public void updateTimeTable(List<TimeDTO> times) {
        for(TimeDTO time : times) {
            String startNbr = time.getStartNbr();
            if (!competitors.containsKey(startNbr)){
                competitors.put(startNbr, new Competitor(startNbr));
            }
            Competitor competitor = competitors.get(startNbr);
            competitor.addTimetoCompetitor(time.getStationId(), time.getTime());
            for (AdminView view : this.views) {
                view.onTimeAdded(time, competitor);
            }
        }
    }

    @Override
    public int getNbrCompetitors() {
        return competitors.size();
    }

    

    public void test(){
        ArrayList<TimeDTO> times = new ArrayList<>();
        TimeDTO time1 = new TimeDTO(0, "7", Instant.now());
        TimeDTO time2 = new TimeDTO(0, "5", Instant.now());
        TimeDTO time3 = new TimeDTO(1, "5", Instant.now());
        TimeDTO time4 = new TimeDTO(110, "12", Instant.now());
        times.add(time1);
        times.add(time2);
        times.add(time3);
        times.add(time4);
        updateTimeTable(times);
    }

    /* 
    @Override
    public void startCompetition(int nbrCompetitors, int nbrStations) {
        this.nbrCompetitors = nbrCompetitors;
        this.nbrStations = nbrStations;
    }

    public int getNbrStations() {
        return nbrStations;
    } 
    */
    public List<TimeDTO> syncGetAllTimesFromServer(int raceID, Optional<Integer> station, Optional<Integer> startNbr) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                .path("/races/{raceID}/times")
                .queryParamIfPresent("startNbr", startNbr)
                .queryParamIfPresent("station", station)
                .build(raceID))
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<List<TimeDTO>>() {
            })
            .block(); // will wait here during network request
    }

    public void getAllTimesFromServer(Consumer<List<TimeDTO>> responseHandler, int raceID, Optional<Integer> station, Optional<Integer> startNbr){
        webClient.get()
       .uri(uriBuilder -> uriBuilder
       .path("/races/{raceID}/times")
       .queryParamIfPresent("startNbr", startNbr)
       .queryParamIfPresent("station", station)
       .build(raceID))
       .accept(MediaType.APPLICATION_JSON)
       .retrieve()
       .bodyToMono(new ParameterizedTypeReference<List<TimeDTO>>() {
       })
       .subscribe(responseHandler);
    }


//   public List<TimeDTO> getParticipantTimes(int raceID, Optional<Integer> station, Optional<Integer> startNbr) {
//     return syncGetAllTimesFromServer(raceID, startNbr);
//   }

//   public List<TimeDTO> getStationTimes(int raceID, Optional<Integer> station) {
//     return syncGetAllTimesFromServer(raceID, station);
//   }

//   public List<TimeDTO> getStationTimeForOneParticipant(int raceID, Integer station, Optional<Integer> startNbr) {
//     return syncGetAllTimesFromServer(raceID, station, startNbr);
//   }
}
