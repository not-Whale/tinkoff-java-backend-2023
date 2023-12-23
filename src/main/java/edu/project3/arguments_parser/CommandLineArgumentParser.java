package edu.project3.arguments_parser;

import edu.project3.formatters.FormatType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

public class CommandLineArgumentParser {
    private final String sourcePath;

    private final LocalDateTime from;

    private final LocalDateTime to;

    private final FormatType formatType;

    private static final String SOURCE_PATH_OPTION_NAME = "path";

    public static final String SOURCE_PATH_ARGUMENT_DESCRIPTION = "Path template or URL to NGINX log files.";

    public static final String SOURCE_PATH_ARGUMENT_NAME = "source file path";

    private static final String FROM_OPTION_NAME = "from";

    public static final String FROM_ARGUMENT_DESCRIPTION = "Log files analytics from date.";

    public static final String FROM_TO_ARGUMENTS_NAME = "ISO8601 date";

    private static final String TO_OPTION_NAME = "to";

    public static final String TO_ARGUMENT_DESCRIPTION = "Log files analytics to date.";

    private static final String FORMAT_OPTION_NAME = "format";

    public static final String FORMAT_ARGUMENT_DESCRIPTION = "Output format. Default: string.";

    public static final String FORMAT_ARGUMENT_NAME = "markdown/adoc/string";

    private static final String MARKDOWN_TYPE = "markdown";

    private static final String ADOC_TYPE = "adoc";

    private CommandLineArgumentParser(String[] args) {
        Options options = getCmdOptions();
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(SOURCE_PATH_OPTION_NAME)) {
                this.sourcePath = cmd.getOptionValue(SOURCE_PATH_OPTION_NAME);
            } else {
                throw new IllegalArgumentException("Source file path must be not null!");
            }
            this.from = cmd.hasOption(FROM_OPTION_NAME)
                ? parseLocalDateTime(cmd.getOptionValue(FROM_OPTION_NAME))
                : null;
            this.to = cmd.hasOption(TO_OPTION_NAME)
                ? parseLocalDateTime(cmd.getOptionValue(TO_OPTION_NAME))
                : null;
            this.formatType = cmd.hasOption(FORMAT_OPTION_NAME)
                ? parseFormatType(cmd.getOptionValue(FORMAT_OPTION_NAME))
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
        Option sourcePathOption = Option.builder(SOURCE_PATH_OPTION_NAME)
            .argName(SOURCE_PATH_ARGUMENT_NAME)
            .hasArg()
            .desc(SOURCE_PATH_ARGUMENT_DESCRIPTION)
            .required()
            .build();
        Option fromOption = Option.builder(FROM_OPTION_NAME)
            .argName(FROM_TO_ARGUMENTS_NAME)
            .hasArg()
            .desc(FROM_ARGUMENT_DESCRIPTION)
            .build();
        Option toOption = Option.builder(TO_OPTION_NAME)
            .argName(FROM_TO_ARGUMENTS_NAME)
            .hasArg()
            .desc(TO_ARGUMENT_DESCRIPTION)
            .build();
        Option formatOption = Option.builder(FORMAT_OPTION_NAME)
            .argName(FORMAT_ARGUMENT_NAME)
            .hasArg()
            .desc(FORMAT_ARGUMENT_DESCRIPTION)
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
