package edu.project3.writers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Запись отчетов в файл.")
public class ReportFileWriterTest {
    private static final String pathString = "src/test/resources/project3/report.txt";

    private static final String report = "Some info...";

    @Test
    @DisplayName("Запись.")
    void write() {
        // given
        ReportFileWriter writer = new ReportFileWriter(pathString);
        File file = Path.of(pathString).toFile();

        // when
        assertDoesNotThrow(() -> {
            writer.write(report);
            writer.write(report);
        });

        // then
        assertThat(file).exists().isFile();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String read = reader.readLine();
            assertThat(read).isEqualTo(report + report);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // cleaning
        file.delete();
    }

    @Test
    @DisplayName("Вместо пути передан null.")
    void writeNullPath() {
        // given
        String nullPath = null;

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> new ReportFileWriter(nullPath)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).hasMessage("Path must not be null!");
    }
}
