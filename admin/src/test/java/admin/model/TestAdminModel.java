package admin.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import result.model.AdminModelImpl;
import result.model.AdminModelObserver;
import shared.dto.ParticipantDTO;
import shared.dto.RaceConfigurationDTO;
import shared.dto.StationDTO;
import shared.dto.TimeDTO;

public class TestAdminModel {

    private AdminModelImpl model;
    private WebClient webClientMock;
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;
    private WebClient.ResponseSpec responseSpecMock;

    @BeforeEach
    void setUp() {

        webClientMock = mock(WebClient.class);
        requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        requestHeadersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        responseSpecMock = mock(WebClient.ResponseSpec.class);
        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(any(String.class))).thenReturn(requestHeadersSpecMock);
        when(requestHeadersUriSpecMock.uri(any(Function.class))).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.accept(any(MediaType.class))).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);

        // Prepare our mock response
        List<TimeDTO> mockResponse = List.of(new TimeDTO(1, "01", Instant.ofEpochSecond(123), Long.valueOf(1)));

        // Setup the mock to return a response with our JSON body
        when(responseSpecMock.bodyToMono(
                new ParameterizedTypeReference<List<TimeDTO>>() {
                })).thenReturn(
                        Mono.delay(Duration.ofMillis(50))
                                .then(Mono.just(mockResponse)));

        // Mock response for syncGetAllTimesFromServer()
        List<TimeDTO> mockTimesResponse = List.of(
                new TimeDTO(1, "01", Instant.ofEpochSecond(123), Long.valueOf(1)),
                new TimeDTO(1, "02", Instant.ofEpochSecond(456), Long.valueOf(2)),
                new TimeDTO(2, "03", Instant.ofEpochSecond(789), Long.valueOf(3)));
        when(responseSpecMock.bodyToMono(new ParameterizedTypeReference<List<TimeDTO>>() {
        }))
                .thenReturn(Mono.just(mockTimesResponse));

        // Mock response for syncGetAllParticipantsFromServer() to prevent
        // NullPointerException
        List<StationDTO> mockStations = List.of(
                new StationDTO(1, "Start"),
                new StationDTO(2, "Finish"));

        List<ParticipantDTO> mockParticipants = List.of(
                new ParticipantDTO("01", "Alice"),
                new ParticipantDTO("02", "Bob"),
                new ParticipantDTO("03", "Charlie"));

        when(responseSpecMock.bodyToMono(RaceConfigurationDTO.class))
                .thenReturn(Mono.just(new RaceConfigurationDTO("team-token", mockStations, mockParticipants)));

        model = new AdminModelImpl(webClientMock);

    }

    @Test
    void testFetchUpdates_UpdatesCompTableWithNewData() {
        MockAdminModelObserver observer = new MockAdminModelObserver();

        model.addObserver(observer);

        model.fetchUpdates();

        List<TimeDTO> capturedTimes = observer.getTimes();

        assertEquals(3, capturedTimes.size());

        assertEquals("01", capturedTimes.get(0).getStartNbr());
        assertEquals(Instant.ofEpochSecond(123), capturedTimes.get(0).getTime());
        assertEquals("02", capturedTimes.get(1).getStartNbr());
        assertEquals(Instant.ofEpochSecond(456), capturedTimes.get(1).getTime());
        assertEquals("03", capturedTimes.get(2).getStartNbr());
        assertEquals(Instant.ofEpochSecond(789), capturedTimes.get(2).getTime());

        assertEquals("team-token", observer.getRaceConfig().getTeamToken());
    }

    @Test
    void testFetchUpdates_UpdatesResultTableWithNewData() {
        MockAdminModelObserver observer = new MockAdminModelObserver();

        model.addObserver(observer);

        model.fetchUpdates();

        List<TimeDTO> capturedTimes = observer.getTimes();

        assertEquals(3, capturedTimes.size());

        assertEquals("01", capturedTimes.get(0).getStartNbr());
        assertEquals(Instant.ofEpochSecond(123), capturedTimes.get(0).getTime());
        assertEquals("02", capturedTimes.get(1).getStartNbr());
        assertEquals(Instant.ofEpochSecond(456), capturedTimes.get(1).getTime());
        assertEquals("03", capturedTimes.get(2).getStartNbr());
        assertEquals(Instant.ofEpochSecond(789), capturedTimes.get(2).getTime());

        assertEquals("team-token", observer.getRaceConfig().getTeamToken());
    }

    @Test
    void testFetchUpdates_WithEmptyData() {
        MockAdminModelObserver observer = new MockAdminModelObserver();
        model.addObserver(observer);

        when(responseSpecMock.bodyToMono(new ParameterizedTypeReference<List<TimeDTO>>() {
        }))
                .thenReturn(Mono.just(List.of())); // Empty list for times


        model.fetchUpdates();

        List<TimeDTO> capturedTimes = observer.getTimes();

        assertEquals(0, capturedTimes.size());
    }

    @Test
    void testMultipleListenersReceiveUpdates() {
        MockAdminModelObserver observer1 = new MockAdminModelObserver();
        MockAdminModelObserver observer2 = new MockAdminModelObserver();

        model.addObserver(observer1);
        model.addObserver(observer2);

        model.fetchUpdates();

        assertEquals(3, observer1.getTimes().size());
        assertEquals("team-token", observer1.getRaceConfig().getTeamToken());
        assertEquals(3, observer2.getTimes().size());
        assertEquals("team-token", observer2.getRaceConfig().getTeamToken());
    }
}

class MockAdminModelObserver implements AdminModelObserver {
    private RaceConfigurationDTO raceConfig;
    private List<TimeDTO> times;

    @Override
    public void onDataUpdated(RaceConfigurationDTO raceConfig, List<TimeDTO> times) {
        this.times = times;
        this.raceConfig = raceConfig;
    }

    public List<TimeDTO> getTimes() {
        return times;
    }

    public RaceConfigurationDTO getRaceConfig() {
        return raceConfig;
    }
}
