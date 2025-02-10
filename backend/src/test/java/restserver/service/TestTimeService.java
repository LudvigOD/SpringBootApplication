package restserver.service;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
    public void testRegisterTime() {
        Time timeEntity = new Time(1, 1, "02", Instant.ofEpochSecond(123));
        when(timeRepository.save(any(Time.class))).thenReturn(timeEntity);

        Time result = timeService.registerTime(1, 1, "02", Instant.ofEpochSecond(123));

        assertNotNull(result);
        assertEquals(result.getStartNbr(), "02");
        assertEquals(result.getTime(), Instant.ofEpochSecond(123));

        verify(timeRepository).save(any(Time.class));
    }

    @Test
    public void testRegisterTimePadStartNbr() {
        Time timeEntity = new Time(1, 1, "02", Instant.ofEpochSecond(123));
        when(timeRepository.save(any(Time.class))).thenReturn(timeEntity);

        Time result = timeService.registerTime(1, 1, "2", Instant.ofEpochSecond(123));

        assertNotNull(result);
        assertEquals(result.getStartNbr(), "02");
        assertEquals(result.getTime(), Instant.ofEpochSecond(123));

        verify(timeRepository).save(any(Time.class));
    }

    @Test
    public void testRegisterTimeTrimStartNbr() {
        Time timeEntity = new Time(1, 1, "02", Instant.ofEpochSecond(123));
        when(timeRepository.save(any(Time.class))).thenReturn(timeEntity);

        Time result = timeService.registerTime(1, 1, "002", Instant.ofEpochSecond(123));

        assertNotNull(result);
        assertEquals(result.getStartNbr(), "02");
        assertEquals(result.getTime(), Instant.ofEpochSecond(123));

        verify(timeRepository).save(any(Time.class));
    }

    @Test
    public void testRegisterTimeTrimOnlyLeadingZerosStartNbr() {
        Time timeEntity = new Time(1, 1, "1002", Instant.ofEpochSecond(123));
        when(timeRepository.save(any(Time.class))).thenReturn(timeEntity);

        Time result = timeService.registerTime(1, 1, "1002", Instant.ofEpochSecond(123));

        assertNotNull(result);
        assertEquals(result.getStartNbr(), "1002");
        assertEquals(result.getTime(), Instant.ofEpochSecond(123));

        verify(timeRepository).save(any(Time.class));
    }

    @Test
    public void testGetAllTimes() {
        Time[] mockTimes = new Time[] {
                new Time(1, 1, "01", Instant.ofEpochSecond(123)),
        };
        when(timeRepository.findByRaceId(1)).thenReturn(Arrays.asList(mockTimes));

        List<Time> result = timeService.getAllTimes(1, Optional.empty(), Optional.empty());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockTimes.length, result.size());
        assertArrayEquals(mockTimes, result.toArray());

        verify(timeRepository).findByRaceId(1);
    }

    @Test
    public void testGetAllTimesStationId() {
        Time[] mockTimes = new Time[] {
                new Time(1, 1, "01", Instant.ofEpochSecond(123)),
        };
        when(timeRepository.findByRaceIdAndStationId(1, 1)).thenReturn(Arrays.asList(mockTimes));

        List<Time> result = timeService.getAllTimes(1, Optional.of(1), Optional.empty());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockTimes.length, result.size());
        assertArrayEquals(mockTimes, result.toArray());

        verify(timeRepository).findByRaceIdAndStationId(1, 1);
    }

    @Test
    public void testGetAllTimesStartNbr() {
        Time[] mockTimes = new Time[] {
                new Time(1, 1, "01", Instant.ofEpochSecond(123)),
        };
        when(timeRepository.findByRaceIdAndStartNbr(1, "01")).thenReturn(Arrays.asList(mockTimes));

        List<Time> result = timeService.getAllTimes(1, Optional.empty(), Optional.of("01"));

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockTimes.length, result.size());
        assertArrayEquals(mockTimes, result.toArray());

        verify(timeRepository).findByRaceIdAndStartNbr(1, "01");
    }

    @Test
    public void testGetAllTimesStationIdAndStartNbr() {
        Time[] mockTimes = new Time[] {
                new Time(1, 1, "01", Instant.ofEpochSecond(123)),
        };
        when(timeRepository.findByRaceIdAndStationIdAndStartNbr(1, 1, "01")).thenReturn(Arrays.asList(mockTimes));

        List<Time> result = timeService.getAllTimes(1, Optional.of(1), Optional.of("01"));

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockTimes.length, result.size());
        assertArrayEquals(mockTimes, result.toArray());

        verify(timeRepository).findByRaceIdAndStationIdAndStartNbr(1, 1, "01");
    }

    @Test
    public void testGetAllTimesEmpty() {
        Time[] mockTimes = new Time[] {
                new Time(1, 1, "01", Instant.ofEpochSecond(123)),
        };
        when(timeRepository.findByRaceId(1)).thenReturn(Arrays.asList(mockTimes));

        List<Time> result = timeService.getAllTimes(1, Optional.empty(), Optional.empty());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockTimes.length, result.size());
        assertArrayEquals(mockTimes, result.toArray());

        verify(timeRepository).findByRaceId(1);
    }

    @Test
    public void testGetAllTimesEmptyStationId() {
        Time[] mockTimes = new Time[] {
                new Time(1, 1, "01", Instant.ofEpochSecond(123)),
        };
        when(timeRepository.findByRaceId(1)).thenReturn(Arrays.asList(mockTimes));

        List<Time> result = timeService.getAllTimes(1, Optional.empty(), Optional.empty());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockTimes.length, result.size());
        assertArrayEquals(mockTimes, result.toArray());

        verify(timeRepository).findByRaceId(1);
    }

    @Test
    public void testGetAllTimesEmptyStartNbr() {
        Time[] mockTimes = new Time[] {
                new Time(1, 1, "01", Instant.ofEpochSecond(123)),
        };
        when(timeRepository.findByRaceId(1)).thenReturn(Arrays.asList(mockTimes));

        List<Time> result = timeService.getAllTimes(1, Optional.empty(), Optional.empty());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockTimes.length, result.size());
        assertArrayEquals(mockTimes, result.toArray());

        verify(timeRepository).findByRaceId(1);
    }

    @Test
    public void testGetAllTimesEmptyStationIdAndStartNbr() {
        Time[] mockTimes = new Time[] {
                new Time(1, 1, "01", Instant.ofEpochSecond(123)),
        };
        when(timeRepository.findByRaceId(1)).thenReturn(Arrays.asList(mockTimes));

        List<Time> result = timeService.getAllTimes(1, Optional.empty(), Optional.empty());

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(mockTimes.length, result.size());
        assertArrayEquals(mockTimes, result.toArray());

        verify(timeRepository).findByRaceId(1);
    }

    @Test
    public void testUpdateTimeEntity() {
        Time firstEntity = new Time(1, 1, "1002", Instant.ofEpochSecond(123));
        firstEntity.setId(1L);
    
        when(timeRepository.findById(1L)).thenReturn(Optional.of(firstEntity));
        when(timeRepository.save(any(Time.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Ensure save returns the modified entity
    
        // Act: Update the entity
        timeService.updateTimeEntity(1, 2, "1015", Instant.ofEpochSecond(231), 1L);
    
        // Assert: Ensure values were updated
        verify(timeRepository).save(any(Time.class)); // Ensure save() was called
        assertEquals(2, firstEntity.getStationId());
        assertEquals("1015", firstEntity.getStartNbr());
        assertEquals(Instant.ofEpochSecond(231), firstEntity.getTime());
    }

    @Test
    public void testUpdateTimeEntityNoEntity() {
        when(timeRepository.findById(1L)).thenReturn(Optional.empty());
    
        // Act: Update the entity
        timeService.updateTimeEntity(1, 2, "1015", Instant.ofEpochSecond(231), 1L);
    
        // Assert: Ensure values were not updated
        verify(timeRepository, never()).save(any(Time.class)); // Ensure save() was not called
    }
}
