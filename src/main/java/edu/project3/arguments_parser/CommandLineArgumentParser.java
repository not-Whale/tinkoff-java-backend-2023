package edu.project3.arguments_parser;

import edu.project3.formatters.FormatType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class CommandLineArgumentParser {
    private String file;

    private LocalDateTime from = null;

    private LocalDateTime to = null;

    private FormatType formatType;

    private static final String PATH_ARGUMENT_NAME = "path";

    private static final String FROM_ARGUMENT_NAME = "from";

    private static final String TO_ARGUMENT_NAME = "to";

    private static final String FORMAT_ARGUMENT_NAME = "format";

    private static final String MARKDOWN_TYPE = "markdown";

    private static final String ADOC_TYPE = "adoc";

    public CommandLineArgumentParser(String[] args) {
        Options options = getCmdOptions();
        CommandLineParser parser = new PosixParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(PATH_ARGUMENT_NAME)) {
                this.file = cmd.getOptionValue(PATH_ARGUMENT_NAME);
            }
            if (cmd.hasOption(FROM_ARGUMENT_NAME)) {
                this.from = parseLocalDateTime(cmd.getOptionValue(FROM_ARGUMENT_NAME));
            }
            if (cmd.hasOption(TO_ARGUMENT_NAME)) {
                this.to = parseLocalDateTime(cmd.getOptionValue(TO_ARGUMENT_NAME));
            }
            if (cmd.hasOption(FORMAT_ARGUMENT_NAME)) {
                this.formatType = parseFormatType(cmd.getOptionValue(FORMAT_ARGUMENT_NAME));
            } else {
                this.formatType = FormatType.STRING;
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
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

    private Options getCmdOptions() {
        Options options = new Options();
        options.addOption(PATH_ARGUMENT_NAME, true, "Шаблон пути или URL к NGINX лог-файлам.");
        options.addOption(FROM_ARGUMENT_NAME, true, "Начальная дата просмотра логов.");
        options.addOption(TO_ARGUMENT_NAME, true, "Конечная дата просмотра логов.");
        options.addOption(FORMAT_ARGUMENT_NAME, true, "Формат вывода.");
        return options;
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
}
