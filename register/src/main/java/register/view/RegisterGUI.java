package register.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.time.temporal.ChronoUnit;

import register.model.RegisterModel;
import register.model.RegisterModelImpl;
import register.util.TimeTuple;
import shared.dto.TimeDTO;
import shared.gui.PlaceholderTextField;

public class RegisterGUI extends JFrame implements RegisterView {

  private final RegisterModel model;

  private JTextField startNumberField;
  private JButton registerButton;
  private DefaultListModel<String> registrationListModel;
  private JList<String> registrationList;
  private String[] columnNames = {"Startnummer", "Tid"};
  DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

  public RegisterGUI(RegisterModel model) {
    this.model = model;
    this.model.addListener(this);
    setExtendedState(JFrame.MAXIMIZED_BOTH);

    initGUI();
  }

  @Override
  public void update(Iterable<TimeTuple> timeTuples) {
  }

  @Override
  public void timeWasRegistered(TimeTuple timeTuple) {
    System.out.println("Time was registered: " + timeTuple);
    // FIXA GÄRNA OM NI HITTAR BÄTTRE LÖSNING PÅ HUR VI AVRUNDAR OCH PLOCKAR UT 1 DECIMAL
    String nanoDecimal = String.valueOf(Math.round((double) timeTuple.getTime().getNano()/100000000));
    
    tableModel.addRow(new Object[]{timeTuple.getStartNbr(), timeTuple.getTime()});

    // Maybe use a table instead of a list?
  }

  private void initGUI() {
    setTitle("Tidregistrering");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    // Behövs inte längre p.g.a fullscreen?
    setSize(800, 900);

    JPanel mainPanel = new JPanel(new BorderLayout());
    JTable registrationTable = new JTable(tableModel);
    registrationTable.setFont(new Font("SANS_SERIF", Font.PLAIN, 25));
    registrationTable.setRowHeight(34);
    registrationTable.setShowHorizontalLines(true);
    registrationTable.setShowVerticalLines(true);
    registrationTable.setGridColor(Color.BLACK);

    startNumberField = new PlaceholderTextField("Startnummer", 10);
    startNumberField.setFont(new Font("SANS_SERIF", Font.PLAIN, 25));

    // Filter för TextField så att man ej kan skriva in annat än siffror
    ((AbstractDocument) startNumberField.getDocument()).setDocumentFilter(new RegisterFilter());

    registerButton = new JButton("Registrera tid");
    registerButton.setFont(new Font("SANS_SERIF", Font.PLAIN, 25));
    registerButton.setBackground(Color.RED);

    registrationListModel = new DefaultListModel<>();
    registrationList = new JList<>(registrationListModel);

    JPanel inputPanel = new JPanel();
    JLabel startNum = new JLabel("Startnummer:");
    startNum.setFont(new Font("SANS_SERIF", Font.PLAIN, 25));

    JScrollPane a = new JScrollPane();


    inputPanel.add(startNum);
    inputPanel.add(startNumberField);
    inputPanel.add(registerButton);

    // Temporary test, fetch times from the server
    // Super hacky, DO NOT DO THIS IN A REAL APPLICATION!
    // Denna ska tas bort sen och ersättas med bara register-knappen
    JButton fetchTimesButton = new JButton("Test: Fetch times");
    fetchTimesButton.setFont(new Font("SANS_SERIF", Font.PLAIN, 25));

    fetchTimesButton.addActionListener((e) -> {
      ((RegisterModelImpl) model).sendNonBlockingGetRequest(timeList -> {
        System.out.println("Received " + timeList.size() + " times from server");
        for (TimeDTO time : timeList) {
          System.out.println(time);
        }
      }, "01");
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
