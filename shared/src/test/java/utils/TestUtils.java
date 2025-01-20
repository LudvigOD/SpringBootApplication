package utils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import shared.Utils;

public class TestUtils {

    /*
     * Just added some simple placeholder tests, for now. Gradle actually complains
     * if no tests are run, so I added these to get rid of the warning.
     */

    @Test
    public void testCreateUtil() { assertDoesNotThrow(() -> new Utils()); }

    @Test
    public void testHelper() { assertEquals("the helper", new Utils().helper()); }

}
