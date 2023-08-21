package register.util;

import java.time.LocalTime;

public class TimeTuple {
  private final String startNbr;
  private final LocalTime time;

  public TimeTuple(String startNbr, LocalTime time) {
    this.startNbr = startNbr;
    this.time = time;
  }

  public TimeTuple(String startNbr, String time) {
    this.startNbr = startNbr;
    this.time = LocalTime.parse(time);
  }

  public String getStartNbr() {
    return startNbr;
  }

  public LocalTime getTime() {
    return time;
  }

}
