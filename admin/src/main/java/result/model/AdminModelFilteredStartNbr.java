package result.model;

import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public class AdminModelFilteredStartNbr extends AbsAdminModelFilter {

    private Boolean onlyValid;

    public AdminModelFilteredStartNbr(AdminModel delegate, boolean onlyValid) {
        super(delegate);
        this.onlyValid = onlyValid;
    }

    @Override
protected boolean filterTime(TimeDTO time) {
    boolean valid = getAllParticipants().stream()
                      .map(ParticipantDTO::getStartNbr)
                      .anyMatch(n -> n.equals(time.getStartNbr()));
    return onlyValid ? valid : !valid;
}


    @Override
    protected boolean filterParticipant(ParticipantDTO participant) {
        return true;
    }

}
