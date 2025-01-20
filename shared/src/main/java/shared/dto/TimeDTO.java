package shared.dto;

import org.springframework.lang.NonNull;

public class TimeDTO {
  private String startNbr;

  // I don't know whether to represent time as a string or as a LocalTime
  // object. We'll have to see what works best!
  // My thoughts: LocalTime already has methods for comparing times, so
  // it might be easier to use that.
  private String time;
  // private LocalTime time;

  // Update: I discovered that there is another type, ZonedDateTime, which
  // is used to represent a time in a specific time zone. I don't know if
  // we'll need that, but I'm going to leave this here for now.
  // private ZonedDateTime time;

  public TimeDTO() {
    // The automatic JSON conversion requires a default constructor
  }

  public TimeDTO(@NonNull String startNbr, String time) {
    this.startNbr = startNbr;
    this.time = time;
  }

  public String getStartNbr() {
    return startNbr;
  }

  public String getTime() {
    return time;
  }

  @Override
  public String toString() {
    return String.format("%s; %s", startNbr, time);
  }
}
