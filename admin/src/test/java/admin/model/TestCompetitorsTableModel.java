package admin.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;

import javax.swing.SwingUtilities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import result.dto.CompetitorDTO;
import result.model.CompetitorsCalculator;
import result.model.CompetitorsTableModel;
import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;
import static org.mockito.ArgumentMatchers.any;

public class TestCompetitorsTableModel {

    private CompetitorsTableModel tableModel;
    private CompetitorsCalculator competitorsCalculatorMock;

    @BeforeEach
    void setUp() {
        competitorsCalculatorMock = mock(CompetitorsCalculator.class);
        tableModel = new CompetitorsTableModel(competitorsCalculatorMock);
    }

    @Test
    void testGetColumnCount_ReturnsCorrectValue() {
        assertEquals(5, tableModel.getColumnCount());
    }

    @Test
    void testGetColumnName_ReturnsCorrectColumnNames() {
        assertEquals("Startnummer", tableModel.getColumnName(0));
        assertEquals("Namn", tableModel.getColumnName(1));
        assertEquals("Start", tableModel.getColumnName(2));
        assertEquals("Mål", tableModel.getColumnName(3));
        assertEquals("Totalt", tableModel.getColumnName(4));
    }

    @Test
    void testGetRowCount_InitiallyZero() {
        assertEquals(0, tableModel.getRowCount());
    }

/*     @Test
    void testGetValueAt_ReturnsCorrectValuesForColumns() {
        // Arrange: Mock competitor data
        ParticipantDTO participant = new ParticipantDTO("01", "Alice");
        TimeDTO startTime = new TimeDTO(0, "01", Instant.ofEpochSecond(100));
        TimeDTO finishTime = new TimeDTO(1, "01", Instant.ofEpochSecond(200));

        tableModel.onDataUpdated(List.of(startTime, finishTime), List.of(participant));

        // Act & Assert: Ensure correct values are returned
        assertEquals("01", tableModel.getValueAt(0, 0)); // Start number
        assertEquals("Alice", tableModel.getValueAt(0, 1)); // Name
        assertEquals(Instant.ofEpochSecond(100), tableModel.getValueAt(0, 2)); // Start time
        assertEquals(Instant.ofEpochSecond(200), tableModel.getValueAt(0, 3)); // Finish time
        assertEquals(Duration.ofSeconds(100), tableModel.getValueAt(0, 4)); // Total time
    }

    @Test
    void testGetValueAt_ReturnsNullForInvalidIndex() {
        assertNull(tableModel.getValueAt(-1, 0)); // Invalid row
        assertNull(tableModel.getValueAt(0, -1)); // Invalid column
        assertNull(tableModel.getValueAt(0, 10)); // Column out of range
    }

    @Test
    void testOnDataUpdated_PopulatesTableWithCompetitors() {
        // Arrange: Mock competitorsCalculator
        ParticipantDTO participant = new ParticipantDTO("01", "Alice");
        TimeDTO time = new TimeDTO(0, "01", Instant.ofEpochSecond(100));
        CompetitorDTO competitor = new CompetitorDTO(participant, (Map<Integer, List<Instant>>) List.of(time));

        when(competitorsCalculatorMock.aggregateTimesByParticipant(any(), any()))
            .thenReturn(List.of(competitor));

        // Act: Update table model
        tableModel.onDataUpdated(List.of(time), List.of(participant));

        // Assert: Ensure data is populated
        assertEquals(1, tableModel.getRowCount());
        assertEquals("01", tableModel.getValueAt(0, 0));
    } */

    @Test
    void testOnDataUpdated_WithEmptyLists_ClearsTable() {
        tableModel.onDataUpdated(List.of(), List.of());
        assertEquals(0, tableModel.getRowCount());
    }

    @Test
    void testOnDataUpdated_CallsFireTableDataChanged() {
        // Arrange: Spy on the model to check fireTableDataChanged()
        CompetitorsTableModel spyTableModel = spy(new CompetitorsTableModel(competitorsCalculatorMock));

        // Act
        spyTableModel.onDataUpdated(List.of(), List.of());

        // Assert: Ensure fireTableDataChanged() was called
        verify(spyTableModel, times(1)).fireTableDataChanged();
    }

    @Test
    void testIsCellEditable_AlwaysReturnsFalse() {
        assertFalse(tableModel.isCellEditable(0, 0));
        assertFalse(tableModel.isCellEditable(5, 2));
    }
}

