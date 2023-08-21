package restserver.dto;

import restserver.entity.Time;

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

  public TimeDTO(Time time) {
    // Lots of things called time now. Maybe we can rename some of them to
    // make it less confusing?
    this.startNbr = time.getTime();
    this.time = time.getTime();
  }

  public String getStartNbr() {
    return startNbr;
  }

  public String getTime() {
    return time;
  }
}
