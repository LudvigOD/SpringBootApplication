package restserver.entity;

import java.time.Instant;

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

  private int raceId;
  private int stationId;
  private String startNbr;
  private Instant time;

  public Time() {
    // The automatic JSON conversion requires a default constructor
  }

  public Time(int raceId, int stationId, String startNbr, Instant time) {
    this.raceId = raceId;
    this.stationId = stationId;
    this.startNbr = startNbr;
    this.time = time;
  }

  public Long getId() {
    return id;
  }

  public int getRaceId() {
    return raceId;
  }

  public int getStationId() {
    return stationId;
  }

  public String getStartNbr() {
    return startNbr;
  }

  public Instant getTime() {
    return time;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setRaceId(int raceId) {
    this.raceId = raceId;
  }

  public void setStationId(int stationId) {
    this.stationId = stationId;
  }

  public void setStartNbr(String startNbr) {
    this.startNbr = startNbr;
  }

  public void setTime(Instant time) {
    this.time = time;
  }

  @Override
  public String toString() {
    return String.format("Time[id=%d, raceId='%s', stationId='%s', startNbr='%s', time='%s']", id, raceId, stationId,
        startNbr, time);
  }

}
