package utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import shared.Utils;

public class TestUtils {
    @Nested
    @DisplayName("formatInstant")
    class TestFormatInstant {
        @Test
        @DisplayName("It should format an Instant to a string")
        void testFormatInstant() {
            Instant instant = Instant.parse("2021-01-01T12:34:56Z");

            String result = Utils.formatInstant(instant);

            assertEquals("12:34:56:0", result);
        }
    }

    @Nested
    @DisplayName("formatDuration")
    class TestFormatDuration {
        @Test
        @DisplayName("It should format a Duration to a string")
        void testFormatDuration() {
            long hours = 1;
            long minutes = 2;
            long seconds = 3;
            long millis = 4;
            long nanos = (hours * 60 * 60 * 1_000_000_000) + (minutes * 60 * 1_000_000_000) + (seconds * 1_000_000_000) + (millis * 1_000_000);

            String result = Utils.formatDuration(java.time.Duration.ofNanos(nanos));

            assertEquals("01:02:03.0", result);
        }
    }
}
