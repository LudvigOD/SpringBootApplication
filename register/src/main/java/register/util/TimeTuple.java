package register.util;

import java.time.Instant;

public class TimeTuple {
  private final String startNbr;
  private final Instant time;

  public TimeTuple(String startNbr, Instant time) {
    this.startNbr = startNbr;
    this.time = time;
  }

  public String getStartNbr() {
    return startNbr;
  }

  public Instant getTime() {
    return time;
  }

}
