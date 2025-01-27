package restserver.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

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

@WebMvcTest(TimesController.class)
@Import(SecurityConfig.class) // Make sure the SecurityConfig is included, which allows all requests
public class TestTimesController {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private TimeService timeService;

        @Autowired
        private ObjectMapper objectMapper; // For converting TimeDTO to JSON

        @Test
        public void registerTime_ShouldProcessTimeDTO() throws Exception {
                TimeDTO timeDTO = new TimeDTO(1, "01", Instant.ofEpochSecond(123));
                String timeDTOJson = objectMapper.writeValueAsString(timeDTO);

                mockMvc.perform(post("/api/races/1/times")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(timeDTOJson))
                                .andExpect(status().isOk());

                // verify that timeService.registerTime was called with the expected parameters
                // (this is a Mockito verification)
                verify(timeService).registerTime(1, 1, "01", Instant.ofEpochSecond(123));
        }

        @Test
        public void fetchAllTimes_ShouldReturnTimeDTOList() throws Exception {
                List<Time> mockTimes = List.of(
                                new Time(1, 1, "01", Instant.ofEpochSecond(123)),
                                new Time(1, 1, "01", Instant.ofEpochSecond(456)),
                                new Time(1, 1, "01", Instant.ofEpochSecond(789)));
                when(timeService.getAllTimes(1, Optional.empty(), Optional.empty())).thenReturn(mockTimes);

                // Perform the GET request and verify the response
                mockMvc.perform(get("/api/races/1/times"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$", hasSize(3)));
        }

        @Test
        public void fetchAllTimes_ShouldFilterByStationId() throws Exception {
                List<Time> mockTimes = List.of(
                                new Time(3, 2, "02", Instant.ofEpochSecond(123)));

                when(timeService.getAllTimes(1, Optional.of(2), Optional.empty())).thenReturn(mockTimes);

                mockMvc.perform(get("/api/races/1/times?stationId=2"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$", hasSize(1)))
                                .andExpect(jsonPath("$[0].stationId", is(2)));
        }

        @Test
        public void fetchAllTimes_ShouldFilterByStartNbr() throws Exception {
                List<Time> mockTimes = List.of(
                                new Time(1, 1, "02", Instant.ofEpochSecond(123)));

                when(timeService.getAllTimes(1, Optional.empty(), Optional.of("02"))).thenReturn(mockTimes);

                mockMvc.perform(get("/api/races/1/times?startNbr=02"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$", hasSize(1)))
                                .andExpect(jsonPath("$[0].startNbr", is("02")));
        }
}
