package result;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableCellRenderer;

public class AdminView {

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Admin Verktyget");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1600, 1133);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(165, 165, 165));
        frame.add(mainPanel);

        JTable leftTable = createLeftTable();
        JTable rightTable = createRightTable();

        JScrollPane leftScrollPane = new JScrollPane(leftTable);
        JScrollPane rightScrollPane = new JScrollPane(rightTable);

        leftScrollPane.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(129, 178, 223)));
        rightScrollPane.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 10, new Color(156, 202, 124)));

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int totalHeight = (int)(screenSize.height * 0.9);
        leftScrollPane.setPreferredSize(new Dimension((int)(screenSize.width * 0.2), totalHeight));
        rightScrollPane.setPreferredSize(new Dimension((int)(screenSize.width * 0.6), totalHeight));

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

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    private static JTable createLeftTable() {
        String[] columnNames = {"Station", "Nr.", "Tid"};
        Object[][] data = {
            {"s", "1", "-"},
            {"s", "2", "-"},
            {"s", "3", "-"},
            {"s", "4", "-"},
            {"e", "2", "-"},
            {"e", "3", "-"},
            {"e", "1", "-"},
            {"e", "2", "-"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        table.setBackground(new Color(230, 230, 255));
        table.setRowHeight(50);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        table.setFillsViewportHeight(true);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setForeground(Color.BLACK);
                
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
                setHorizontalAlignment(SwingConstants.LEFT);
                
                if (row % 2 == 0) {
                    c.setBackground(new Color(240, 240, 255));
                } else {
                    c.setBackground(new Color(255, 255, 255));
                }

                if (isSelected) {
                    c.setBackground(new Color(100, 149, 237));
                }

                if (row == 4 && column == 1) {
                    c.setForeground(new Color(255, 99, 71));
                }

                return c;
            }
        });

        table.getTableHeader().setBackground(new Color(129, 178, 223));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));

        return table;
    }

    private static JTable createRightTable() {
        String[] columnNames = {"Nr.", "Namn", "Start", "Mål", "Totalt"};
        Object[][] data = {
            {"1", "AA", "-", "-", "-"},
            {"2", "BB", "-", "2 tider!", "-"},
            {"3", "CC", "-", "-", "-"},
            {"4", "DD", "-", "X", "?"}
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        table.setBackground(new Color(240, 255, 240));
        table.setRowHeight(50);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        table.setFillsViewportHeight(true);

        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                                                           boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
                setHorizontalAlignment(SwingConstants.LEFT);
                
                if (row % 2 == 0) {
                    c.setBackground(new Color(240, 255, 240));
                } else {
                    c.setBackground(new Color(255, 255, 255));
                }

                if (isSelected) {
                    c.setBackground(new Color(100, 149, 237));
                }

                return c;
            }
        });

        table.getTableHeader().setBackground(new Color(156, 202, 124));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 18));

        return table;
    }
}
