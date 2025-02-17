package result.dto;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

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
        setTotalTime(result.getCompetitor().getTotalTime());
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

    private void setTotalTime(Optional<Duration> totalTime) {
        if (totalTime.isEmpty()){
            this.totalTime = "--:--:--";
        } else {
            this.totalTime = totalTime.get().toString();
        }
    }

    private void setStartTime(Optional<Instant> startTime) {
        if (startTime.isEmpty()){
            this.startTime = "--:--:--";
        } else {
            this.startTime = startTime.get().toString();
        }
    }

    private void setEndTime(Optional<Instant> endTime) {
        if (endTime.isEmpty()){
            this.endTime = "--:--:--";
        } else {
            this.endTime = endTime.get().toString();
        }
    }

    @Override
    public String toString() {
        return String.format("FinalResultDTO{plac='%s', startNbr ='%s', name ='%s', totalTime ='%s', startTime ='%s', endTime ='%s', }", plac, startNbr, name, totalTime, startTime, endTime);
    }
    
}