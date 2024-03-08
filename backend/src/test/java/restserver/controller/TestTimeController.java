package restserver.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import restserver.config.SecurityConfig;
import restserver.entity.Time;
import restserver.service.TimeService;
import shared.dto.TimeDTO;

@WebMvcTest(TimeController.class)
@Import(SecurityConfig.class) // Make sure the SecurityConfig is included, which allows all requests
public class TestTimeController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TimeService timeService;

    @Autowired
    private ObjectMapper objectMapper; // For converting TimeDTO to JSON

    @Test
    public void fetchTimeByStartNbr_ShouldReturnTimeDTOList() throws Exception {
        // Mock the time service to return a list of Time objects
        List<Time> mockTimes = List.of(
                new Time("01", "12:34:56"),
                new Time("01", "12:34:57"),
                new Time("01", "12:35:00"));
        when(timeService.getTimesForStartNbr("01")).thenReturn(mockTimes);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/api/time/startNbr/01"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].startNbr", is("01")));
    }

    @Test
    public void registerTime_ShouldProcessTimeDTO() throws Exception {
        TimeDTO timeDTO = new TimeDTO("01", "12:34:56");
        String timeDTOJson = objectMapper.writeValueAsString(timeDTO);

        mockMvc.perform(post("/api/time/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(timeDTOJson))
                .andExpect(status().isOk());
        // Depending on your controller, adjust the expected status accordingly

        // Optionally verify that timeService.registerTime was called with the expected
        // parameters
    }
}
