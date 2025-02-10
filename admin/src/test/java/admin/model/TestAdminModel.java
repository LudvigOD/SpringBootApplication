package admin.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import result.model.AdminModelImpl;
import result.model.CompetitorsTableModel;
import result.model.ResultsTableModel;
import shared.dto.ParticipantDTO;
import shared.dto.TimeDTO;


public class TestAdminModel {

    private AdminModelImpl model;
    private ArrayList<TimeDTO> times;
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
            new TimeDTO(1, "02", Instant.ofEpochSecond(456),Long.valueOf(2)),
            new TimeDTO(2, "03", Instant.ofEpochSecond(789), Long.valueOf(3))
        );
        when(responseSpecMock.bodyToMono(new ParameterizedTypeReference<List<TimeDTO>>() {}))
            .thenReturn(Mono.just(mockTimesResponse));

        // Mock response for syncGetAllParticipantsFromServer() to prevent NullPointerException
        List<ParticipantDTO> mockParticipantsResponse = List.of(
            new ParticipantDTO("01", "Alice"),
            new ParticipantDTO("02","Bob"),
            new ParticipantDTO("03","Charlie"));
        when(responseSpecMock.bodyToMono(new ParameterizedTypeReference<List<ParticipantDTO>>() {}))
            .thenReturn(Mono.just(mockParticipantsResponse));

        model = new AdminModelImpl(webClientMock);

        
    }

    @Test
    void testGetAllTimes() {

        model.fetchUpdates();
        List<TimeDTO> result = model.getAllTimes();

        assertEquals(3, result.size());
        assertEquals("01", result.get(0).getStartNbr());
        assertEquals(Instant.ofEpochSecond(123), result.get(0).getTime());
        assertEquals("02", result.get(1).getStartNbr());
        assertEquals(Instant.ofEpochSecond(456), result.get(1).getTime());
        assertEquals("03", result.get(2).getStartNbr());
        assertEquals(Instant.ofEpochSecond(789), result.get(2).getTime());
    } 

    @Test
    void testGetAllParticipants() {

        model.fetchUpdates();
        List<ParticipantDTO> result = model.getAllParticipants();

        assertEquals(3, result.size());
        assertEquals("01", result.get(0).getStartNbr());
        assertEquals("Alice", result.get(0).getName());
        assertEquals("02", result.get(1).getStartNbr());
        assertEquals("Bob", result.get(1).getName());
        assertEquals("03", result.get(2).getStartNbr());
        assertEquals("Charlie", result.get(2).getName());
    }

    @Test 
    void testFetchUpdates_UpdatesCompTableWithNewData() {

        CompetitorsTableModel mockView = mock(CompetitorsTableModel.class);
        model.addListener(mockView);

        model.fetchUpdates();

        ArgumentCaptor<List<TimeDTO>> timeCaptor = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<List<ParticipantDTO>> participantCaptor = ArgumentCaptor.forClass(List.class);
        verify(mockView, times(2)).onDataUpdated(timeCaptor.capture(), participantCaptor.capture());

        List<TimeDTO> capturedTimes = timeCaptor.getValue();
        List<ParticipantDTO> capturedParticipants = participantCaptor.getValue();

        assertEquals(3, capturedTimes.size());
        assertEquals(3, capturedParticipants.size());

        assertEquals("01", capturedTimes.get(0).getStartNbr());
        assertEquals(Instant.ofEpochSecond(123), capturedTimes.get(0).getTime());
        assertEquals("02", capturedTimes.get(1).getStartNbr());
        assertEquals(Instant.ofEpochSecond(456), capturedTimes.get(1).getTime());
        assertEquals("03", capturedTimes.get(2).getStartNbr());
        assertEquals(Instant.ofEpochSecond(789), capturedTimes.get(2).getTime());

        assertEquals("01", capturedParticipants.get(0).getStartNbr());
        assertEquals("Alice", capturedParticipants.get(0).getName());
        assertEquals("02", capturedParticipants.get(1).getStartNbr());
        assertEquals("Bob", capturedParticipants.get(1).getName());
        assertEquals("03", capturedParticipants.get(2).getStartNbr());
        assertEquals("Charlie", capturedParticipants.get(2).getName());
    }

    @Test 
    void testFetchUpdates_UpdatesResultTableWithNewData() {

        ResultsTableModel mockView = mock(ResultsTableModel.class);
        model.addListener(mockView);

        model.fetchUpdates();

        ArgumentCaptor<List<TimeDTO>> timeCaptor = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<List<ParticipantDTO>> participantCaptor = ArgumentCaptor.forClass(List.class);
        verify(mockView, times(2)).onDataUpdated(timeCaptor.capture(), participantCaptor.capture());

        List<TimeDTO> capturedTimes = timeCaptor.getValue();
        List<ParticipantDTO> capturedParticipants = participantCaptor.getValue();

        assertEquals(3, capturedTimes.size());
        assertEquals(3, capturedParticipants.size());

        assertEquals("01", capturedTimes.get(0).getStartNbr());
        assertEquals(Instant.ofEpochSecond(123), capturedTimes.get(0).getTime());
        assertEquals("02", capturedTimes.get(1).getStartNbr());
        assertEquals(Instant.ofEpochSecond(456), capturedTimes.get(1).getTime());
        assertEquals("03", capturedTimes.get(2).getStartNbr());
        assertEquals(Instant.ofEpochSecond(789), capturedTimes.get(2).getTime());

        assertEquals("01", capturedParticipants.get(0).getStartNbr());
        assertEquals("Alice", capturedParticipants.get(0).getName());
        assertEquals("02", capturedParticipants.get(1).getStartNbr());
        assertEquals("Bob", capturedParticipants.get(1).getName());
        assertEquals("03", capturedParticipants.get(2).getStartNbr());
        assertEquals("Charlie", capturedParticipants.get(2).getName());
    }

    @Test
    void testFetchUpdates_WithEmptyData() {
        when(responseSpecMock.bodyToMono(new ParameterizedTypeReference<List<TimeDTO>>() {}))
            .thenReturn(Mono.just(List.of())); // Empty list for times

        when(responseSpecMock.bodyToMono(new ParameterizedTypeReference<List<ParticipantDTO>>() {}))
            .thenReturn(Mono.just(List.of())); // Empty list for participants

        model.fetchUpdates();

        assertEquals(0, model.getAllTimes().size());
        assertEquals(0, model.getAllParticipants().size());
    }

    @Test
    void testFetchUpdates_OnlyTimes() {
        List<TimeDTO> mockTimes = List.of(
            new TimeDTO(1, "01", Instant.ofEpochSecond(123), 1L),
            new TimeDTO(1, "02", Instant.ofEpochSecond(456), 2L)
        );

        when(responseSpecMock.bodyToMono(new ParameterizedTypeReference<List<TimeDTO>>() {}))
            .thenReturn(Mono.just(mockTimes));

        when(responseSpecMock.bodyToMono(new ParameterizedTypeReference<List<ParticipantDTO>>() {}))
            .thenReturn(Mono.just(List.of())); // No participants

        model.fetchUpdates();

        assertEquals(2, model.getAllTimes().size());
        assertEquals(0, model.getAllParticipants().size());
    }

    @Test
    void testFetchUpdates_OnlyParticipants() {
        List<ParticipantDTO> mockParticipants = List.of(
            new ParticipantDTO("01", "Alice"),
            new ParticipantDTO("02", "Bob")
        );

        when(responseSpecMock.bodyToMono(new ParameterizedTypeReference<List<TimeDTO>>() {}))
            .thenReturn(Mono.just(List.of())); // No times

        when(responseSpecMock.bodyToMono(new ParameterizedTypeReference<List<ParticipantDTO>>() {}))
            .thenReturn(Mono.just(mockParticipants));

        model.fetchUpdates();

        assertEquals(0, model.getAllTimes().size());
        assertEquals(2, model.getAllParticipants().size());
    }
    @Test
    void testMultipleListenersReceiveUpdates() {
        CompetitorsTableModel mockView1 = mock(CompetitorsTableModel.class);
        ResultsTableModel mockView2 = mock(ResultsTableModel.class);

        model.addListener(mockView1);
        model.addListener(mockView2);

        model.fetchUpdates();

        verify(mockView1, times(2)).onDataUpdated(any(), any());
        verify(mockView2, times(2)).onDataUpdated(any(), any());
    }
}
