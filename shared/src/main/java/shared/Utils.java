package shared;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Utils {

    public String helper() {
        return "the helper";
    }

    public static LocalTime convertInstantToLocalTime(Instant instant) {
        return instant.atZone(ZoneId.systemDefault()).toLocalTime();
    }

    public static String displayTimeInCorrectFormat(Instant instant) {
        LocalTime localTime = convertInstantToLocalTime(instant);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss:S");
        return localTime.format(dateTimeFormatter);
    }
}