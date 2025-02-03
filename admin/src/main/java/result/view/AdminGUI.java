package result.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import result.model.AdminModel;
import result.model.AdminView;
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
        ParticipantsTable participantsTable = new ParticipantsTable();
        ResultsTable resultsTable = new ResultsTable();

        model.addListener(timesTable);
        model.addListener(participantsTable);
        model.addListener(resultsTable);

        JScrollPane leftScrollPane = new JScrollPane(timesTable);
        JScrollPane rightScrollPane = new JScrollPane(participantsTable);

        JButton selectParticipantsTableButton = new JButton("Deltagare");
        selectParticipantsTableButton.setFont(new Font("Arial", Font.PLAIN, 20));
        selectParticipantsTableButton.setBackground(new Color(112, 173, 71));
        selectParticipantsTableButton.setForeground(Color.WHITE);
        selectParticipantsTableButton.setPreferredSize(new Dimension(200, 50));
        selectParticipantsTableButton.addActionListener(event -> {
            rightScrollPane.setViewportView(participantsTable);
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

        GridBagConstraints gbc = new GridBagConstraints();

        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(165, 165, 165));
        inputPanel.add(selectParticipantsTableButton);
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

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(tablesPanel, BorderLayout.CENTER);

        add(mainPanel);
    }
}

class TimesTable extends JTable implements AdminView {
    private static final String PATTERN_FORMAT = "hh:mm:ss.S";

    public TimesTable() {
        super(new DefaultTableModel(new String[] { "Station", "Nr.", "Tid" }, 0));

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
                .withZone(ZoneId.systemDefault());

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

                        setText(formatter.format(time));
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

class ParticipantsTable extends JTable implements AdminView {
    private static final String PATTERN_FORMAT = "hh:mm:ss.S";

    public ParticipantsTable() {
        super(new ParticipantsTableModel());

        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FORMAT)
                .withZone(ZoneId.systemDefault());

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
                        // Start number
                        break;
                    }
                    case 1: {
                        // Name
                        break;
                    }
                    case 2: {
                        // Start time
                        @SuppressWarnings("unchecked")
                        Optional<Instant> startTime = (Optional<Instant>) value;

                        setText(startTime.map(formatter::format).orElse("--:--:--"));
                        break;
                    }
                    case 3: {
                        // Finish time
                        @SuppressWarnings("unchecked")
                        Optional<Instant> finishTime = (Optional<Instant>) value;

                        setText(finishTime.map(formatter::format).orElse("--:--:--"));
                        break;
                    }
                    case 4: {
                        @SuppressWarnings("unchecked")
                        Optional<Duration> totalTime = (Optional<Duration>) value;

                        setText(totalTime.map(t -> {
                            // Format to HH:mm:ss.S
                            long hours = t.toHours();
                            long minutes = t.toMinutes() % 60;
                            long seconds = t.getSeconds() % 60;
                            long millis = t.toMillis() % 1000;

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
        ((ParticipantsTableModel) getModel()).onDataUpdated(times, participants);
    }
}

class ResultsTable extends JTable implements AdminView {
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
                        // Place
                        @SuppressWarnings("unchecked")
                        Optional<Integer> place = (Optional<Integer>) value;

                        setText(place.map(Object::toString).orElse("-"));
                        break;
                    }
                    case 1: {
                        // Start number
                        setText(value.toString());
                        break;
                    }
                    case 2: {
                        // Name
                        setText(value.toString());
                        break;
                    }
                    case 3: {
                        // Total Time
                        @SuppressWarnings("unchecked")
                        Optional<Duration> totalTime = (Optional<Duration>) value;

                        setText(totalTime.map(t -> {
                            // Format to HH:mm:ss.S
                            long hours = t.toHours();
                            long minutes = t.toMinutes() % 60;
                            long seconds = t.getSeconds() % 60;
                            long millis = t.toMillis() % 1000;

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
        ((ResultsTableModel) getModel()).onDataUpdated(times, participants);
    }
}
