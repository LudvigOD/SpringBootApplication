package shared.dto;

import java.time.Instant;

import org.springframework.lang.NonNull;

public class TimeDTO {
    private Integer stationId;
    private String startNbr;
    private Long timeId;

    private Instant time;

    public TimeDTO() {
        // The automatic JSON conversion requires a default constructor
    }

    // If used to send POST the timeId wont be used, anything is valid
    // If it is used for PUT, the id will be used to find the Time Entity with the
    // same Id.
    public TimeDTO(
            Integer stationId,
            @NonNull String startNbr,
            Instant time,
            @NonNull Long timeId) {
        this.stationId = stationId;
        this.startNbr = startNbr;
        this.time = time;
        this.timeId = timeId;
    }

    public Long getTimeId() {
        return timeId;
    }

    public Integer getStationId() {
        return stationId;
    }

    public String getStartNbr() {
        return startNbr;
    }

    public void setStartNbr(String s){
        this.startNbr = s;
    }

    public Instant getTime() {
        return time;
    }

    @Override
    public String toString() {
        return String.format(
                "stationId: %s; startNbr: %s; time: %s; timeId: %s",
                stationId, startNbr, time, timeId);
    }
}
