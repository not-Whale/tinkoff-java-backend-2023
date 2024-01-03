package edu.project3.readers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Чтение логов из файла.")
public class LogFileReaderTest {
    private static final String path = "src/test/resources/project3/source.txt";

    private static final String globPath = "src/**/source.txt";

    private static final String incorrectPath = "!src\\~a.txt /a/ txt .a";

    @Test
    @DisplayName("Чтение из файла.")
    void readFile() {
        // given
        Reader reader = new LogFileReader(path);

        // when
        assertThat(reader.canRead()).isTrue();
        String[] logs = reader.read();

        // then
        assertThat(logs).isNotNull().isNotEmpty().containsOnly(
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            "93.180.71.3 - - [17/May/2015:08:05:23 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            "80.91.33.133 - - [17/May/2015:08:05:24 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)\"",
            "217.168.17.5 - - [17/May/2015:08:05:34 +0000] \"GET /downloads/product_1 HTTP/1.1\" 200 490 \"-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\"",
            "217.168.17.5 - - [17/May/2015:08:05:09 +0000] \"GET /downloads/product_2 HTTP/1.1\" 200 490 \"-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\"",
            "93.180.71.3 - - [17/May/2015:08:05:57 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            "217.168.17.5 - - [17/May/2015:08:05:02 +0000] \"GET /downloads/product_2 HTTP/1.1\" 404 337 \"-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\"",
            "217.168.17.5 - - [17/May/2015:08:05:42 +0000] \"GET /downloads/product_1 HTTP/1.1\" 404 332 \"-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\"",
            "80.91.33.133 - - [17/May/2015:08:05:01 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)\"",
            "93.180.71.3 - - [17/May/2015:08:05:27 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            "217.168.17.5 - - [17/May/2015:08:05:12 +0000] \"GET /downloads/product_2 HTTP/1.1\" 200 3316 \"-\" \"-\""
        );
    }

    @Test
    @DisplayName("Чтение из файла по glob-пути.")
    void readGlobPath() {
        // given
        Reader reader = new LogFileReader(globPath);

        // when
        assertThat(reader.canRead()).isTrue();
        String[] logs = reader.read();

        // then
        assertThat(logs).isNotNull().isNotEmpty().containsOnly(
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            "93.180.71.3 - - [17/May/2015:08:05:23 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            "80.91.33.133 - - [17/May/2015:08:05:24 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)\"",
            "217.168.17.5 - - [17/May/2015:08:05:34 +0000] \"GET /downloads/product_1 HTTP/1.1\" 200 490 \"-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\"",
            "217.168.17.5 - - [17/May/2015:08:05:09 +0000] \"GET /downloads/product_2 HTTP/1.1\" 200 490 \"-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\"",
            "93.180.71.3 - - [17/May/2015:08:05:57 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            "217.168.17.5 - - [17/May/2015:08:05:02 +0000] \"GET /downloads/product_2 HTTP/1.1\" 404 337 \"-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\"",
            "217.168.17.5 - - [17/May/2015:08:05:42 +0000] \"GET /downloads/product_1 HTTP/1.1\" 404 332 \"-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\"",
            "80.91.33.133 - - [17/May/2015:08:05:01 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)\"",
            "93.180.71.3 - - [17/May/2015:08:05:27 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            "217.168.17.5 - - [17/May/2015:08:05:12 +0000] \"GET /downloads/product_2 HTTP/1.1\" 200 3316 \"-\" \"-\""
        );
    }

    @Test
    @DisplayName("Неправильный формат пути.")
    void readIncorrectPath() {
        // given
        Reader reader = new LogFileReader(incorrectPath);

        // when
        assertThat(reader.canRead()).isFalse();
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            reader::read
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).hasMessage("Incorrect path to read from!");
    }

    @Test
    @DisplayName("На вход вместо пути передан null.")
    void readNullPath() {
        // given
        String nullPath = null;

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> new LogFileReader(nullPath)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).hasMessage("Path must not be null!");
    }
}
