package result.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;

import result.model.AdminModel;
import result.model.OnlyValidTimesAdminModel;
import shared.dto.ParticipantDTO;
import shared.gui.RegisterFilter;

public class AdminGUI extends JFrame {
    public AdminGUI(AdminModel adminModel) {
        setTitle("Adminverktyg");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 1133);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel tablesPanel = new JPanel(new GridBagLayout());
        tablesPanel.setBackground(new Color(165, 165, 165));

        /* Times Tables */
        AdminModel validTimesModel = new OnlyValidTimesAdminModel(adminModel, true);
        AdminModel invalidTimesModel = new OnlyValidTimesAdminModel(adminModel, false);
        TimesTableModel validTimesTableModel = new TimesTableModel(validTimesModel);
        TimesTableModel invalidTimesTableModel = new TimesTableModel(invalidTimesModel);
        validTimesModel.addObserver(validTimesTableModel);
        invalidTimesModel.addObserver(invalidTimesTableModel);

        TimesTable validTimesTable = new TimesTable(validTimesTableModel);
        TimesTable invalidTimesTable = new TimesTable(invalidTimesTableModel);

        /* Competitors Table */
        CompetitorsTableModel competitorsTableModel = new CompetitorsTableModel();
        adminModel.addObserver(competitorsTableModel);

        CompetitorsTable competitorsTable = new CompetitorsTable(adminModel, competitorsTableModel);

        /* Results Table */
        ResultsTableModel resultsTableModel = new ResultsTableModel();
        adminModel.addObserver(resultsTableModel);

        ResultsTable resultsTable = new ResultsTable(resultsTableModel);

        JScrollPane leftTopScrollPane = new JScrollPane(validTimesTable);
        JScrollPane leftBottomScrollPane = new JScrollPane(invalidTimesTable);
        JScrollPane rightScrollPane = new JScrollPane(competitorsTable);

        JButton selectCompetitorsTableButton = new JButton("Deltagare");
        formatButton(competitorsTable, rightScrollPane, selectCompetitorsTableButton);

        JButton selectResultsTableButton = new JButton("Resultat");
        formatButton(resultsTable, rightScrollPane, selectResultsTableButton);

        JButton setRaceIDButton = new JButton("RaceID");
        JTextField raceIDField = new JTextField();
        ((AbstractDocument) raceIDField.getDocument()).setDocumentFilter(new RegisterFilter());
        raceIDField.setFont(new Font("Arial", Font.PLAIN, 20));
        raceIDField.setPreferredSize(new Dimension(50, 50));
        raceIDField.setMaximumSize(new Dimension(50, 50)); // Förhindrar att den blir större
        raceIDField.setMinimumSize(new Dimension(50, 50));
        raceIDField.setHorizontalAlignment(JTextField.CENTER); // Centrerar texten inuti fältet

        raceIDField.setAlignmentX(CENTER_ALIGNMENT);
        formatRaceIDButton(raceIDField, setRaceIDButton, adminModel);

        JButton selectFileButton = new JButton("Ladda fil...");
        selectFileButton.setFont(new Font("Arial", Font.PLAIN, 20));
        selectFileButton.setBackground(new Color(112, 173, 71));
        selectFileButton.setForeground(Color.WHITE);
        selectFileButton.setPreferredSize(new Dimension(200, 50));

        selectFileButton.addActionListener((f) -> {
            parseFile(adminModel);
        });

        leftTopScrollPane.getViewport().setBackground(new Color(129, 178, 223));
        leftBottomScrollPane.getViewport().setBackground(new Color(129, 178, 223));
        rightScrollPane.getViewport().setBackground(new Color(156, 202, 124));

        leftTopScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(65, 65, 65), 2),
                BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(129, 178, 223))));
        leftBottomScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(65, 65, 65), 2),
                BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(129, 178, 223))));
        rightScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(65, 65, 65), 2),
                BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(156, 202, 124))));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int totalHeight = (int) (screenSize.height * 0.9);
        leftTopScrollPane.setPreferredSize(new Dimension((int) (screenSize.width * 0.2), totalHeight / 2));
        leftBottomScrollPane.setPreferredSize(new Dimension((int) (screenSize.width * 0.2), totalHeight / 2));
        rightScrollPane.setPreferredSize(new Dimension((int) (screenSize.width * 0.6), totalHeight));

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(165, 165, 165));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(165, 165, 165));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // få det att funka op mac?
        selectCompetitorsTableButton.setOpaque(true);
        selectCompetitorsTableButton.setBorderPainted(false);
        selectResultsTableButton.setOpaque(true);
        selectResultsTableButton.setBorderPainted(false);
        setRaceIDButton.setOpaque(true);
        setRaceIDButton.setBorderPainted(false);
        selectFileButton.setOpaque(true);
        selectFileButton.setBorderPainted(false);
        buttonPanel.add(selectCompetitorsTableButton);
        buttonPanel.add(selectResultsTableButton);
        buttonPanel.add(selectFileButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(raceIDField);
        buttonPanel.add(setRaceIDButton);

        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        inputPanel.add(buttonPanel);

        GridBagLayout gridLayout = new GridBagLayout();
        inputPanel.setLayout(gridLayout);

        GridBagLayout gridLayoutInput = new GridBagLayout();
        BoxLayout verticalLayoutInput = new BoxLayout(buttonPanel, BoxLayout.Y_AXIS);
        inputPanel.setLayout(gridLayout);

        BoxLayout verticalLayoutTable = new BoxLayout(tablesPanel, BoxLayout.Y_AXIS);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (getWidth() < 950) { // när fönstret är under 900 pixlar används vertikal-layout
                    buttonPanel.setLayout(verticalLayoutInput);
                } else {
                    buttonPanel.setLayout(gridLayoutInput); // annars används gridlayout (bredvid varandra)
                }
                buttonPanel.revalidate(); // berättar för komponenterna att de ska ändra form/layout
                buttonPanel.repaint(); // ritar ut den nya layouten
            }
        });

        tablesPanel.setLayout(gridLayout);

        GridBagConstraints gbc = new GridBagConstraints();
        formatTimeTable(tablesPanel, leftTopScrollPane, leftBottomScrollPane, gbc);
        formatResultTable(tablesPanel, rightScrollPane, gbc);

        // Lägg till en lyssnare som byter layout baserat på fönsterbredd
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                tablesPanel.removeAll(); // Ta bort allt innehåll innan byte

                if (getWidth() < 950) { // Om skärmen är mindre än 900 pixlar hamnar tabellerna ovanför varandra
                    tablesPanel.setLayout(verticalLayoutTable);
                    tablesPanel.add(leftTopScrollPane);
                    tablesPanel.add(leftBottomScrollPane);
                    tablesPanel.add(rightScrollPane);
                } else { // Om det är fullskärm ritas tabellerna ut bredvid varandra som innan
                    tablesPanel.setLayout(gridLayout);
                    formatTimeTable(tablesPanel, leftTopScrollPane, leftBottomScrollPane, gbc);
                    formatResultTable(tablesPanel, rightScrollPane, gbc);
                }

                tablesPanel.revalidate(); // Uppdatera layout
                tablesPanel.repaint(); // Rita ut fönstret på nytt
            }
        });

        selectResultsTableButton.setMaximumSize(new Dimension(200, 50));
        selectCompetitorsTableButton.setMaximumSize(new Dimension(200, 50));
        selectFileButton.setMaximumSize(new Dimension(200, 50));
        setRaceIDButton.setMaximumSize(new Dimension(200, 50));

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(tablesPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void parseFile(AdminModel model) {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
        fileChooser.setFileFilter(filter);
        int returnValue = fileChooser.showOpenDialog(null);
        try {
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                Scanner s = new Scanner(selectedFile);

                while (s.hasNextLine()) {
                    String[] l = s.nextLine().split(",");
                    model.createParticipant(new ParticipantDTO(l[0], l[1]));
                }

                s.close();
            } else {
                System.out.println("Ingen fil valdes.");
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private void formatButton(JTable table, JScrollPane rightScrollPane, JButton selectCompetitorsTableButton) {
        selectCompetitorsTableButton.setFont(new Font("Arial", Font.PLAIN, 20));
        selectCompetitorsTableButton.setBackground(new Color(112, 173, 71));
        selectCompetitorsTableButton.setForeground(Color.WHITE);
        selectCompetitorsTableButton.setPreferredSize(new Dimension(200, 50));
        selectCompetitorsTableButton.addActionListener(event -> {
            rightScrollPane.setViewportView(table);
        });
    }

    private void formatRaceIDButton(JTextField textField, JButton selectRaceIDButton,
            AdminModel model) {
        selectRaceIDButton.setFont(new Font("Arial", Font.PLAIN, 20));
        selectRaceIDButton.setBackground(new Color(112, 173, 71));
        selectRaceIDButton.setForeground(Color.WHITE);
        selectRaceIDButton.setPreferredSize(new Dimension(200, 50));
        selectRaceIDButton.addActionListener(event -> {

            int raceID = Integer.parseInt(textField.getText());
            model.setRaceID(raceID);

        });
    }

    private void formatResultTable(JPanel tablesPanel, JScrollPane rightScrollPane, GridBagConstraints gbc) {
        gbc.gridx = 1;
        gbc.weightx = 0.6;
        gbc.insets = new Insets(20, 10, 20, 20);
        tablesPanel.add(rightScrollPane, gbc);
    }

    private void formatTimeTable(JPanel tablesPanel, JScrollPane top, JScrollPane bottom, GridBagConstraints gbc) {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); // Stack vertically
        leftPanel.setBackground(new Color(129, 178, 223));

        top.setPreferredSize(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.2), 400));
        bottom.setPreferredSize(new Dimension((int) (Toolkit.getDefaultToolkit().getScreenSize().width * 0.2), 400));

        leftPanel.add(top);
        leftPanel.add(bottom);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 20, 20, 10);

        tablesPanel.add(leftPanel, gbc);
    }
}
