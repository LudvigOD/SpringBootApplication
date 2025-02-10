package register.model;

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
import java.util.function.Function;

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
import shared.dto.TimeDTO;

public class TestRegisterModel {

        // Notes: For testing client-server communication, we can use a Mock WebServer.
        // There seem to be libraries for doing this, like OkHttp and WireMock.
        // However, Spring also has built-in mocking capabilities for WebClients. I will
        // experiment with this for a bit.

        private RegisterModelImpl regModel;
        private WebClient webClientMock;
        private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;
        private WebClient.RequestHeadersSpec requestHeadersSpecMock;
        private WebClient.ResponseSpec responseSpecMock;

        @BeforeEach
        void setUp() throws Exception {
                // Mock WebClient and its method chain, i.e. the same chain that is used in the
                // actual implementation in RegisterModelImpl.
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
                List<TimeDTO> mockResponse = List.of(new TimeDTO(1, "01", Instant.ofEpochSecond(123)));

                // Setup the mock to return a response with our JSON body
                when(responseSpecMock.bodyToMono(
                                new ParameterizedTypeReference<List<TimeDTO>>() {
                                })).thenReturn(
                                                Mono.delay(Duration.ofMillis(50))
                                                                .then(Mono.just(mockResponse)));

                // Initialize RegisterModelImpl with the mocked WebClient
                regModel = new RegisterModelImpl(webClientMock);
        }

        @Test
        public void test() {
                System.out.println("Hello, Spring!");
                assertEquals(1, 1);
        }

        @Test
        void testSendNonBlockingGetRequest() throws InterruptedException {
                // Since the method is non-blocking, this test needs to wait for the response.
                // We can use Java's built-in "latch", to wait for the response handler to be
                // called.
                CountDownLatch latch = new CountDownLatch(1);

                // Use a lambda to define the response handler Consumer
                Consumer<List<TimeDTO>> responseHandler = response -> {
                        // Perform assertions on the response
                        assertNotNull(response);
                        assertFalse(response.isEmpty());
                        assertEquals("01", response.get(0).getStartNbr());
                        System.out.println("Response handler was called!");

                        // Count down the latch to signal that the response handler was called
                        latch.countDown();
                };

                // Call the method that we want to test, with our response handler
                regModel.asyncReloadTimes(responseHandler, 01);

                // Wait for the response handler to be called (or for the timeout to expire).
                // await returns true if the latch was counted down to zero, false otherwise.
                // For example, try to increase the wait time from 50 ms to 2 seconds in the
                // setup method and see what happens.
                assertTrue(latch.await(1, TimeUnit.SECONDS), "Response handler was not called in time!");

        }

        @Test
        public void testRegisterTime() {
                // Note to self: We should maybe move some of this setup to the setup method?

                // Mock the ExchangeFunction, which is basically a function that takes a
                // ClientRequest and returns a ClientResponse
                ExchangeFunction exchangeFunction = mock(ExchangeFunction.class);
                ClientResponse clientResponse = ClientResponse.create(HttpStatus.OK)
                                .header("Content-Type", "application/json")
                                .body("{}")
                                .build();
                when(exchangeFunction.exchange(any(ClientRequest.class)))
                                .thenReturn(Mono.just(clientResponse));

                // Create a WebClient with the mock ExchangeFunction
                WebClient mockWebClient = WebClient.builder()
                                .exchangeFunction(exchangeFunction)
                                .build();

                // Inject the mock WebClient into the model
                RegisterModelImpl instance = new RegisterModelImpl(mockWebClient);

                // Call the method under test
                instance.registerTime("01", 1);

                // Verify the interactions or state changes expected
                verify(exchangeFunction).exchange(any(ClientRequest.class));
                // Additional verifications or assertions as needed
        }

}
