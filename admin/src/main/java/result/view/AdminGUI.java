package result.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import shared.dto.TimeDTO;

public class AdminGUI extends JFrame implements AdminView {

    private final AdminModel model;

    private final DefaultTableModel timesTableModel = new DefaultTableModel(new String[] { "Station", "Nr.", "Tid" },
            0);
    private final DefaultTableModel resultsTableModel = new DefaultTableModel(
            new String[] { "Nr.", "Namn", "Start", "Mål", "Totalt" },
            0);

    public AdminGUI(AdminModel model) {
        this.model = model;
        this.model.addListener(this);

        initGUI();
    }

    @Override
    public void onTimeAdded(TimeDTO time) {
        Object[] row = { time.getStationId(), time.getStartNbr(), time.getTime() };
        timesTableModel.addRow(row);
    }

    public void initGUI() {
        setTitle("Adminverktyg");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1600, 1133);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(165, 165, 165));

        JTable leftTable = createTimesTable();
        JTable rightTable = createResultsTable();

        JScrollPane leftScrollPane = new JScrollPane(leftTable);
        JScrollPane rightScrollPane = new JScrollPane(rightTable);

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

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 20, 20, 10);
        mainPanel.add(leftScrollPane, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.6;
        gbc.insets = new Insets(20, 10, 20, 20);
        mainPanel.add(rightScrollPane, gbc);

        add(mainPanel);
    }

    private JTable createTimesTable() {
        JTable table = new JTable(timesTableModel);
        table.setShowGrid(true);
        table.setGridColor(Color.WHITE);

        table.setBackground(new Color(129, 178, 223));
        table.setRowHeight(40);
        table.setFont(new Font("Arial", Font.PLAIN, 20));
        table.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        table.setFillsViewportHeight(false);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

                if (row == 4 && column == 1) {
                    c.setForeground(new Color(255, 99, 71));
                }

                return c;
            }
        });

        var header = table.getTableHeader();

        header.setBackground(new Color(91, 155, 213));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setPreferredSize(new Dimension(5, 40));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);

        model.getAllTimes().forEach(time -> {
            Object[] row = { time.getStationId(), time.getStartNbr(), time.getTime() };
            timesTableModel.addRow(row);
        });

        return table;
    }

    private JTable createResultsTable() {
        String[] columnNames = { "Nr.", "Namn", "Start", "Mål", "Totalt" };
        Object[][] data = {
                { "1", "AA", "-", "-", "-" },
                { "2", "BB", "-", "2 tider!", "-" },
                { "3", "CC", "-", "-", "-" },
                { "4", "DD", "-", "X", "?" }
        };

        JTable table = new JTable(resultsTableModel);
        table.setShowGrid(true);
        table.setGridColor(Color.WHITE);

        table.setBackground(new Color(156, 202, 124));
        table.setRowHeight(40);
        table.setFont(new Font("Arial", Font.PLAIN, 20));
        table.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        table.setFillsViewportHeight(false);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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

                return c;
            }
        });

        var header = table.getTableHeader();

        header.setBackground(new Color(112, 173, 71));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setPreferredSize(new Dimension(5, 40));
        header.setReorderingAllowed(false);
        header.setResizingAllowed(false);

        model.getResults().forEach(result -> {
            Object[] row = { result.startNbr, result.name, result.startTime, result.finishTime, result.totalDuration };
            resultsTableModel.addRow(row);
        });

        return table;
    }
}
