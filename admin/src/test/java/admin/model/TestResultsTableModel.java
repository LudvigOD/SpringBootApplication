package admin.model;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import result.model.CompetitorsCalculator;
import result.model.ResultsCalculator;
import result.model.ResultsTableModel;

public class TestResultsTableModel {

    private ResultsTableModel tableModel;
    private CompetitorsCalculator competitorsCalculatorMock;
    private ResultsCalculator resultsCalculatorMock;

    @BeforeEach
    void setUp() {
        competitorsCalculatorMock = mock(CompetitorsCalculator.class);
        resultsCalculatorMock = mock(ResultsCalculator.class);
        tableModel = new ResultsTableModel(competitorsCalculatorMock, resultsCalculatorMock);
    }

    @Test
    void testGetColumnCount_ReturnsCorrectValue() {
        assertEquals(4, tableModel.getColumnCount());
    }

    @Test
    void testGetColumnName_ReturnsCorrectColumnNames() {
        assertEquals("Placering", tableModel.getColumnName(0));
        assertEquals("Startnummer", tableModel.getColumnName(1));
        assertEquals("Namn", tableModel.getColumnName(2));
        assertEquals("Total Tid", tableModel.getColumnName(3));
    }

    @Test
    void testOnDataUpdated_WithEmptyLists_ClearsTable() {
        tableModel.onDataUpdated(List.of(), List.of());
        assertEquals(0, tableModel.getRowCount());
    }

/*     @Test
    void testOnDataUpdated_CallsFireTableDataChanged() {
        // Arrange: Spy on the model to check fireTableDataChanged()
        ResultsTableModel spyTableModel = spy(new ResultsTableModel(competitorsCalculatorMock, resultsCalculatorMock));

        // Act
        spyTableModel.onDataUpdated(List.of(), List.of());

        // Assert: Ensure fireTableDataChanged() was called
        verify(spyTableModel, times(1)).fireTableDataChanged();
    } */

    @Test
    void testIsCellEditable_AlwaysReturnsFalse() {
        assertFalse(tableModel.isCellEditable(0, 0));
        assertFalse(tableModel.isCellEditable(5, 2));
    }
}
