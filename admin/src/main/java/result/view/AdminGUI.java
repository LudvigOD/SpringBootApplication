package result.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import result.model.AdminModel;
import result.model.AdminModelObserver;
import result.model.CompetitorsTableModel;
import result.model.ResultsTableModel;
import shared.Utils;
import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public class AdminGUI extends JFrame {
    public AdminGUI(AdminModel model) {
        setTitle("Adminverktyg");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 1133);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel tablesPanel = new JPanel(new GridBagLayout());
        tablesPanel.setBackground(new Color(165, 165, 165));

        TimesTable timesTable = new TimesTable();
        CompetitorsTable competitorsTable = new CompetitorsTable();
        ResultsTable resultsTable = new ResultsTable();

        model.addListener(timesTable);
        model.addListener(competitorsTable);
        model.addListener(resultsTable);

        JScrollPane leftScrollPane = new JScrollPane(timesTable);
        JScrollPane rightScrollPane = new JScrollPane(competitorsTable);

        JButton selectCompetitorsTableButton = new JButton("Deltagare");
        selectCompetitorsTableButton.setFont(new Font("Arial", Font.PLAIN, 20));
        selectCompetitorsTableButton.setBackground(new Color(112, 173, 71));
        selectCompetitorsTableButton.setForeground(Color.WHITE);
        selectCompetitorsTableButton.setPreferredSize(new Dimension(200, 50));
        selectCompetitorsTableButton.addActionListener(event -> {
            rightScrollPane.setViewportView(competitorsTable);
        });

        JButton selectResultsTableButton = new JButton("Resultat");
        selectResultsTableButton.setFont(new Font("Arial", Font.PLAIN, 20));
        selectResultsTableButton.setBackground(new Color(112, 173, 71));
        selectResultsTableButton.setForeground(Color.WHITE);
        selectResultsTableButton.setPreferredSize(new Dimension(200, 50));
        selectResultsTableButton.addActionListener(event -> {
            rightScrollPane.setViewportView(resultsTable);
        });

        leftScrollPane.getViewport().setBackground(new Color(129, 178, 223));
        rightScrollPane.getViewport().setBackground(new Color(156, 202, 124));

        leftScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(65, 65, 65), 2),
                BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(129, 178, 223))));

        rightScrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(65, 65, 65), 2),
                BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(156, 202, 124))));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int totalHeight = (int) (screenSize.height * 0.9);
        leftScrollPane.setPreferredSize(new Dimension((int) (screenSize.width * 0.2), totalHeight));
        rightScrollPane.setPreferredSize(new Dimension((int) (screenSize.width * 0.6), totalHeight));
