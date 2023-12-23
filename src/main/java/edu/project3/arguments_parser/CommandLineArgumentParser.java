package edu.project3.arguments_parser;

import edu.project3.formatters.FormatType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class CommandLineArgumentParser {
    private final String sourcePath;

    private final LocalDateTime from;

    private final LocalDateTime to;

    private final FormatType formatType;

    private static final String SOURCE_PATH_ARGUMENT_NAME = "path";

    private static final String FROM_ARGUMENT_NAME = "from";

    private static final String TO_ARGUMENT_NAME = "to";

    private static final String FORMAT_ARGUMENT_NAME = "format";

    private static final String MARKDOWN_TYPE = "markdown";

    private static final String ADOC_TYPE = "adoc";

    private CommandLineArgumentParser(String[] args) {
        Options options = getCmdOptions();
        CommandLineParser parser = new PosixParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(SOURCE_PATH_ARGUMENT_NAME)) {
                this.sourcePath = cmd.getOptionValue(SOURCE_PATH_ARGUMENT_NAME);
            } else {
                throw new IllegalArgumentException("Source file path must be not null!");
            }
            this.from = cmd.hasOption(FROM_ARGUMENT_NAME)
                ? parseLocalDateTime(cmd.getOptionValue(FROM_ARGUMENT_NAME))
                : null;
            this.to = cmd.hasOption(TO_ARGUMENT_NAME)
                ? parseLocalDateTime(cmd.getOptionValue(TO_ARGUMENT_NAME))
                : null;
            this.formatType = cmd.hasOption(FORMAT_ARGUMENT_NAME)
                ? parseFormatType(cmd.getOptionValue(FORMAT_ARGUMENT_NAME))
                : FormatType.STRING;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static CommandLineArgumentParser with(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException("Cmd arguments must be not null!");
        }
        return new CommandLineArgumentParser(args);
    }

    public String sourcePath() {
        return sourcePath;
    }

    public LocalDateTime from() {
        return from;
    }

    public LocalDateTime to() {
        return to;
    }

    public FormatType formatType() {
        return formatType;
    }

    private Options getCmdOptions() {
        Options options = new Options();
        Option sourcePathOption = Option.builder(SOURCE_PATH_ARGUMENT_NAME)
            .argName("source file path")
            .hasArg()
            .desc("Path template or URL to NGINX log files.")
            .required()
            .build();
        Option fromOption = Option.builder(FROM_ARGUMENT_NAME)
            .argName("ISO8601 date")
            .hasArg()
            .desc("Log files analytics from date.")
            .build();
        Option toOption = Option.builder(TO_ARGUMENT_NAME)
            .argName("ISO8601 date")
            .hasArg()
            .desc("Log files analytics to date.")
            .build();
        Option formatOption = Option.builder(FORMAT_ARGUMENT_NAME)
            .argName("markdown/adoc/string")
            .hasArg()
            .desc("Output format. Default: string.")
            .build();
        options.addOption(sourcePathOption);
        options.addOption(fromOption);
        options.addOption(toOption);
        options.addOption(formatOption);
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
