package result.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import result.model.AdminModelObserver;
import result.model.ResultsTableModel;
import shared.Utils;
import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;

public class ResultsTable extends JTable implements AdminModelObserver {
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

                        setText(totalTime.map(Utils::formatDuration).orElse("--:--:--"));
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
