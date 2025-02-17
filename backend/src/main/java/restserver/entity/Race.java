package restserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Race {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String teamToken;

  public Race() {
    // The automatic JSON conversion requires a default constructor
  }

  public Race(String teamToken) {
    this.teamToken = teamToken;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setTeamToken(String teamToken) {
    this.teamToken = teamToken;
  }

  public String getTeamToken() {
    return teamToken;
  }

  @Override
  public String toString() {
    return String.format("Race[id=%d, teamToken='%s']", id, teamToken);
  }

}
