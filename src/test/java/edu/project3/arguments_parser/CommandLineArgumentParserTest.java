package edu.project3.arguments_parser;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.project3.formatters.FormatType.MARKDOWN;
import static edu.project3.formatters.FormatType.STRING;
import static java.time.Month.AUGUST;
import static java.time.Month.SEPTEMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Парсинг аргументов коммандной строки.")
public class CommandLineArgumentParserTest {
    @Test
    @DisplayName("Все аргументы указаны.")
    void parseCorrectData() {
        // given
        String[] args = new String[] {
            "--path", "src/test/resources/project3/source.txt",
            "--from", "2023-08-15",
            "--to", "2023-09-15",
            "--format", "markdown",
            "--save", "src/test/resources/project3/report.md"
        };

        // when
        CommandLineArgumentParser parser = CommandLineArgumentParser.with(args);

        // then
        assertThat(parser.sourcePath()).isEqualTo("src/test/resources/project3/source.txt");
        assertThat(parser.from()).isEqualTo(LocalDate.of(2023, AUGUST, 15).atStartOfDay());
        assertThat(parser.to()).isEqualTo(LocalDate.of(2023, SEPTEMBER, 15).atStartOfDay());
        assertThat(parser.formatType()).isEqualTo(MARKDOWN);
        assertThat(parser.savePath()).isEqualTo("src/test/resources/project3/report.md");
    }

    @Test
    @DisplayName("Не указан аргумент path.")
    void withoutPathArg() {
        // given
        String[] args = new String[] {
            "--from", "2023-08-15",
            "--to", "2023-09-15",
            "--format", "markdown",
            "--save", "src/test/resources/project3/report.md",
        };

        // when
        Exception exception = assertThrows(
            RuntimeException.class,
            () -> CommandLineArgumentParser.with(args)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(RuntimeException.class);
        assertThat(exception).hasMessage("Arguments parsing failed. Reason: Missing required option: path");
    }

    @Test
    @DisplayName("Не указаны все аргументы, кроме path.")
    void withOnlyPathArg() {
        // given
        String[] args = new String[] {
            "--path", "src/test/resources/project3/source.txt"
        };

        // when
        CommandLineArgumentParser parser = CommandLineArgumentParser.with(args);

        // then
        assertThat(parser.sourcePath()).isEqualTo("src/test/resources/project3/source.txt");
        assertThat(parser.from()).isNull();
        assertThat(parser.to()).isNull();
        assertThat(parser.formatType()).isEqualTo(STRING);
        assertThat(parser.savePath()).isNull();
    }

    @Test
    @DisplayName("Некорректный формат файла.")
    void incorrectFormatType() {
        // given
        String[] args = new String[] {
            "--path", "src/test/resources/project3/source.txt",
            "--from", "2023-08-15",
            "--to", "2023-09-15",
            "--format", "word",
            "--save", "src/test/resources/project3/report.docx"
        };

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> CommandLineArgumentParser.with(args)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).hasMessage("Unknown format type: word");
    }

    @Test
    @DisplayName("Некорректный формат даты.")
    void incorrectFromFormat() {
        // given
        String[] args = new String[] {
            "--path", "src/test/resources/project3/source.txt",
            "--from", "2023/08/15",
            "--to", "2023-09-15",
            "--format", "adoc",
            "--save", "src/test/resources/project3/report.adoc"
        };

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> CommandLineArgumentParser.with(args)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).hasMessage("Date parsing failed. Reason: Text '2023/08/15' could not be parsed at index 4");
    }

    @Test
    @DisplayName("Несоответствие расширения файла сохранения для markdown.")
    void incorrectMarkdownSaveFileExtension() {
        // given
        String[] args = new String[] {
            "--path", "src/test/resources/project3/source.txt",
            "--from", "2023-08-15",
            "--to", "2023-09-15",
            "--format", "markdown",
            "--save", "src/test/resources/project3/report.txt"
        };

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> CommandLineArgumentParser.with(args)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).hasMessage("For markdown output .md file required!");
    }

    @Test
    @DisplayName("Несоответствие расширения файла сохранения для строкового формата.")
    void incorrectTextSaveFileExtension() {
        // given
        String[] args = new String[] {
            "--path", "src/test/resources/project3/source.txt",
            "--from", "2023-08-15",
            "--to", "2023-09-15",
            "--format", "string",
            "--save", "src/test/resources/project3/report.docx"
        };

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> CommandLineArgumentParser.with(args)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).hasMessage("For string output .txt file required!");
    }

    @Test
    @DisplayName("Несоответствие расширения файла сохранения для adoc.")
    void incorrectADOCSaveFileExtension() {
        // given
        String[] args = new String[] {
            "--path", "src/test/resources/project3/source.txt",
            "--from", "2023-08-15",
            "--to", "2023-09-15",
            "--format", "adoc",
            "--save", "src/test/resources/project3/report.txt"
        };

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> CommandLineArgumentParser.with(args)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).hasMessage("For adoc output .adoc file required!");
    }

    @Test
    @DisplayName("Null аргумент.")
    void parseNullArgs() {
        // given
        String[] args = null;

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> CommandLineArgumentParser.with(args)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).hasMessage("Cmd arguments must be not null!");
    }
}
