package result.util;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class Competitor {
    
    String startNbr;
    Map<Integer, List<Instant>> stationIDAndTimes;

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
        if(stationIDAndTimes.containsKey(0)){
            return stationIDAndTimes.get(0).get(0).toString();
        }
        return "-";
    }

    public String getFinishTime() {
        if(stationIDAndTimes.containsKey(1)){
            return stationIDAndTimes.get(1).get(0).toString();
        }
        return "-";
    }

    public String getTotalTime() {
        if(stationIDAndTimes.containsKey(0) && stationIDAndTimes.containsKey(1)){
            Instant instant1 = stationIDAndTimes.get(0).get(0);
            Instant instant2 = stationIDAndTimes.get(1).get(0);
            Duration duration = Duration.between(instant1, instant2);
            return duration.toString();
        }
        return "-";
    }

}
