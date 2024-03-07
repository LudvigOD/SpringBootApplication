import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * This class is just here to experiment with how testing works in Spring.
 */
// @SpringBootTest
public class SpringTest {

    @Test
    public void test() {
        System.out.println("Hello, Spring!");
        assertEquals(1, 1);
    }
}
