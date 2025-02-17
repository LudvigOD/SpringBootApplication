package result.dto;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

import shared.Utils;

public class FinalResultDTO {
    private String plac;
    private String startNbr;
    private String name;
    private String totalTime;
    private String startTime;
    private String endTime;

    public FinalResultDTO() {
        // The automatic JSON conversion requires a default constructor
    }

    public FinalResultDTO(ResultDTO result, Integer startStation, Integer endStation) {
        setPlace(result.getPlace());
        this.startNbr = result.getCompetitor().getParticipant().getStartNbr();
        this.name = result.getCompetitor().getParticipant().getName();
        setTotalTime(result.getCompetitor().getTotalTime(), 
                     result.getCompetitor().getOnlyTimeForStation(startStation), 
                     result.getCompetitor().getOnlyTimeForStation(endStation));
        setStartTime(result.getCompetitor().getOnlyTimeForStation(startStation));
        setEndTime(result.getCompetitor().getOnlyTimeForStation(endStation));
    }

    private void setPlace(Optional<Integer> place) {
        if (place.isEmpty()){
            this.plac = "DNF";
        } else {
            this.plac = place.get().toString();
        }
    }

    private void setTotalTime(Optional<Duration> totalTime, Optional<Instant> startTime, Optional<Instant> endTime) {
        if (totalTime.isEmpty()){
            this.totalTime = "--:--:--";
        } else {
            Instant start = startTime.get().plusNanos(500_000_000);
            start = start.minusNanos(start.getNano());
            Instant end = endTime.get().plusNanos(500_000_000);
            end = end.minusNanos(end.getNano());
            Duration total = Duration.between(start, end);
            this.totalTime = Utils.formatDurationServer(total);
        }
    }

    private void setStartTime(Optional<Instant> startTime) {
        if (startTime.isEmpty()){
            this.startTime = "--:--:--";
        } else {
            this.startTime = Utils.formatInstantServer(startTime.get().plusNanos(500_000_000));
        }
    }

    private void setEndTime(Optional<Instant> endTime) {
        if (endTime.isEmpty()){
            this.endTime = "--:--:--";
        } else {
            this.endTime = Utils.formatInstantServer(endTime.get().plusNanos(500_000_000));
        }
    }

    public String getPlac(){
        return plac;
    }

    public String getStartNbr(){
        return startNbr;
    }

    public String getName(){
        return name;
    }

    public String getTotalTime(){
        return totalTime;
    }

    public String getStartTime(){
        return startTime;
    }

    public String getEndTime(){
        return endTime;
    }

    @Override
    public String toString() {
        return String.format("FinalResultDTO{plac='%s', startNbr ='%s', name ='%s', totalTime ='%s', startTime ='%s', endTime ='%s', }", plac, startNbr, name, totalTime, startTime, endTime);
    }
    
}