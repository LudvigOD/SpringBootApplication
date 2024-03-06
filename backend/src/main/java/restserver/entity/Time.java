package restserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Time {

  // Note to self: These annotations tells Spring that this id
  // attribute should be an ID in the database table, and that
  // its value should be automatically generated (1, 2, 3, ...).
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String startNbr;
  private String time;

  public Time() {
    // The automatic JSON conversion requires a default constructor
  }

  public Time(String startNbr, String time) {
    this.startNbr = startNbr;
    this.time = time;
  }

  public Long getId() {
    return id;
  }

  public String getStartNbr() {
    return startNbr;
  }

  public String getTime() {
    return time;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setStartNbr(String startNbr) {
    this.startNbr = startNbr;
  }

  public void setTime(String time) {
    this.time = time;
  }

}
