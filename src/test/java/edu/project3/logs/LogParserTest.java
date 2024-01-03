package edu.project3.logs;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Парсинг лога из строки с представлением в виде record.")
public class LogParserTest {
    @Test
    @DisplayName("Парсинг.")
    void parseSuccess() {
        // given
        String logString = "93.180.71.3 - - [17/May/2015:08:05:27 +0000] "
            + "\"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" "
            + "\"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"";

        // when
        Log logRecord = LogParser.parse(logString);

        // then
        assertThat(logRecord).isNotNull().isEqualTo(new Log(
            "93.180.71.3",
            null,
            ZonedDateTime.of(
                LocalDateTime.of(2015, Month.MAY, 17, 8, 5, 27),
                ZoneId.of("Z")
            ),
            RequestType.GET,
            "/downloads/product_1",
            "HTTP/1.1",
            304,
            0L,
            null,
            "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
        ));
    }

    @Test
    @DisplayName("На вход вместо лога передан null.")
    void parseNullLogString() {
        // given
        String logString = null;

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> LogParser.parse(logString)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).hasMessage("Log string must not be null!");
    }
}
