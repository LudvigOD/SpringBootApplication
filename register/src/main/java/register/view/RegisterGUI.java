package register.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

import java.awt.Font;
import java.time.format.DateTimeFormatter;
import register.model.RegisterModel;
import register.model.RegisterModelImpl;
import register.util.TimeTuple;
import shared.dto.TimeDTO;
import shared.gui.PlaceholderTextField;

public class RegisterGUI extends JFrame implements RegisterView {

  private final RegisterModel model;
  private int selectedStation = 1;
  private Integer[] stations = {1, 2 , 3 , 4};
  private JComboBox<Integer> chooseStation = new JComboBox<Integer>(stations);
  private String[] tableHeaders = {"Start", "Mål", "Station"};
  private Font defaultFont = new Font("SANS_SERIF", Font.PLAIN, 25);
  private JTextField startNumberField;
  private JButton registerButton;
  private DefaultTableModel tableModel = new DefaultTableModel(tableHeaders, 0);

  public RegisterGUI(RegisterModel model) {
    this.model = model;
    this.model.addListener(this);

    initGUI();
  }

  // ??
  @Override
  public void update(Iterable<TimeTuple> timeTuples) {
  }

  @Override
  public void timeWasRegistered(TimeTuple timeTuple) {
    System.out.println("Time was registered: " + timeTuple);
    // FIXA GÄRNA OM NI HITTAR BÄTTRE LÖSNING PÅ HUR VI AVRUNDAR OCH PLOCKAR UT 1 DECIMAL
    var utils = new shared.Utils();
    tableModel.addRow(new Object[]{timeTuple.getStartNbr(), utils.displayTimeInCorrectFormat(timeTuple.getTime()), selectedStation});
  }

  private void initGUI() {
    setTitle("Tidregistrering");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(Integer.MAX_VALUE, Integer.MAX_VALUE);

    JPanel mainPanel = new JPanel(new BorderLayout());
    JTable registrationTable = new JTable(tableModel) {
      public boolean isCellEditable(int row, int col) {
        return false;
      }
    };
    // Eventuellt skapa klass för registrationTable?
    registrationTable.setFont(defaultFont);
    registrationTable.setRowHeight(34);
    registrationTable.setShowHorizontalLines(true);
    registrationTable.setShowVerticalLines(true);
    registrationTable.setGridColor(Color.BLACK);
    registrationTable.getTableHeader().setFont(defaultFont);

    startNumberField = new PlaceholderTextField("Startnummer", 10);
    startNumberField.setFont(defaultFont);

    // Filter för TextField så att man ej kan skriva in annat än siffror
    ((AbstractDocument) startNumberField.getDocument()).setDocumentFilter(new RegisterFilter());

    registerButton = new JButton("Registrera tid");
    registerButton.setFont(defaultFont);
    registerButton.setBackground(Color.RED);

    JPanel inputPanel = new JPanel();

    JLabel startNum = new JLabel("Startnummer:");
    startNum.setFont(defaultFont);

    
    JComboBox<Integer> chooseStation = new JComboBox<Integer>(stations);
    chooseStation.setFont(new Font("SANS_SERIF", Font.PLAIN, 20));
    chooseStation.addActionListener(event -> {
      selectedStation = (int) chooseStation.getSelectedItem();
    });

    JLabel stationLabel = new JLabel("Station:");
    stationLabel.setFont(defaultFont);

    inputPanel.add(stationLabel);
    inputPanel.add(chooseStation);
    inputPanel.add(startNum);
    inputPanel.add(startNumberField);
    inputPanel.add(registerButton);
    

    // Temporary test, fetch times from the server
    // Super hacky, DO NOT DO THIS IN A REAL APPLICATION!
    // Denna ska tas bort sen och ersättas med bara register-knappen
    JButton fetchTimesButton = new JButton("Test: Fetch times");
    fetchTimesButton.setFont(defaultFont);

    fetchTimesButton.addActionListener((e) -> {
      ((RegisterModelImpl) model).asyncReloadTimes(timeList -> {
        System.out.println("Received " + timeList.size() + " times from server");
        for (TimeDTO time : timeList) {
          System.out.println(time);
        }
      }, startNumberField.getText());
    });

    inputPanel.add(fetchTimesButton);
    mainPanel.add(inputPanel, BorderLayout.NORTH);
    JScrollPane scrollPane = new JScrollPane(registrationTable);
    mainPanel.add(scrollPane, BorderLayout.CENTER);

    registerButton.addActionListener((e) -> {
      String startNumber = startNumberField.getText();
      if (!startNumber.isEmpty()) {
        model.registerTime(startNumber, 1);
      }
    });

    add(mainPanel);
  }

}
