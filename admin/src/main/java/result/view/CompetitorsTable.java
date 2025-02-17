package result.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import result.model.AdminModel;
import result.model.StationStartNumberAdminModel;
import shared.Utils;

public class CompetitorsTable extends JTable {
    public CompetitorsTable(AdminModel adminModel, CompetitorsTableModel model) {
        super(model);

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
                c.setForeground(Color.BLACK);
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
                        // Total time: Optional<Duration>
                        @SuppressWarnings("unchecked")
                        Optional<Duration> totalTime = (Optional<Duration>) value;

                        setText(totalTime.map(Utils::formatDuration).orElse("--:--:--"));

                        break;
                    }
                    default: {
                        if (model.isDuration(column)) {
                            // Station Duration: Optional<Duration>
                            @SuppressWarnings("unchecked")
                            Optional<Duration> stationDuration = (Optional<Duration>) value;

                            setText(stationDuration.map(Utils::formatDuration).orElse("--:--:--"));
                        } else {
                            // Station Times: List<Instant>
                            @SuppressWarnings("unchecked")
                            List<Instant> times = (List<Instant>) value;

                            switch (times.size()) {
                                case 0:
                                    setText("Tid saknas");
                                    c.setForeground(new Color(255, 99, 71));
                                    break;

                                case 1:
                                    setText(Utils.formatInstant(times.get(0)));
                                    break;

                                default:
                                    setText("Flera tider");
                                    c.setForeground(new Color(255, 99, 71));
                                    break;
                            }

                        }

                        break;
                    }
                }

                return c;
            }
        });

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = rowAtPoint(e.getPoint());
                int column = columnAtPoint(e.getPoint());
                CompetitorsTableModel model = (CompetitorsTableModel) getModel();

                if (column > 3) {
                        long stationId = model.getStationId(column);
                        String startNumber = (String) getValueAt(row, 0);

                        TimesWindow.openTimesDialog(
                                new StationStartNumberAdminModel(adminModel, stationId, startNumber));
                }
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
}
