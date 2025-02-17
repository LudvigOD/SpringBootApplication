package result.model;

import java.util.List;

import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

/**
 * Provides only those times whose participant exists.
 */
public class OnlyValidTimesAdminModel extends FilteredAdminModel {
  private final boolean validTimes;

  /**
   * @param onlyValid If true, only Times whose Participant exists are shown. If
   *                  false, only Times whose Participant does not exist are
   *                  shown.
   */
  public OnlyValidTimesAdminModel(AdminModel adminModel, boolean onlyValid) {
    super(adminModel);
    this.validTimes = onlyValid;
  }

  @Override
  protected boolean filterTime(TimeDTO time, List<ParticipantDTO> participants) {
    return validTimes == participants.stream()
        .map(ParticipantDTO::getStartNbr)
        .anyMatch(n -> n.equals(time.getStartNbr()));
  }
}
