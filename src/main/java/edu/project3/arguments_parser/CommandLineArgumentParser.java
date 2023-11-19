package edu.project3.arguments_parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import edu.project3.formatters.FormatType;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class CommandLineArgumentParser {
    private String file;

    private LocalDateTime from = null;

    private LocalDateTime to = null;

    private FormatType formatType = null;

    private static final String PATH_ARGUMENT = "path";

    private static final String FROM_ARGUMENT = "from";

    private static final String TO_ARGUMENT = "to";

    private static final String FORMAT_ARGUMENT = "format";

    private static final String MARKDOWN_TYPE = "markdown";

    private static final String ADOC_TYPE = "adoc";

    public CommandLineArgumentParser(String[] args) {
        Options options = new Options();
        options.addOption(PATH_ARGUMENT, true, "Шаблон пути или URL к NGINX лог-файлам.");
        options.addOption(FROM_ARGUMENT, true, "Начальная дата просмотра логов.");
        options.addOption(TO_ARGUMENT, true, "Конечная дата просмотра логов.");
        options.addOption(FORMAT_ARGUMENT, true, "Формат вывода.");

        CommandLineParser parser = new PosixParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(PATH_ARGUMENT)) {
                this.file = cmd.getOptionValue(PATH_ARGUMENT);
            }
            if (cmd.hasOption(FROM_ARGUMENT)) {
                this.from = parseLocalDateTime(cmd.getOptionValue(FROM_ARGUMENT));
            }
            if (cmd.hasOption(TO_ARGUMENT)) {
                this.to = parseLocalDateTime(cmd.getOptionValue(TO_ARGUMENT));
            }
            if (cmd.hasOption(FORMAT_ARGUMENT)) {
                this.formatType = parseFormatType(cmd.getOptionValue(FORMAT_ARGUMENT));
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private FormatType parseFormatType(String formatTypeString) {
        if (formatTypeString.equalsIgnoreCase(MARKDOWN_TYPE)) {
            return FormatType.MARKDOWN;
        }
        if (formatTypeString.equalsIgnoreCase(ADOC_TYPE)) {
            return FormatType.ADOC;
        }
        throw new IllegalArgumentException("Unknown format type: " + formatTypeString);
    }

    private LocalDateTime parseLocalDateTime(String dateTimeString) {
        try {
            return LocalDate.parse(dateTimeString, DateTimeFormatter.ISO_DATE).atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public String getFile() {
        return file;
    }

    public LocalDateTime getFrom() {
        return from;
    }

    public LocalDateTime getTo() {
        return to;
    }

    public FormatType getFormatType() {
        return formatType;
    }
}
