package restserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Station {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private int raceId;
  private String name;

  public Station() {
    // The automatic JSON conversion requires a default constructor
  }

  public Station(int raceId, String name) {
    this.raceId = raceId;
    this.name = name;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }

  public void setRaceId(int raceId) {
    this.raceId = raceId;
  }

  public int getRaceId() {
    return raceId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return String.format("Station[id=%d, raceId='%s', name='%s']", id, raceId, name);
  }

}
