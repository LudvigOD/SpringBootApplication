package admin.view;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import result.view.CompetitorsTableModel;
import shared.dto.RaceConfigurationDTO;

public class TestCompetitorsTableModel {

    private CompetitorsTableModel tableModel;

    @BeforeEach
    void setUp() {
        tableModel = new CompetitorsTableModel();
    }

    @Test
    void testGetColumnName_InitiallyDefault() {
        assertEquals("Startnummer", tableModel.getColumnName(0));
        assertEquals("Namn", tableModel.getColumnName(1));
        assertEquals("Totaltid", tableModel.getColumnName(2));

        assertEquals(3, tableModel.getColumnCount());
    }

    @Test
    void testGetRowCount_InitiallyZero() {
        assertEquals(0, tableModel.getRowCount());
    }

    @Test
    void testOnDataUpdated_WithEmptyLists_ClearsTable() {
        tableModel.onDataUpdated(mock(RaceConfigurationDTO.class), List.of());

        assertEquals(0, tableModel.getRowCount());
    }

    @Test
    void testIsCellEditable_AlwaysReturnsFalse() {
        assertFalse(tableModel.isCellEditable(0, 0));
        assertFalse(tableModel.isCellEditable(5, 2));
    }
}
