package result.view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import result.model.AdminModel;
import result.model.AdminModelFiltered;

public class TimesWindow {
  public static void openTimesDialog(AdminModel model, int stationId, String startNbr) {
    JFrame newWindow = new JFrame("Filtered Times");
    newWindow.setSize(600, 600);

    AdminModelFiltered filteredAdminModel = new AdminModelFiltered(model, stationId, startNbr);

    TimesTable filteredTimesTable = new TimesTable(filteredAdminModel);
    filteredAdminModel.addListener(filteredTimesTable);

    JScrollPane scrollPane = new JScrollPane(filteredTimesTable);
    newWindow.add(scrollPane);
    newWindow.setVisible(true);

    newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    newWindow.setLocationRelativeTo(null);

    newWindow.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent windowEvent) {
        filteredAdminModel.removeListener(filteredTimesTable);
      }
    });
  }
}
