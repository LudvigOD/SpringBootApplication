package admin.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import result.view.ResultsTableModel;
import shared.dto.RaceConfigurationDTO;

public class TestResultsTableModel {

    private ResultsTableModel tableModel;

    @BeforeEach
    void setUp() {
        tableModel = new ResultsTableModel();
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
        tableModel.onDataUpdated(mock(RaceConfigurationDTO.class), List.of());

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

    @Test
    void testGetRowCount_InitiallyZero() {
        assertEquals(0, tableModel.getRowCount());
    }
}
