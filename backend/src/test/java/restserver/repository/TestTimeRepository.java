package restserver.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import restserver.entity.Time;

@DataJpaTest
public class TestTimeRepository {
    @Autowired
    private TimeRepository timeRepository;

    @Test
    public void testFindByRaceId() {
        timeRepository.save(new Time(1, 1, "01", Instant.ofEpochSecond(123)));
        timeRepository.save(new Time(1, 1, "02", Instant.ofEpochSecond(456)));
        timeRepository.save(new Time(2, 1, "03", Instant.ofEpochSecond(789)));

        List<Time> results = timeRepository.findByRaceId(1);

        assertThat(results).hasSize(2);
        assertThat(results.get(0).getRaceId()).isEqualTo(1);
        assertThat(results.get(1).getRaceId()).isEqualTo(1);
    }

    @Test
    public void testFindByRaceIdAndStationId() {
        timeRepository.save(new Time(1, 1, "01", Instant.ofEpochSecond(123)));
        timeRepository.save(new Time(1, 1, "02", Instant.ofEpochSecond(456)));
        timeRepository.save(new Time(1, 2, "03", Instant.ofEpochSecond(789)));
        timeRepository.save(new Time(2, 1, "04", Instant.ofEpochSecond(789)));

        List<Time> results = timeRepository.findByRaceIdAndStationId(1, 1);

        assertThat(results).hasSize(2);
        assertThat(results.get(0).getRaceId()).isEqualTo(1);
        assertThat(results.get(0).getStartNbr()).isEqualTo("01");
        assertThat(results.get(1).getRaceId()).isEqualTo(1);
        assertThat(results.get(1).getStartNbr()).isEqualTo("02");
    }

    @Test
    public void testFindByRaceIdAndStartNbr() {
        timeRepository.save(new Time(1, 1, "01", Instant.ofEpochSecond(123)));
        timeRepository.save(new Time(1, 1, "02", Instant.ofEpochSecond(456)));
        timeRepository.save(new Time(2, 1, "01", Instant.ofEpochSecond(789)));
        timeRepository.save(new Time(2, 2, "01", Instant.ofEpochSecond(234)));

        List<Time> results = timeRepository.findByRaceIdAndStartNbr(2, "01");

        assertThat(results).hasSize(2);
        assertThat(results.get(0).getRaceId()).isEqualTo(2);
        assertThat(results.get(0).getStartNbr()).isEqualTo("01");
        assertThat(results.get(1).getRaceId()).isEqualTo(2);
        assertThat(results.get(1).getStartNbr()).isEqualTo("01");
    }

    @Test
    public void testFindByRaceIdAndStationIdAndStartNbr() {
        timeRepository.save(new Time(1, 1, "01", Instant.ofEpochSecond(123)));
        timeRepository.save(new Time(1, 1, "02", Instant.ofEpochSecond(456)));
        timeRepository.save(new Time(2, 1, "01", Instant.ofEpochSecond(789)));
        timeRepository.save(new Time(2, 2, "03", Instant.ofEpochSecond(789)));

        List<Time> results = timeRepository.findByRaceIdAndStationIdAndStartNbr(2, 2, "03");

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getRaceId()).isEqualTo(2);
        assertThat(results.get(0).getStationId()).isEqualTo(2);
        assertThat(results.get(0).getStartNbr()).isEqualTo("03");
    }
}
