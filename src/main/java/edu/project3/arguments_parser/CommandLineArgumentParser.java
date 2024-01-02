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
import org.apache.commons.io.FilenameUtils;
import org.jetbrains.annotations.Nullable;

public class CommandLineArgumentParser {
    private final String sourcePath;

    private final LocalDateTime from;

    private final LocalDateTime to;

    private final FormatType formatType;

    private final String savePath;

    private static final String SOURCE_PATH_OPTION_NAME = "path";

    private static final String SOURCE_PATH_ARGUMENT_DESCRIPTION = "Path template or URL to input NGINX log files.";

    private static final String SOURCE_PATH_ARGUMENT_NAME = "source file path";

    private static final String FROM_OPTION_NAME = "from";

    private static final String FROM_ARGUMENT_DESCRIPTION = "Log files analytics from date.";

    private static final String FROM_TO_ARGUMENTS_NAME = "ISO8601 date";

    private static final String TO_OPTION_NAME = "to";

    private static final String TO_ARGUMENT_DESCRIPTION = "Log files analytics to date.";

    private static final String FORMAT_OPTION_NAME = "format";

    private static final String FORMAT_ARGUMENT_DESCRIPTION = "Output format. Default: string.";

    private static final String FORMAT_ARGUMENT_NAME = "markdown/adoc/string";

    private static final String SAVE_PATH_OPTION_NAME = "save";

    private static final String SAVE_PATH_ARGUMENT_NAME = "save file path";

    private static final String SAVE_PATH_ARGUMENT_DESCRIPTION = "Path to output save file.";

    private static final String MARKDOWN_TYPE = "markdown";

    private static final String ADOC_TYPE = "ADOC";

    private static final String STRING_TYPE = "string";

    private static final String MARKDOWN_EXTENSION = "md";

    private static final String ADOC_EXTENSION = "adoc";

    private static final String STRING_EXTENSION = "txt";

    private CommandLineArgumentParser(String[] args) {
        Options options = getCmdOptions();
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            this.sourcePath = cmd.getOptionValue(SOURCE_PATH_OPTION_NAME);
            this.from = cmd.hasOption(FROM_OPTION_NAME)
                ? parseLocalDateTime(cmd.getOptionValue(FROM_OPTION_NAME))
                : null;
            this.to = cmd.hasOption(TO_OPTION_NAME)
                ? parseLocalDateTime(cmd.getOptionValue(TO_OPTION_NAME))
                : null;
            this.formatType = cmd.hasOption(FORMAT_OPTION_NAME)
                ? parseFormatType(cmd.getOptionValue(FORMAT_OPTION_NAME))
                : FormatType.STRING;
            this.savePath = cmd.hasOption(SAVE_PATH_OPTION_NAME)
                ? cmd.getOptionValue(SAVE_PATH_OPTION_NAME)
                : null;
            if (savePath != null) {
                validateSaveFileName();
            }
        } catch (ParseException e) {
            throw new RuntimeException("Arguments parsing failed. Reason: " + e.getMessage());
        }
    }

    public static CommandLineArgumentParser with(String[] args) {
        if (args == null) {
            throw new IllegalArgumentException("Cmd arguments must not be null!");
        }
        return new CommandLineArgumentParser(args);
    }

    public String sourcePath() {
        return sourcePath;
    }

    public @Nullable LocalDateTime from() {
        return from;
    }

    public @Nullable LocalDateTime to() {
        return to;
    }

    public @Nullable FormatType formatType() {
        return formatType;
    }

    public @Nullable String savePath() {
        return savePath;
    }

    private void validateSaveFileName() {
        if (formatType.equals(FormatType.STRING) && !FilenameUtils.getExtension(savePath).equals(STRING_EXTENSION)) {
            throw new IllegalArgumentException("For string output .txt file required!");
        }
        if (formatType.equals(FormatType.MARKDOWN) && !FilenameUtils.getExtension(savePath).equals(MARKDOWN_EXTENSION)) {
            throw new IllegalArgumentException("For markdown output .md file required!");
        }
        if (formatType.equals(FormatType.ADOC) && !FilenameUtils.getExtension(savePath).equals(ADOC_EXTENSION)) {
            throw new IllegalArgumentException("For adoc output .adoc file required!");
        }
    }

    private Options getCmdOptions() {
        Options options = new Options();
        Option sourcePathOption = Option.builder()
            .longOpt(SOURCE_PATH_OPTION_NAME)
            .argName(SOURCE_PATH_ARGUMENT_NAME)
            .hasArg()
            .desc(SOURCE_PATH_ARGUMENT_DESCRIPTION)
            .required()
            .build();
        Option fromOption = Option.builder()
            .longOpt(FROM_OPTION_NAME)
            .argName(FROM_TO_ARGUMENTS_NAME)
            .hasArg()
            .desc(FROM_ARGUMENT_DESCRIPTION)
            .build();
        Option toOption = Option.builder()
            .longOpt(TO_OPTION_NAME)
            .argName(FROM_TO_ARGUMENTS_NAME)
            .hasArg()
            .desc(TO_ARGUMENT_DESCRIPTION)
            .build();
        Option formatOption = Option.builder()
            .longOpt(FORMAT_OPTION_NAME)
            .argName(FORMAT_ARGUMENT_NAME)
            .hasArg()
            .desc(FORMAT_ARGUMENT_DESCRIPTION)
            .build();
        Option savePathOption = Option.builder()
            .longOpt(SAVE_PATH_OPTION_NAME)
            .argName(SAVE_PATH_ARGUMENT_NAME)
            .hasArg()
            .desc(SAVE_PATH_ARGUMENT_DESCRIPTION)
            .build();
        options.addOption(sourcePathOption);
        options.addOption(fromOption);
        options.addOption(toOption);
        options.addOption(formatOption);
        options.addOption(savePathOption);
        return options;
    }

    private FormatType parseFormatType(String formatTypeString) {
        if (formatTypeString.equalsIgnoreCase(MARKDOWN_TYPE)) {
            return FormatType.MARKDOWN;
        }
        if (formatTypeString.equalsIgnoreCase(ADOC_TYPE)) {
            return FormatType.ADOC;
        }
        if (formatTypeString.equalsIgnoreCase(STRING_TYPE)) {
            return FormatType.STRING;
        }
        throw new IllegalArgumentException("Unknown format type: " + formatTypeString);
    }

    private LocalDateTime parseLocalDateTime(String dateTimeString) {
        try {
            return LocalDate.parse(dateTimeString, DateTimeFormatter.ISO_DATE).atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Date parsing failed. Reason: " + e.getMessage());
        }
    }
}
