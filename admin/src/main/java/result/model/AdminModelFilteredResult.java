package result.model;

import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public class AdminModelFilteredResult extends AbsAdminModelFilter {
    private final int stationId;
    private String startNbr;

    public AdminModelFilteredResult(AdminModel delegate, int stationId, String startNbr) {
        super(delegate);
        this.stationId = stationId;
        this.startNbr = startNbr;
    }

    @Override
    protected boolean filterTime(TimeDTO time) {
        return time.getStationId() == this.stationId && time.getStartNbr().equals(this.startNbr);
    }

    @Override
    protected boolean filterParticipant(ParticipantDTO participant) {
        return participant.getStartNbr().equals(this.startNbr);
    }
}
