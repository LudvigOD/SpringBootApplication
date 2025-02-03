package shared.dto;

import java.util.Optional;

public class UpdateParticipantDTO {
    private Optional<String> startNbr;
    private Optional<String> name;

    public UpdateParticipantDTO() {
        // The automatic JSON conversion requires a default constructor
    }

    public UpdateParticipantDTO(Optional<String> startNbr, Optional<String> name) {
        this.startNbr = startNbr;
        this.name = name;
    }

    public Optional<String> getStartNbr() {
        return startNbr;
    }

    public Optional<String> getName() {
        return name;
    }
}