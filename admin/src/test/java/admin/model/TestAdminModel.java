package admin.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;
import result.model.AdminModelImpl;
import shared.dto.TimeDTO;

public class TestAdminModel {


    private AdminModelImpl adminModel;
    private WebClient webClientMock;
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;
    private WebClient.ResponseSpec responseSpecMock;


    @BeforeEach
    void setUp() throws Exception {
        // Mock WebClient and its method chain, i.e. the same chain that is used in the
        // actual implementation in AdminModelImpl.
        webClientMock = mock(WebClient.class);
        requestHeadersUriSpecMock = mock(WebClient.RequestHeadersUriSpec.class);
        requestHeadersSpecMock = mock(WebClient.RequestHeadersSpec.class);
        responseSpecMock = mock(WebClient.ResponseSpec.class);

        when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        when(requestHeadersUriSpecMock.uri(any(String.class))).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.accept(any(MediaType.class))).thenReturn(requestHeadersSpecMock);
        when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);

        // Prepare our mock response
        List<TimeDTO> mockResponse = List.of(new TimeDTO(1, "01", Instant.ofEpochSecond(123)));

        // Setup the mock to return a response with our JSON body
        when(responseSpecMock.bodyToMono(
                new ParameterizedTypeReference<List<TimeDTO>>() {
                })).thenReturn(
                        Mono.delay(Duration.ofMillis(50))
                                .then(Mono.just(mockResponse)));

        // Initialize AdminModelImpl with the mocked WebClient
        adminModel = new AdminModelImpl(webClientMock);
    }

    @Test 
    public void testGetParticipantTimes() {
            
    }

    @Test 
    public void testStartCompetition() {
        int competitors = 10;
        int stations = 3;

        adminModel.startCompetition(competitors, stations);

        assertEquals(adminModel.getNbrCompetitors(), 10);
        assertEquals(adminModel.getNbrStations(), 10);

    }
 

}
