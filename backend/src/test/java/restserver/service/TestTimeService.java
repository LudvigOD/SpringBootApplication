package restserver.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import restserver.entity.Time;
import restserver.repository.TimeRepository;

@SpringBootTest
public class TestTimeService {

    @Mock
    private TimeRepository timeRepository;

    @InjectMocks
    private TimeService timeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTimesForStartNbr() {
        // setting up - pretending we're looking up start number "01"
        String startNbr = "01";
        // making some fake Time objs
        Time[] mockTimes = new Time[] {
                new Time(startNbr, "10:00:00", "A"),
                new Time(startNbr, "10:00:01", "B"),
                new Time(startNbr, "10:00:02", "C")
        };
        // telling the mock repository:
        // when findByStartNbr is called with "01", return our fake times
        when(timeRepository.findByStartNbr(startNbr)).thenReturn(Arrays.asList(mockTimes));

        // running the actual method we wanna test
        List<Time> result = timeService.getTimesForStartNbr(startNbr);

        // bunch of checks to make sure we got back what we expected
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockTimes.length, result.size());
        assertArrayEquals(mockTimes, result.toArray());

        // making sure the repo method findByStartNbr was called with the right argument
        verify(timeRepository).findByStartNbr(startNbr);
    }

    @Test
    public void testRegisterTime() {
        // setup with a startNbr "02" and time "11:00:00" and station A
        String startNbr = "02";
        String time = "11:00:00";
        String station = "A";
        // fake Time obj, that will be saved to the mock repository
        Time timeEntity = new Time(startNbr, time, station);
        // mock repo save method, returns our fake timeEntity
        when(timeRepository.save(any(Time.class))).thenReturn(timeEntity);

        // running the method we want to test
        Time result = timeService.registerTime(startNbr, time, station);

        // result shouldn't be null, startNbr and time should match what we set up
        assertNotNull(result);
        assertEquals(startNbr, result.getStartNbr());
        assertEquals(time, result.getTime());
        assertEquals(station, result.getStation());
        // making sure save was called on the repo
        verify(timeRepository).save(any(Time.class));
    }

}
