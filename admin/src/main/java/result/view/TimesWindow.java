package result.view;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import result.model.AdminModel;

public class TimesWindow {
  public static void openTimesDialog(AdminModel model) {
    JFrame newWindow = new JFrame("Filtered Times");
    newWindow.setSize(600, 600);

    TimesTableModel timesTableModel = new TimesTableModel(model);
    model.addObserver(timesTableModel);

    TimesTable filteredTimesTable = new TimesTable(timesTableModel);

    JScrollPane scrollPane = new JScrollPane(filteredTimesTable);
    newWindow.add(scrollPane);
    newWindow.setVisible(true);

    newWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    newWindow.setLocationRelativeTo(null);

    newWindow.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent windowEvent) {
        model.removeObserver(timesTableModel);
      }
    });
  }
}
