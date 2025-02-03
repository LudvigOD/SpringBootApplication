package restserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int raceId;
    private String startNbr;
    private String name;

    // springboot need default constructor
    public Participant() {

    }

    public Participant(int raceId, String startNbr, String name) {
        this.raceId = raceId;
        this.startNbr = startNbr;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public int getRaceId() {
        return raceId;
    }

    public String getStartNbr() {
        return startNbr;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public void setStartNbr(String startNbr) {
        this.startNbr = startNbr;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Participant[id=%d, raceId=%d, startNbr='%s', name='%s']", id, raceId, startNbr, name);
    }

}
