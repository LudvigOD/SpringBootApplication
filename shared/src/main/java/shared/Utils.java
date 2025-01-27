package shared;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Utils {

    public String helper() {
        return "the helper";
    }

    public LocalTime convertInstantToLocalTime(Instant instant) {
        return instant.atZone(ZoneId.systemDefault()).toLocalTime();
    }

    public String displayTimeInCorrectFormat(Instant instant) {
        LocalTime localTime = convertInstantToLocalTime(instant);
        // int nanoTime = localTime.getNano();
        // int milliseconds = nanoTime / 1_000_000;
        // int firstDecimal = milliseconds / 10;
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss:S");
        // String formattedTime = localTime.format(dateTimeFormatter);
        // return formattedTime + ":" + firstDecimal;

        return localTime.format(dateTimeFormatter);
    }
}