/* 
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(165, 165, 165));
        inputPanel.add(selectCompetitorsTableButton);
        inputPanel.add(selectResultsTableButton);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 20, 20, 10);
        tablesPanel.add(leftScrollPane, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.6;
        gbc.insets = new Insets(20, 10, 20, 20);
        tablesPanel.add(rightScrollPane, gbc);
*/      
        // Lägger till en buttonPanel för att kunna centrera knapparna när fönstret minskas
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(165, 165, 165));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(165, 165, 165));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        buttonPanel.add(selectCompetitorsTableButton);
        buttonPanel.add(selectResultsTableButton);

        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        inputPanel.add(buttonPanel);

        GridBagLayout gridLayout = new GridBagLayout();
        //gBoxLayout verticalLayout = new BoxLayout(inputPanel, BoxLayout.Y_AXIS);
        inputPanel.setLayout(gridLayout);

        JFrame frame = this;
        GridBagLayout gridLayoutInput = new GridBagLayout();
        BoxLayout verticalLayoutInput = new BoxLayout(buttonPanel, BoxLayout.Y_AXIS);
        inputPanel.setLayout(gridLayout);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (frame.getWidth() < 900) {   // när fönstret är under 900 pixlar används vertikal-layout
                    buttonPanel.setLayout(verticalLayoutInput);
                } else {
                    buttonPanel.setLayout(gridLayoutInput); // annars används gridlayout (bredvid varandra)
                }
                buttonPanel.revalidate(); // berättar för komponenterna att de ska ändra form/layout
                buttonPanel.repaint(); // ritar ut den nya layouten
            }
        });

            BoxLayout verticalLayoutTable = new BoxLayout(tablesPanel, BoxLayout.Y_AXIS);

            tablesPanel.setLayout(gridLayout);

            // GridBagConstraints för att placera tabellerna bredvid varandra
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.weightx = 0.2;
            gbc.weighty = 1.0;
            gbc.fill = GridBagConstraints.BOTH;
            gbc.insets = new Insets(20, 20, 20, 10);
            tablesPanel.add(leftScrollPane, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0.;
            gbc.insets = new Insets(20, 10, 20, 20);
            tablesPanel.add(rightScrollPane, gbc);

            // Lägg till en lyssnare som byter layout baserat på fönsterbredd
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    tablesPanel.removeAll(); // Ta bort allt innehåll innan byte

                    if (getWidth() < 900) { // Om skärmen är mindre än 900 pixlar hamnar tabellerna ovanför varandra
                        tablesPanel.setLayout(verticalLayoutTable);
                        tablesPanel.add(leftScrollPane);
                        tablesPanel.add(rightScrollPane);
                    } else { // Om det är fullskärm ritas tabellerna ut bredvid varandra som innan
                        tablesPanel.setLayout(gridLayout);

                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        gbc.weightx = 0.2;
                        gbc.weighty = 1.0;
                        gbc.insets = new Insets(20, 20, 20, 10);
                        tablesPanel.add(leftScrollPane, gbc);

                        gbc.gridx = 1;
                        gbc.weightx = 0.6;
                        gbc.insets = new Insets(20, 10, 20, 20);
                        tablesPanel.add(rightScrollPane, gbc);

                    }

                    tablesPanel.revalidate(); // Uppdatera layout
                    tablesPanel.repaint(); // Rita ut fönstret på nytt
                }
            });

        selectResultsTableButton.setMaximumSize(new Dimension(200, 50));
        selectCompetitorsTableButton.setMaximumSize(new Dimension(200, 50));

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(tablesPanel, BorderLayout.CENTER);

        add(mainPanel);
    }
}

class TimesTable extends JTable implements AdminModelObserver {
    private static final String PATTERN_FORMAT = "hh:mm:ss.S";

    public TimesTable() {
        super(new DefaultTableModel(new String[] { "Station", "Nr.", "Tid" }, 0));



        setShowGrid(true);
        setGridColor(Color.WHITE);

        setBackground(new Color(129, 178, 223));
        setRowHeight(40);
        setFont(new Font("Arial", Font.PLAIN, 20));
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        setFillsViewportHeight(false);

        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setForeground(Color.BLACK);
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

                setHorizontalAlignment(SwingConstants.LEFT);

                if (row % 2 == 0) {
                    c.setBackground(new Color(210, 222, 239));
                } else {
                    c.setBackground(new Color(234, 239, 247));
                }

                // if we need to select cells
                // if (isSelected) {
                // c.setBackground(new Color(100, 149, 237));
                // }

                // if (row == 4 && column == 1) {
                // c.setForeground(new Color(255, 99, 71));
                // }

                switch (column) {
                    case 0: {
                        // Station ID
                        int stationId = (int) value;

                        switch (stationId) {
                            case 1:
                                setText("Start");
                                break;
                            case 2:
                                setText("Mål");
                                break;
                            default:
                                setText("Mellanstation " + stationId);
                                break;
                        }
                        break;
                    }
                    case 1: {
                        // Start number
                        break;
                    }
                    case 2: {
                        // Time
                        Instant time = (Instant) value;

                        setText(Utils.displayTimeInCorrectFormat(time));
                        break;
                    }
                }

                return c;
            }
        });

        var header = getTableHeader();

        header.setBackground(new Color(91, 155, 213));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setPreferredSize(new Dimension(5, 40));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
    }

    @Override
    public void onDataUpdated(List<TimeDTO> times, List<ParticipantDTO> participants) {
        DefaultTableModel model = (DefaultTableModel) getModel();
        model.setRowCount(0);

        for (TimeDTO time : times) {
            model.addRow(new Object[] { time.getStationId(), time.getStartNbr(), time.getTime() });
        }
    }
}

