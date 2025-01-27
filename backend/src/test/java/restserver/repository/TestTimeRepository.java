package restserver.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import restserver.entity.Time;

@DataJpaTest
public class TestTimeRepository {

    /*
     * Ok, so here's my understanding of testing repositories:
     * - first off, we're not testing the interface itself, because that's just
     * Spring doing its magic
     * - but, we gotta make sure the stuff we think happens in the database,
     * actually does. I.e., "does this query even work?"
     * 
     * Why bother testing interfaces?
     * - Spring is doing a lot of magic for us, dynamically creating real
     * implementations of the repository interface, and we want to test that it
     * works as expected
     * - If we ever need to write custom queries (like SQL queries) we want to make
     * sure they actually fetch what we think they do
     * - Same for the named methods that automatically become queries (e.g.
     * findByStartNbr). We need test those that they work as expected. 'cause typos
     * and logic errors, they happen 🤷‍♂️
     * - plus, mapping entities to tables in the db requires that we use the same
     * names everywhere, so it's easy to mess up e.g. relations or column names
     * 
     * How it works?
     * - Apparently, we can use @DataJpaTest annotation to pretend we're talking to
     * a real db, but it's all in-memory and fast
     * - save some stuff, fetch it, see if it comes back right. If it does, we're
     * good!
     */

    @Autowired
    private TimeRepository timeRepository;

    @Test
    public void findByStartNbr_ShouldReturnTimes() {
        // Setup data for testing
        Time time1 = new Time("01", "10:00:00", "A");
        Time time2 = new Time("02", "10:05:00", "B");
        Time time3 = new Time("01", "10:10:00", "C");
        timeRepository.save(time1);
        timeRepository.save(time2);
        timeRepository.save(time3);

        // Run the method we want to test
        List<Time> results1 = timeRepository.findByStartNbr("01");
        List<Time> results2 = timeRepository.findByStartNbr("02");

        // Verify the results
        assertThat(results1).hasSize(2);
        assertThat(results1.get(0).getStartNbr()).isEqualTo("01");
        assertThat(results1.get(0).getTime()).isEqualTo("10:00:00");
        assertThat(results1.get(0).getStation()).isEqualTo("A");

        assertThat(results1.get(1).getStartNbr()).isEqualTo("01");
        assertThat(results1.get(1).getTime()).isEqualTo("10:10:00");
        assertThat(results1.get(1).getStation()).isEqualTo("C");

        assertThat(results2).hasSize(1);
        assertThat(results2.get(0).getStartNbr()).isEqualTo("02");
        assertThat(results2.get(0).getTime()).isEqualTo("10:05:00");
        assertThat(results2.get(0).getStation()).isEqualTo("B");
    }
}
