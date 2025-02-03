package result.model;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.format.datetime.DateTimeFormatAnnotationFormatterFactory;
import org.springframework.web.reactive.function.client.WebClient;

import ch.qos.logback.core.util.Duration;
import result.view.AdminView;
import shared.dto.TimeDTO;
import result.util.Competitor;

public class AdminModelImpl implements AdminModel {

    //Ska attributen vara final som i Register modellen?
    private List<AdminView> views;
    private Map<String, Competitor> competitors;

    //private WebClient webClient;

    // Ändra så att man kan ha fler tävlingar i senare skede, dessa attribut får
    // flyttas dit isf.
    //private int nbrCompetitors;
    //private int nbrStations;

    //public AdminModelImpl(WebClient webClient) {
    //    this.times = new ArrayList<>();
    //    this.webClient = webClient;
    //}

    public AdminModelImpl() {
        this.views = new ArrayList<>();
        this.competitors = new HashMap<>();
        //this.webClient = webClient;
    }

    @Override
    public void addListener(AdminView view) {
        this.views.add(view);
    }

    @Override
    public void removeListener(AdminView view) {
        this.views.remove(view);
    }

    @Override
    public void updateTimeTable(List<TimeDTO> times) {
        for(TimeDTO time : times) {
            String startNbr = time.getStartNbr();
            if (!competitors.containsKey(startNbr)){
                competitors.put(startNbr, new Competitor(startNbr));
                System.out.println(startNbr);
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

    
    @Override
    public List<TimeDTO> getAllTimes() {
        ArrayList<TimeDTO> times = new ArrayList<>();
        TimeDTO time1 = new TimeDTO(0, "1", Instant.now());
        TimeDTO time2 = new TimeDTO(0, "3", Instant.now());
        TimeDTO time3 = new TimeDTO(0, "2", Instant.now());
        TimeDTO time4 = new TimeDTO(1, "1", Instant.now());
        times.add(time1);
        times.add(time2);
        times.add(time3);
        times.add(time4);
        updateTimeTable(times);
        return times;
    }

    public void test(){
        ArrayList<TimeDTO> times = new ArrayList<>();
        TimeDTO time1 = new TimeDTO(0, "7", Instant.now());
        TimeDTO time2 = new TimeDTO(0, "5", Instant.now());
        System.out.println(time2.getTime());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
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

}
