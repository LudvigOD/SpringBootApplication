package result.model;

import admin.view.RegisterView;

public interface AdminModel {

  void addListener(AdminView view);

  void removeListener(AdminView view);

  /**
   * Registers a time for a start number using the current time.
   * 
   * @param startNbr
   */
  void registerTime(String startNbr);

  /**
   * Edits the time of a start number.
   * 
   * @param startNbr
   */
  void editTime(String startNbr);

  /**
   * Deletes a time for a start number.
   * 
   * @param startNbr
   */
  void deleteTime(String startNbr);

  /**
   * Change a start number.
   * 
   * @param startNbr
   */
  void editStartNbr(String startNbr);

  /**
   * Create a participant.
   * 
   * @param startNbr
   */
  void registerParticipant();

}
