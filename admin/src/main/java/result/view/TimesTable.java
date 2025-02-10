package result.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.time.Instant;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import result.model.AdminModel;
import result.model.AdminModelObserver;
import result.model.TimesTableModel;
import shared.Utils;
import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public class TimesTable extends JTable implements AdminModelObserver {
    public TimesTable(AdminModel adminModel) {
        super(new TimesTableModel(adminModel));

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
                        // Station ID: int
                        int stationId = (int) value;

                        switch (stationId) {
                            case 0:
                                setText("Start");
                                break;
                            case 1:
                                setText("Mål");
                                break;
                                default:
                                setText("Mellanstation " + stationId);
                                break;
                        }
                        break;
                    }
                    case 1: {
                        // Start number: String
                        if (((TimesTableModel) table.getModel()).isDuplicateTime(row)) {
                            c.setForeground(new Color(255, 99, 71));
                        }

                        break;
                    }
                    case 2: {
                        // Time: Instant
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
        ((TimesTableModel) getModel()).onDataUpdated(times, participants);
    }
}
