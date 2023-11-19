package edu.homework6.stream_composition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Композиция OutputStream'ов.")
public class StreamCompositionTest {
    private static final Logger LOGGER = LogManager.getLogger();

    @Test
    @DisplayName("Композиция.")
    void compose() {
        // given
        Path path = Path.of(StreamComposition.FILE_PATH);
        StreamComposition.compose();
        try (FileReader fileReader = new FileReader(path.toFile());
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            // when
            String quote = bufferedReader.readLine();

            // then
            assertThat(path.toFile().exists()).isTrue();
            assertThat(quote).isEqualTo(StreamComposition.QUOTE);
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        } finally {
            StreamComposition.deleteFile();
        }
    }
}
