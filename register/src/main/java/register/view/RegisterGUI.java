package register.view;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import register.model.RegisterModel;
import register.util.TimeTuple;
import shared.gui.PlaceholderTextField;

public class RegisterGUI extends JFrame implements RegisterView {

  private final RegisterModel model;

  private JTextField startNumberField;
  private JButton registerButton;
  private DefaultListModel<String> registrationListModel;
  private JList<String> registrationList;

  public RegisterGUI(RegisterModel model) {
    this.model = model;
    this.model.addListener(this);

    initGUI();
  }

  @Override
  public void update(Iterable<TimeTuple> timeTuples) {
  }

  @Override
  public void timeWasRegistered(TimeTuple timeTuple) {
    System.out.println("Time was registered: " + timeTuple);
    registrationListModel.addElement(String.format("%s - %s", timeTuple.getStartNbr(), timeTuple.getTime()));

    // Maybe use a table instead of a list?
  }

  private void initGUI() {
    setTitle("Tidregistrering");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(300, 300);

    JPanel mainPanel = new JPanel(new BorderLayout());

    startNumberField = new PlaceholderTextField("Startnummer", 10);
    registerButton = new JButton("Registrera tid");
    registrationListModel = new DefaultListModel<>();
    registrationList = new JList<>(registrationListModel);

    JPanel inputPanel = new JPanel();
    inputPanel.add(new JLabel("Start Number:"));
    inputPanel.add(startNumberField);
    inputPanel.add(registerButton);

    mainPanel.add(inputPanel, BorderLayout.NORTH);
    mainPanel.add(new JScrollPane(registrationList), BorderLayout.CENTER);

    registerButton.addActionListener((e) -> {
      String startNumber = startNumberField.getText();
      if (!startNumber.isEmpty()) {
        model.registerTime(startNumber);
        startNumberField.setText("");
      }
    });

    add(mainPanel);
  }

}
