package restserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/* 
 * Design note: This class is an entity class, which means that it represents
 * a table in the database. The table is called "time" and has three columns:
 * - id (primary key, automatically generated)
 * - startNbr (string)
 * - time (string)
 * 
 * I'm thinking that the "time" table should be used to store each time
 * registration, i.e. when a driver starts or finishes. The "startNbr" column
 * is used to identify the driver, and the "time" column is used to store the
 * time. The "id" column is used to uniquely identify each registration, if
 * we e.g. want to delete a registration.
 */
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
  private String station;
  // Note to self: The "time" column is a string for now, but maybe we should
  // use a different data type? I'm thinking that we should use an actual time
  // data type, e.g. LocalTime or Timestamp. ZonedDateTime perhaps?

  public Time() {
    // The automatic JSON conversion requires a default constructor
  }

  public Time(String startNbr, String time, String station) {
    this.startNbr = startNbr;
    this.time = time;
    this.station = station;
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

  public String getStation() {
    return station;
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

  public void setStation(String station) {
    this.station = station;
  }

  @Override
  public String toString() {
    return String.format("Time[id=%d, startNbr='%s', time='%s', station='%s']", id, startNbr, time, station);
  }

}