class CompetitorsTable extends JTable implements AdminModelObserver {
    public CompetitorsTable() {
        super(new CompetitorsTableModel());


        setShowGrid(true);
        setGridColor(Color.WHITE);

        setBackground(new Color(156, 202, 124));
        setRowHeight(40);
        setFont(new Font("Arial", Font.PLAIN, 20));
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        setFillsViewportHeight(false);

        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
                setHorizontalAlignment(SwingConstants.LEFT);

                if (row % 2 == 0) {
                    c.setBackground(new Color(213, 227, 207));
                } else {
                    c.setBackground(new Color(235, 241, 233));
                }

                // if (isSelected) {
                // c.setBackground(new Color(100, 149, 237));
                // }

                switch (column) {
                    case 0: {
                        // Start number: String
                        break;
                    }
                    case 1: {
                        // Name: String
                        break;
                    }
                    case 2: {
                        // Start time: Optional<Instant>
                        @SuppressWarnings("unchecked")
                        Optional<Instant> startTime = (Optional<Instant>) value;
                        if(startTime.isPresent()) {
                            setText(Utils.displayTimeInCorrectFormat(startTime.get()));
                        } else {
                            setText("--:--:--");
                        }
                        break;
                    }
                    case 3: {
                        // Finish time: Optional<Instant>
                        @SuppressWarnings("unchecked")
                        Optional<Instant> finishTime = (Optional<Instant>) value;
                        if(finishTime.isPresent()) {
                            setText(Utils.displayTimeInCorrectFormat(finishTime.get()));
                        } else {
                            setText("--:--:--");
                        }
                        break;
                    }
                    case 4: {
                        // Total time: Optional<Duration>
                        @SuppressWarnings("unchecked")
                        Optional<Duration> totalTime = (Optional<Duration>) value;

                        setText(totalTime.map(t -> {
                            // Format to HH:mm:ss.S
                            long hours = t.toHours();
                            long minutes = t.toMinutes() % 60;
                            long seconds = t.getSeconds() % 60;
                            long millis = (t.toMillis() % 1000) / 100;

                            return String.format("%02d:%02d:%02d.%d", hours, minutes, seconds, millis);
                        }).orElse("--:--:--"));
                        break;
                    }
                }

                return c;
            }
        });

        var header = getTableHeader();

        header.setBackground(new Color(112, 173, 71));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setPreferredSize(new Dimension(5, 40));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
    }

    @Override
    public void onDataUpdated(List<TimeDTO> times, List<ParticipantDTO> participants) {
        ((CompetitorsTableModel) getModel()).onDataUpdated(times, participants);
    }
}

class ResultsTable extends JTable implements AdminModelObserver {
    public ResultsTable() {
        super(new ResultsTableModel());

        setShowGrid(true);
        setGridColor(Color.WHITE);

        setBackground(new Color(156, 202, 124));
        setRowHeight(40);
        setFont(new Font("Arial", Font.PLAIN, 20));
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        setFillsViewportHeight(false);

        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
                setHorizontalAlignment(SwingConstants.LEFT);

                if (row % 2 == 0) {
                    c.setBackground(new Color(213, 227, 207));
                } else {
                    c.setBackground(new Color(235, 241, 233));
                }

                // if (isSelected) {
                // c.setBackground(new Color(100, 149, 237));
                // }

                switch (column) {
                    case 0: {
                        // Place: Optional<Integer>
                        @SuppressWarnings("unchecked")
                        Optional<Integer> place = (Optional<Integer>) value;

                        setText(place.map(Object::toString).orElse("-"));
                        break;
                    }
                    case 1: {
                        // Start number: String
                        setText(value.toString());
                        break;
                    }
                    case 2: {
                        // Name: String
                        setText(value.toString());
                        break;
                    }
                    case 3: {
                        // Total time: Optional<Duration>
                        @SuppressWarnings("unchecked")
                        Optional<Duration> totalTime = (Optional<Duration>) value;

                        setText(totalTime.map(t -> {
                            // Format to HH:mm:ss.S
                            long hours = t.toHours();
                            long minutes = t.toMinutes() % 60;
                            long seconds = t.getSeconds() % 60;
                            long millis = (t.toMillis() % 1000) / 100;

                            return String.format("%02d:%02d:%02d.%01d", hours, minutes, seconds, millis);
                        }).orElse("--:--:--"));
                        break;
                    }
                }

                return c;
            }
        });

        var header = getTableHeader();

        header.setBackground(new Color(112, 173, 71));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setPreferredSize(new Dimension(5, 40));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);
    }

    @Override
    public void onDataUpdated(List<TimeDTO> times, List<ParticipantDTO> participants) {
        ((ResultsTableModel) getModel()).onDataUpdated(times, participants);
    }
}
