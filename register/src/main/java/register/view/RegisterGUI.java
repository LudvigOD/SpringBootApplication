package register.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.time.format.DateTimeFormatter;

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
  private Font defaultFont = new Font("SANS_SERIF", Font.PLAIN, 25);
  private JTextField startNumberField;
  private JButton registerButton;
  private String[] columnNames = { "Startnummer", "Tid" };
  private DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

  public RegisterGUI(RegisterModel model) {
    this.model = model;
    this.model.addListener(this);
    // Fungerar inte på vissa datorer
    // setExtendedState(JFrame.MAXIMIZED_BOTH);

    initGUI();
  }

  // ??
  @Override
  public void update(Iterable<TimeTuple> timeTuples) {
  }

  @Override
  public void timeWasRegistered(TimeTuple timeTuple) {
    System.out.println("Time was registered: " + timeTuple);
    // FIXA GÄRNA OM NI HITTAR BÄTTRE LÖSNING PÅ HUR VI AVRUNDAR OCH PLOCKAR UT 1
    // DECIMAL
    tableModel.addRow(new Object[] { timeTuple.getStartNbr(),
        timeTuple.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss.S")) });
  }

  private void initGUI() {
    setTitle("Tidregistrering");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(800, 600);

    JPanel mainPanel = new JPanel(new BorderLayout());
    JTable registrationTable = new JTable(tableModel);
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

    String[] stations = { "Start", "Mål" };
    JComboBox<String> chooseStation = new JComboBox<String>(stations);

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
        model.registerTime(startNumber);
      }
    });

    add(mainPanel);
  }

}
