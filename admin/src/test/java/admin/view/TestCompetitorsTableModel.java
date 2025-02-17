package admin.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import result.view.CompetitorsTableModel;

public class TestCompetitorsTableModel {

    private CompetitorsTableModel tableModel;

    @BeforeEach
    void setUp() {
        tableModel = new CompetitorsTableModel();
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

    @Test
    void testOnDataUpdated_WithEmptyLists_ClearsTable() {
        tableModel.onDataUpdated(List.of(), List.of());

        assertEquals(0, tableModel.getRowCount());
    }

    @Test
    void testIsCellEditable_AlwaysReturnsFalse() {
        assertFalse(tableModel.isCellEditable(0, 0));
        assertFalse(tableModel.isCellEditable(5, 2));
    }
}
