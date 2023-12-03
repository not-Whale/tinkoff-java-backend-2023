package edu.homework6.directory_stream_filter;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Фильтры-предикаты.")
public class DirectoryStreamFiltersTest {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String DIR_PATH = "src/main/resources/directory_stream_filter/dir";

    private static final String FILE_PATH = "src/main/resources/directory_stream_filter/file1.txt";

    private static final String NESTED_FILE_PATH = "src/main/resources/directory_stream_filter/dir/file2.txt";

    private static final String IMG_PATH = "src/main/resources/directory_stream_filter/dog.jpg";

    @Test
    @DisplayName("Проверка на директорию.")
    void isDirectory() {
        // given
        Path path = Path.of(DIR_PATH);

        // when
        try (DirectoryStream<Path> entries =
                 Files.newDirectoryStream(path.getParent(), DirectoryStreamFilters.isDirectory())) {
            List<Path> files = new ArrayList<>();
            entries.forEach(files::add);

            // then
            assertThat(files).isNotEmpty().containsOnly(path);
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Проверка на файл.")
    void isRegularFile() {
        // given
        Path file = Path.of(FILE_PATH);
        Path img = Path.of(IMG_PATH);

        // when
        try (DirectoryStream<Path> entries =
                 Files.newDirectoryStream(file.getParent(), DirectoryStreamFilters.isRegularFile())) {
            List<Path> files = new ArrayList<>();
            entries.forEach(files::add);

            // then
            assertThat(files).isNotEmpty().containsOnly(file, img);
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Проверка на файлы с записью.")
    void isReadable() {
        // given
        Path dir = Path.of(DIR_PATH);
        Path file = Path.of(FILE_PATH);
        Path img = Path.of(IMG_PATH);

        // when
        try (DirectoryStream<Path> entries =
                 Files.newDirectoryStream(file.getParent(), DirectoryStreamFilters.isReadable())) {
            List<Path> files = new ArrayList<>();
            entries.forEach(files::add);

            // then
            assertThat(files).isNotEmpty().containsOnly(file, dir, img);
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Проверка на файлы с чтением.")
    void isWritable() {
        // given
        Path dir = Path.of(DIR_PATH);
        Path file = Path.of(FILE_PATH);
        Path img = Path.of(IMG_PATH);

        // when
        try (DirectoryStream<Path> entries =
                 Files.newDirectoryStream(file.getParent(), DirectoryStreamFilters.isWritable())) {
            List<Path> files = new ArrayList<>();
            entries.forEach(files::add);

            // then
            assertThat(files).isNotEmpty().containsOnly(file, dir, img);
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Проверка на неисполняемые файлы с использованием отрицания.")
    void isExecutableNotExecWithNegateFilter() {
        // given
        Path dir = Path.of(DIR_PATH);
        Path file = Path.of(FILE_PATH);
        Path img = Path.of(IMG_PATH);

        // when
        try (DirectoryStream<Path> entries =
                 Files.newDirectoryStream(file.getParent(), AbstractFilter.not(DirectoryStreamFilters.isExecutable()))) {
            List<Path> files = new ArrayList<>();
            entries.forEach(files::add);

            // then
            assertThat(files).isNotEmpty().containsOnly(file, img);
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Проверка на размер файла с использованием конъюнкции.")
    void isLargerAndSmallerThan() {
        // given
        Path img = Path.of(IMG_PATH);

        // when
        try (DirectoryStream<Path> entries =
                 Files.newDirectoryStream(
                     img.getParent(),
                     DirectoryStreamFilters.largerThan(10_000)
                         .and(DirectoryStreamFilters.smallerThan(50_000))
                 )) {
            List<Path> files = new ArrayList<>();
            entries.forEach(files::add);

            // then
            assertThat(files).isNotEmpty().containsOnly(img);
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Проверка на размер файла.")
    void hasSize() {
        // given
        Path file = Path.of(FILE_PATH);

        // when
        try (DirectoryStream<Path> entries =
                 Files.newDirectoryStream(file.getParent(), DirectoryStreamFilters.hasSize(90))) {
            List<Path> files = new ArrayList<>();
            entries.forEach(files::add);

            // then
            assertThat(files).isNotEmpty().containsOnly(file);
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Проверка на расширение файла.")
    void hasExtension() {
        // given
        Path file = Path.of(FILE_PATH);

        // when
        try (DirectoryStream<Path> entries =
                 Files.newDirectoryStream(file.getParent(), DirectoryStreamFilters.hasExtension("txt"))) {
            List<Path> files = new ArrayList<>();
            entries.forEach(files::add);

            // then
            assertThat(files).isNotEmpty().containsOnly(file);
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Проверка имени по регулярному выражению.")
    void nameMatchesRegex() {
        // given
        Path img = Path.of(IMG_PATH);

        // when
        try (DirectoryStream<Path> entries =
                 Files.newDirectoryStream(img.getParent(), DirectoryStreamFilters.nameMatches(".*\\.jpg"))) {
            List<Path> files = new ArrayList<>();
            entries.forEach(files::add);

            // then
            assertThat(files).isNotEmpty().containsOnly(img);
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Проверка пути по регулярному выражению.")
    void pathMatchesRegex() {
        // given
        Path file = Path.of(NESTED_FILE_PATH);

        // when
        try (DirectoryStream<Path> entries =
                 Files.newDirectoryStream(
                     file.getParent(),
                     DirectoryStreamFilters.pathMatches(".*/dir/.*"))) {
            List<Path> files = new ArrayList<>();
            entries.forEach(files::add);

            // then
            assertThat(files).isNotEmpty().containsOnly(file);
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Проверка magic numbers.")
    void magicNumbers() {
        // given
        Path img = Path.of(IMG_PATH);

        // when
        try (DirectoryStream<Path> entries =
                 Files.newDirectoryStream(
                     img.getParent(),
                     DirectoryStreamFilters.magicNumbers(0xFF, 0xD8, 0xFF, 0xE0, 0x00))) {
            List<Path> files = new ArrayList<>();
            entries.forEach(files::add);

            // then
            assertThat(files).isNotEmpty().containsOnly(img);
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Проверка дизъюнкции.")
    void readableOrHidden() {
        // given
        Path dir = Path.of(DIR_PATH);
        Path file = Path.of(FILE_PATH);
        Path img = Path.of(IMG_PATH);

        // when
        try (DirectoryStream<Path> entries =
                 Files.newDirectoryStream(
                     file.getParent(),
                     DirectoryStreamFilters.isReadable().or(DirectoryStreamFilters.isHidden()))) {
            List<Path> files = new ArrayList<>();
            entries.forEach(files::add);

            // then
            assertThat(files).isNotEmpty().containsOnly(file, dir, img);
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Цепочка из нескольких логических композиций.")
    void composePredicates() {
        // given
        Path dir = Path.of(DIR_PATH);
        Path file = Path.of(FILE_PATH);
        Path img = Path.of(IMG_PATH);

        // when
        try (DirectoryStream<Path> entries =
                 Files.newDirectoryStream(
                     file.getParent(),
                     DirectoryStreamFilters.isRegularFile().negate()
                         .or(DirectoryStreamFilters.smallerThan(50_000)
                             .and(DirectoryStreamFilters.magicNumbers(0xFF, 0xD8, 0xFF, 0xE0, 0x00))))) {
            List<Path> files = new ArrayList<>();
            entries.forEach(files::add);

            // then
            assertThat(files).isNotEmpty().containsOnly(dir, img);
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }

    @Test
    @DisplayName("Цепочка из нескольких and.")
    void composeAnd() {
        // given
        Path file = Path.of(FILE_PATH);
        Path img = Path.of(IMG_PATH);

        // when
        try (DirectoryStream<Path> entries =
                 Files.newDirectoryStream(
                     file.getParent(),
                     DirectoryStreamFilters.isRegularFile()
                         .and(DirectoryStreamFilters.isReadable())
                         .and(DirectoryStreamFilters.largerThan(100))
                         .and(DirectoryStreamFilters.nameMatches("[^0-9].*"))
                         .and(DirectoryStreamFilters.hasExtension("jpg"))
                         .and(DirectoryStreamFilters.magicNumbers(0xFF, 0xD8, 0xFF, 0xE0, 0x00)))) {
            List<Path> files = new ArrayList<>();
            entries.forEach(files::add);

            // then
            assertThat(files).isNotEmpty().containsOnly(img);
        } catch (IOException e) {
            LOGGER.info("Упс... Что-то пошло не так! " + e);
        }
    }
}
