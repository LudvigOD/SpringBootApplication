package register.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AbstractDocument;

import register.model.EndStation;
import register.model.RegisterModel;
import register.model.RegisterModelImpl;
import register.model.StartStation;
import register.model.StationModel;
import register.util.TimeTuple;
import shared.Utils;
import shared.dto.TimeDTO;
import shared.gui.PlaceholderTextField;

public class RegisterGUI extends JFrame implements RegisterView {

  private final RegisterModel model;
  private StationModel startStation = new StartStation();
  private StationModel endStation = new EndStation();
  private StationModel selectedStation = startStation;
  private StationModel[] stations = { startStation, endStation };
  private JComboBox<StationModel> chooseStation = new JComboBox<StationModel>(stations);
  private String[] tableHeaders = { "Startnummer", "Tid", "Station" };
  private Font defaultFont = new Font("SANS_SERIF", Font.PLAIN, 25);
  private JTextField startNumberField;
  private JButton registerButton;
  private DefaultTableModel tableModel = new DefaultTableModel(tableHeaders, 0);

  public RegisterGUI(RegisterModel model) {
    this.model = model;
    this.model.addListener(this);
    // Fungerar inte på vissa datorer
    // setExtendedState(JFrame.MAXIMIZED_BOTH);

    initGUI();
  }


  @Override
  public void timeWasRegistered() {
    //System.out.println("Time was registered: " + timeTuple);
    // FIXA GÄRNA OM NI HITTAR BÄTTRE LÖSNING PÅ HUR VI AVRUNDAR OCH PLOCKAR UT 1
    // DECIMAL
    // if(timeTuple.getStartNbr().equals("000")) {
    //   tableModel.addRow(new Object[] { "StartID?", Utils.displayTimeInCorrectFormat(timeTuple.getTime()),
    //     selectedStation });
    // } else {
    //   tableModel.addRow(new Object[] { timeTuple.getStartNbr(), Utils.displayTimeInCorrectFormat(timeTuple.getTime()),
    //     selectedStation });
    // }
    tableModel.setRowCount(0);
    Consumer<List<TimeDTO>> responseHandler = response -> {
      response.forEach(timeDTO -> {
        if(timeDTO.getStartNbr().equals("00")) {
          tableModel.addRow(new Object[] { "StartID?", Utils.displayTimeInCorrectFormat(timeDTO.getTime()),
            selectedStation });
        } else {
          tableModel.addRow(new Object[] { timeDTO.getStartNbr(), Utils.displayTimeInCorrectFormat(timeDTO.getTime()),
            selectedStation });
        }
      });
    };
    model.asyncReloadTimes(responseHandler, selectedStation.id());
  }

  private void initGUI() {
    setTitle("Tidregistrering");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(Toolkit.getDefaultToolkit().getScreenSize().width,
        Toolkit.getDefaultToolkit().getScreenSize().height);

    JPanel mainPanel = new JPanel(new BorderLayout());
    JTable registrationTable = new JTable(tableModel) {
      public boolean isCellEditable(int row, int col) {
        Object value = getValueAt(row, col);
        return value instanceof String;
      }
    };
    tableModel.addTableModelListener(e -> {
      if(e.getType() == TableModelEvent.UPDATE) {
        int row = e.getFirstRow();
        int column = e.getColumn();

        if(column == 0) {
          Object newValueObj = tableModel.getValueAt(row, column);
          String newStartNbr = (newValueObj != null) ? newValueObj.toString() : "";
          TimeDTO time = model.syncReloadTimes(Optional.of(selectedStation.id())).get(row);
          time.setStartNbr(newStartNbr);
          model.updateTime(time, 1);
        }
      }
    });
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

    startNumberField.setFont(defaultFont);

    registerButton = new JButton("Registrera tid");
    registerButton.setFont(defaultFont);
    registerButton.setBackground(Color.RED);
    registerButton.setFont(defaultFont);
    registerButton.setBackground(Color.RED);

    JPanel inputPanel = new JPanel();
    GridBagLayout gridLayout = new GridBagLayout();
    BoxLayout verticalLayout = new BoxLayout(inputPanel, BoxLayout.Y_AXIS);
    inputPanel.setLayout(gridLayout);

    addComponentListener(new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            if (getWidth() < 900) {   // när fönstret är under 900 pixlar används vertikal-layout
                inputPanel.setLayout(verticalLayout);
                inputPanel.setPreferredSize(new Dimension(200 , 200));
            } else {
                inputPanel.setLayout(gridLayout); // annars används gridlayout (bredvid varandra)
                inputPanel.setPreferredSize(new Dimension(700,60));
            }
            inputPanel.revalidate(); // berättar för komponenterna att de ska ändra form/layout
            inputPanel.repaint(); // ritar ut den nya layouten
        }
    });


    JLabel startNum = new JLabel("Startnummer:");
    startNum.setFont(defaultFont);

    chooseStation.setFont(new Font("SANS_SERIF", Font.PLAIN, 20));
    chooseStation.addActionListener(event -> {
      selectedStation = (StationModel) chooseStation.getSelectedItem();
    });
    chooseStation.setMaximumSize(new Dimension(75,25));

    startNumberField.setMaximumSize(new Dimension(175,40));

    JLabel stationLabel = new JLabel("Station:");
    stationLabel.setFont(defaultFont);

    chooseStation.setAlignmentX(Component.CENTER_ALIGNMENT);
    startNumberField.setAlignmentX(Component.CENTER_ALIGNMENT);
    startNum.setAlignmentX(Component.CENTER_ALIGNMENT);
    stationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    registerButton.setMaximumSize(new Dimension(200, 40));
    registerButton.setAlignmentX(Component.CENTER_ALIGNMENT);

    inputPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
    inputPanel.setPreferredSize(new Dimension(700,60));

    inputPanel.add(stationLabel);
    inputPanel.add(Box.createHorizontalStrut(5));
    inputPanel.add(chooseStation);
    inputPanel.add(Box.createHorizontalStrut(15));
    inputPanel.add(startNum);
    inputPanel.add(Box.createHorizontalStrut(5));
    inputPanel.add(startNumberField);
    inputPanel.add(Box.createHorizontalStrut(15));
    inputPanel.add(registerButton);

    // // Temporary test, fetch times from the server
    // // Super hacky, DO NOT DO THIS IN A REAL APPLICATION!
    // // Denna ska tas bort sen och ersättas med bara register-knappen
    // // Denna ska tas bort sen och ersättas med bara register-knappen
    // JButton fetchTimesButton = new JButton("Test: Fetch times");
    // fetchTimesButton.setFont(defaultFont);

    // fetchTimesButton.setFont(defaultFont);

    // fetchTimesButton.addActionListener((e) -> {
    //   ((RegisterModelImpl) model).asyncReloadTimes(timeList -> {
    //     System.out.println("Received " + timeList.size() + " times from server");
    //     for (TimeDTO time : timeList) {
    //       System.out.println(time);
    //     }
    //   }, Integer.valueOf(startNumberField.getText()));
    // });

    // inputPanel.add(fetchTimesButton);
    mainPanel.add(inputPanel, BorderLayout.NORTH);
    JScrollPane scrollPane = new JScrollPane(registrationTable);
    mainPanel.add(scrollPane, BorderLayout.CENTER);


    registerButton.addActionListener((e) -> {
      String startNumber = startNumberField.getText();
      if (!startNumber.isEmpty()) {
        model.registerTime(startNumber, selectedStation.id());
      } else {
        model.registerTime("0", selectedStation.id());
      }
    });

    add(mainPanel);
  }

}
