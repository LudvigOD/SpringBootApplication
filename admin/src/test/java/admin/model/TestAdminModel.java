package admin.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import result.model.AdminModelImpl;
import shared.dto.TimeDTO;

public class TestAdminModel {


    private AdminModelImpl model;
    private ArrayList<TimeDTO> times;


    @BeforeEach
    void setUp() throws Exception {
        WebClient webClient = WebClient.builder()
                .baseUrl("http://localhost:8080/api")
                .build();
        model = new AdminModelImpl(webClient);
        times = new ArrayList<>();
        TimeDTO time1 = new TimeDTO(0, "1", Instant.parse("2025-02-03T20:20:20.00Z"));
        TimeDTO time2 = new TimeDTO(0, "3", Instant.parse("2025-02-03T20:22:15.00Z"));
        TimeDTO time3 = new TimeDTO(0, "2", Instant.parse("2025-02-03T20:24:23.00Z"));
        TimeDTO time4 = new TimeDTO(1, "1", Instant.parse("2025-02-03T20:25:10.00Z"));
        times.add(time1);
        times.add(time2);
        times.add(time3);
        times.add(time4);
        model.updateTimeTable(times);
    }

    @Test 
    public void testGetNbrCompetitors() {
        assertEquals(model.getNbrCompetitors(), 3);
    }

    @Test 
    public void testAddNewCompetitor() {
        List<TimeDTO> times2 = new ArrayList<>();
        TimeDTO time = new TimeDTO(0, "4", Instant.parse("2025-02-03T20:20:20.00Z"));
        times2.add(time);
        model.updateTimeTable(times2);
        assertEquals(model.getNbrCompetitors(), 4);
    }

    @Test 
    public void test2() {


    } 
 

}
