package shared.dto;

public class ParticipantDTO {
    private String startNbr;
    private String name;

    public ParticipantDTO() {
        // The automatic JSON conversion requires a default constructor
    }

    public ParticipantDTO(String startNbr, String name) {
        this.startNbr = startNbr;
        this.name = name;
    }

    public String getStartNbr() {
        return startNbr;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("ParticipantDTO{startNbr='%s', name='%s'}", startNbr, name);
    }
}
