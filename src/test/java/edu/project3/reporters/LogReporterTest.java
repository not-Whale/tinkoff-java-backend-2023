package edu.project3.reporters;

import edu.project3.logs.Log;
import edu.project3.logs.LogParser;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.project3.logs.RequestType.GET;
import static edu.project3.logs.RequestType.HEAD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Создание отчетов по логам.")
public class LogReporterTest {
    private static final String[] logStrings = new String[] {
        "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
        "93.180.71.3 - - [17/May/2015:08:05:23 +0000] \"HEAD /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
        "80.91.33.133 - - [17/May/2015:08:05:24 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)\"",
        "217.168.17.5 - - [17/May/2015:08:05:34 +0000] \"GET /downloads/product_1 HTTP/1.1\" 200 490 \"-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\"",
        "217.168.17.5 - - [17/May/2015:08:05:09 +0000] \"GET /downloads/product_2 HTTP/1.1\" 200 490 \"-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\""
    };

    private static final LocalDateTime from =
        LocalDateTime.of(2015, Month.MAY, 17, 8, 5, 0);

    private static final LocalDateTime to =
        LocalDateTime.of(2015, Month.MAY, 17, 8, 5, 25);

    private static LogReporter logReporter;

    @BeforeAll
    public static void initLogReporterTest() {
        assertDoesNotThrow(() -> {
            Log[] logRecords = Arrays.stream(logStrings).map(LogParser::parse).toArray(Log[]::new);
            logReporter = LogReporter.of(logRecords, null, from, to);
        });
    }

    @Test
    @DisplayName("Основная информация.")
    void generalInfo() {
        // given
        // ---

        // when
        GeneralInfo generalInfo = logReporter.generalInfo();

        // then
        assertThat(generalInfo).isNotNull().isEqualTo(new GeneralInfo(
            "unknown",
            "17.05.2015 08:05:00",
            "17.05.2015 08:05:25",
            3L,
            163L
        ));
    }

    @Test
    @DisplayName("Самые популярные ресурсы.")
    void mostPopularResources() {
        // given
        // ---

        // when
        var report = logReporter.mostPopularResources();

        // then
        assertThat(report).isNotNull().isNotEmpty().containsOnly(
            Map.entry("/downloads/product_1", 2L),
            Map.entry("/downloads/product_2", 1L)
        );
    }

    @Test
    @DisplayName("Самые частые коды ответа.")
    void mostPopularResponseCodes() {
        // given
        // ---

        // when
        var report = logReporter.mostPopularResponseCodes();

        // then
        assertThat(report).isNotNull().isNotEmpty().containsOnly(
            Map.entry(304, 2L),
            Map.entry(200, 1L)
        );
    }

    @Test
    @DisplayName("Самые частые типы запросов.")
    void mostPopularRequestTypes() {
        // given
        // ---

        // when
        var report = logReporter.mostPopularRequestTypes();

        // then
        assertThat(report).isNotNull().isNotEmpty().containsOnly(
            Map.entry(GET, 2L),
            Map.entry(HEAD, 1L)
        );
    }

    @Test
    @DisplayName("Количество запросов определенного типа (HEAD) на ресурс.")
    void headRequestsPerResource() {
        // given
        // ---

        // when
        var report = logReporter.requestsPerResourceByType(HEAD);

        // then
        assertThat(report).isNotNull().isNotEmpty().containsOnly(
            Map.entry("/downloads/product_1", 1L)
        );
    }

    @Test
    @DisplayName("Наиболее стабильные ресурсы.")
    void mostStableResources() {
        // given
        // ---

        // when
        var report = logReporter.mostStableResources();

        // then
        assertThat(report).isNotNull().isNotEmpty().containsOnly(
            Map.entry("/downloads/product_2", 1.0),
            Map.entry("/downloads/product_1", 0.0)
        );
    }

    @Test
    @DisplayName("На вход вместо логов передан null.")
    void reportNullLogs() {
        // given
        Log[] nullLogs = null;

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> LogReporter.of(nullLogs, "Resource name", from, to)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).hasMessage("Logs must not be null!");
    }

    @Test
    @DisplayName("Без параметров from и to")
    void withOutFromTo() {
        // given
        Log[] logs = Arrays.stream(logStrings).map(LogParser::parse).toArray(Log[]::new);

        // when
        assertDoesNotThrow(() -> {
            LogReporter newReporter = LogReporter.of(logs, "name", null, null);
        });
    }
}
