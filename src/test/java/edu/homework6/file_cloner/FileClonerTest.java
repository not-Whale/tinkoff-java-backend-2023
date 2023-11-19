package edu.homework6.file_cloner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Копирование файлов как в Windows.")
public class FileClonerTest {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String TINKOFF_BANK_BIGGEST_SECRET_FILE_PATH =
        "src/main/resources/file_cloner/Tinkoff Bank Biggest Secret.txt";

    private static final String TINKOFF_BANK_BIGGEST_SECRET_FIRST_COPY_FILE_PATH =
        "src/main/resources/file_cloner/Tinkoff Bank Biggest Secret - копия 1.txt";

    private static final String TINKOFF_BANK_BIGGEST_SECRET_SECOND_COPY_FILE_PATH =
        "src/main/resources/file_cloner/Tinkoff Bank Biggest Secret - копия 2.txt";

    @Test
    @DisplayName("Копирование файла.")
    void cloneFile() {
        // given
        Path file = Path.of(TINKOFF_BANK_BIGGEST_SECRET_FILE_PATH);
        Path copy = Path.of(TINKOFF_BANK_BIGGEST_SECRET_FIRST_COPY_FILE_PATH);

        // when
        FileCloner.cloneFile(file);
        try (FileReader fileReader = new FileReader(file.toFile());
             FileReader copyReader = new FileReader(copy.toFile());
             BufferedReader bufferedFileReader = new BufferedReader(fileReader);
             BufferedReader bufferedCopyReader = new BufferedReader(copyReader)) {
            // when
            String fileQuote = bufferedFileReader.readLine();
            String copyQuote = bufferedCopyReader.readLine();

            // then
            assertThat(file.toFile().exists()).isTrue();
            assertThat(copy.toFile().exists()).isTrue();
            assertThat(fileQuote).isEqualTo(copyQuote);
            assertThat(file.getParent().toFile().listFiles())
                .isNotNull()
                .isNotEmpty()
                .containsOnly(
                    copy.toFile(),
                    file.toFile()
                );

            Files.delete(copy);
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Копирование файла дважды.")
    void cloneFileTwice() {
        // given
        Path file = Path.of(TINKOFF_BANK_BIGGEST_SECRET_FILE_PATH);
        Path copy = Path.of(TINKOFF_BANK_BIGGEST_SECRET_FIRST_COPY_FILE_PATH);
        Path secondCopy = Path.of(TINKOFF_BANK_BIGGEST_SECRET_SECOND_COPY_FILE_PATH);

        // when
        FileCloner.cloneFile(file);
        try (FileReader fileReader = new FileReader(file.toFile());
             FileReader copyReader = new FileReader(copy.toFile());
             FileReader secondCopyReader = new FileReader(copy.toFile());
             BufferedReader bufferedFileReader = new BufferedReader(fileReader);
             BufferedReader bufferedCopyReader = new BufferedReader(copyReader);
             BufferedReader bufferedSecondCopyReader = new BufferedReader(secondCopyReader)){
            // when
            String fileQuote = bufferedFileReader.readLine();
            String copyQuote = bufferedCopyReader.readLine();
            String secondCopyQuote = bufferedSecondCopyReader.readLine();

            // then
            assertThat(file.toFile().exists()).isTrue();
            assertThat(copy.toFile().exists()).isTrue();
            assertThat(secondCopy.toFile().exists()).isTrue();
            assertThat(fileQuote).isEqualTo(copyQuote);
            assertThat(fileQuote).isEqualTo(secondCopyQuote);
            assertThat(file.getParent().toFile().listFiles())
                .isNotNull()
                .isNotEmpty()
                .containsOnly(
                    secondCopy.toFile(),
                    copy.toFile(),
                    file.toFile()
                );

            Files.delete(copy);
            Files.delete(secondCopy);
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }
}
