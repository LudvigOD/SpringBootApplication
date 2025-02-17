package shared;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class Utils {
    private static String TIME_FORMAT = "HH:mm:ss:S";

    public static String formatInstant(Instant instant) {
        return instant.atZone(ZoneId.systemDefault()).toLocalTime().format(DateTimeFormatter.ofPattern(TIME_FORMAT));
    }

    public static String formatDuration(Duration duration) {
        if (duration.isNegative()) {
            return "-" + formatDuration(duration.negated());
        }

        // Format to HH:mm:ss.S
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;
        long millis = (duration.toMillis() % 1000) / 100;

        return String.format("%02d:%02d:%02d.%d", hours, minutes, seconds, millis);
    }

    /**
     * Returns the only element of the list as an {@link Optional}.
     */
    public static <T> Optional<T> getOnlyElement(List<T> list) {
        return list.size() == 1
            ? Optional.of(list.get(0))
            : Optional.empty();
    }
}
