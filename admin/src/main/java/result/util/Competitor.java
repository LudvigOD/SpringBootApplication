package result.util;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shared.Utils;

import java.util.ArrayList;

public class Competitor {
    
    private String startNbr;
    private Map<Integer, List<Instant>> stationIDAndTimes;

    public Competitor(String startNbr){
        this.startNbr = startNbr;
        stationIDAndTimes = new HashMap<Integer, List<Instant>>();
    }

    public String getStartNbr() {
        return startNbr;
    }

    public void addTimetoCompetitor(Integer stationID, Instant time) {
        if (stationIDAndTimes.containsKey(stationID)){
            stationIDAndTimes.get(stationID).add(time);
        } else {
            List<Instant> times = new ArrayList<>();
            times.add(time);
            stationIDAndTimes.put(stationID, times);
        }
    }

    public String getStartTime() {
        if(stationIDAndTimes.containsKey(1)){
            var utils = new Utils();
            return utils.displayTimeInCorrectFormat(stationIDAndTimes.get(1).get(0));
        }
        return "-";
    }

    public String getFinishTime() {
        if(stationIDAndTimes.containsKey(2)){
            var utils = new Utils();
            return utils.displayTimeInCorrectFormat(stationIDAndTimes.get(2).get(0));
        }
        return "-";
    }

    public String getTotalTime() {
        if(stationIDAndTimes.containsKey(1) && stationIDAndTimes.containsKey(2)){
            Instant instant1 = stationIDAndTimes.get(1).get(0);
            Instant instant2 = stationIDAndTimes.get(2).get(0);
            Duration duration = Duration.between(instant1, instant2);
            return String.format("%02d:%02d:%02d:%02d", duration.toHours(), duration.toMinutes(), duration.toSeconds(), duration.toMillis());
        }
        return "-";
    }

}
