package restserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Time {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String startNbr;
  private String time;

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
