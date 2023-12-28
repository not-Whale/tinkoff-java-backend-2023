package edu.project3.formatters;

import java.util.Arrays;
import java.util.Comparator;

public class FormatUtils {
    private static final String ADOC_BORDER = "|===\n";

    public static final String NAMES_MUST_MATCH_VALUES_MESSAGE = "Column's names must match values!";

    private static final String NAMES_NULL_ERROR_MESSAGE = "Names must not be null!";

    private static final String VALUES_NULL_ERROR_MESSAGE = "Values must not be null!";

    private static final String STATISTIC_NAME_NULL_ERROR_MESSAGE = "Statistic name must not be null!";

    private static final String VALUES_NAME_NULL_ERROR_MESSAGE = "Values name must not be null!";

    private FormatUtils() {}

    public static String reportToString(String[] names, String[] values, String statisticName, String valuesName) {
        validateInputs(names, values, statisticName, valuesName);

        long tableLength = names.length;
        long lastLine = tableLength - 1;
        int maxNameLength = getMaxNameLength(names, statisticName);
        int maxValuesLength = getMaxValuesLength(values, valuesName);

        StringBuilder stringBuilder = new StringBuilder();
        appendStringFormattedFirstLine(stringBuilder, maxNameLength, maxValuesLength);
        appendStringFormattedTitle(stringBuilder, maxNameLength, maxValuesLength, statisticName, valuesName);
        appendStringFormattedDelimiter(stringBuilder, maxNameLength, maxValuesLength);
        for (int i = 0; i < tableLength; i++) {
            appendStringFormattedDataLine(stringBuilder, maxNameLength, maxValuesLength, names[i], values[i]);
            if (i != lastLine) {
                appendStringFormattedDelimiter(stringBuilder, maxNameLength, maxValuesLength);
            }
        }
        appendStringFormattedLastLine(stringBuilder, maxNameLength, maxValuesLength);
        return stringBuilder.toString();
    }

    public static String reportToADOC(String[] names, String[] values, String statisticName, String valuesName) {
        validateInputs(names, values, statisticName, valuesName);

        long tableLength = names.length;
        long lastLine = tableLength - 1;

        StringBuilder stringBuilder = new StringBuilder();
        appendADOCFormattedFirstLine(stringBuilder, statisticName, valuesName);
        for (int i = 0; i < tableLength; i++) {
            appendADOCFormattedDataLine(stringBuilder, names[i], values[i]);
            if (i != lastLine) {
                stringBuilder.append("\n");
            }
            stringBuilder.append(ADOC_BORDER);
        }
        return stringBuilder.toString();
    }

    public static String reportToMarkdown(String[] names, String[] values, String statisticName, String valuesName) {
        validateInputs(names, values, statisticName, valuesName);

        long tableLength = names.length;
        int maxNameLength = getMaxNameLength(names, statisticName);
        int maxValuesLength = getMaxValuesLength(values, valuesName);

        StringBuilder stringBuilder = new StringBuilder();
        appendMarkdownFormattedTitle(stringBuilder, maxNameLength, maxValuesLength, statisticName, valuesName);
        appendMarkdownFormattedTitleDelimiter(stringBuilder, maxNameLength, maxValuesLength);
        for (int i = 0; i < tableLength; i++) {
            appendMarkdownFormattedDataLine(stringBuilder, maxNameLength, maxValuesLength, names[i], values[i]);
        }
        return stringBuilder.toString();
    }

    private static void validateInputs(String[] names, String[] values, String statisticName, String valuesName) {
        if (names == null) {
            throw new IllegalArgumentException(NAMES_NULL_ERROR_MESSAGE);
        }
        if (values == null) {
            throw new IllegalArgumentException(VALUES_NULL_ERROR_MESSAGE);
        }
        if (statisticName == null) {
            throw new IllegalArgumentException(STATISTIC_NAME_NULL_ERROR_MESSAGE);
        }
        if (valuesName == null) {
            throw new IllegalArgumentException(VALUES_NAME_NULL_ERROR_MESSAGE);
        }
        if (names.length != values.length) {
            throw new IllegalArgumentException(NAMES_MUST_MATCH_VALUES_MESSAGE);
        }
    }

    private static void appendMarkdownFormattedDataLine(
        StringBuilder stringBuilder,
        int maxNameLength, int maxValuesLength,
        String name, String value) {
        stringBuilder
            .append("|")
            .append(name)
            .repeat(" ", maxNameLength - name.length())
            .append("|")
            .append(value)
            .repeat(" ", maxValuesLength - value.length())
            .append("|")
            .append("\n");
    }

    private static void appendMarkdownFormattedTitleDelimiter(
        StringBuilder stringBuilder,
        int maxNameLength, int maxValuesLength) {
        stringBuilder
            .append("|")
            .append(":")
            .repeat("-", maxNameLength - 2)
            .append(":")
            .append("|")
            .append(":")
            .repeat("-", maxValuesLength - 2)
            .append(":")
            .append("|")
            .append("\n");
    }

    private static void appendMarkdownFormattedTitle(
        StringBuilder stringBuilder,
        int maxNameLength, int maxValuesLength,
        String statisticName, String valuesName) {
        stringBuilder
            .append("|")
            .append(statisticName)
            .repeat(" ", maxNameLength - statisticName.length())
            .append("|")
            .append(valuesName)
            .repeat(" ", maxValuesLength - valuesName.length())
            .append("|")
            .append("\n");
    }

    private static void appendADOCFormattedDataLine(
        StringBuilder stringBuilder,
        String name, String value) {
        stringBuilder
            .append("|")
            .append(name)
            .append("\n")
            .append("|")
            .append(value)
            .append("\n");
    }

    private static void appendADOCFormattedFirstLine(
        StringBuilder stringBuilder,
        String statisticName, String valuesName) {
        stringBuilder
            .append("cols=\"1,1\"\n")
            .append(ADOC_BORDER)
            .append("|")
            .append(statisticName)
            .append(" |")
            .append(valuesName)
            .append("\n\n");
    }

    private static void appendStringFormattedLastLine(
        StringBuilder stringBuilder,
        int maxNameLength, int maxValuesLength) {
        stringBuilder
            .append("└")
            .repeat("─", maxNameLength)
            .append("┴")
            .repeat("─", maxValuesLength)
            .append("┘")
            .append("\n");
    }

    private static void appendStringFormattedDataLine(
        StringBuilder stringBuilder,
        int maxNameLength, int maxValuesLength,
        String name, String value) {
        stringBuilder
            .append("|")
            .append(name)
            .repeat(" ", maxNameLength - name.length())
            .append("│")
            .append(value)
            .repeat(" ", maxValuesLength - value.length())
            .append("│")
            .append("\n");
    }

    private static void appendStringFormattedDelimiter(
        StringBuilder stringBuilder,
        int maxNameLength, int maxValuesLength) {
        stringBuilder
            .append("├")
            .repeat("─", maxNameLength)
            .append("┼")
            .repeat("─", maxValuesLength)
            .append("┤")
            .append("\n");
    }

    private static void appendStringFormattedTitle(
        StringBuilder stringBuilder,
        int maxNameLength, int maxValuesLength,
        String statisticName, String valuesName) {
        stringBuilder
            .append("│")
            .append(statisticName)
            .repeat(" ", maxNameLength - statisticName.length())
            .append("│")
            .append(valuesName)
            .repeat(" ", maxValuesLength - valuesName.length())
            .append("│")
            .append("\n");
    }

    private static void appendStringFormattedFirstLine(
        StringBuilder stringBuilder,
        int maxNameLength, int maxValuesLength) {
        stringBuilder
            .append("┌")
            .repeat("─", maxNameLength)
            .append("┬")
            .repeat("─", maxValuesLength)
            .append("┐")
            .append("\n");
    }

    private static int getMaxNameLength(String[] names, String statisticName) {
        int maxNameLength = Arrays.stream(names)
            .map(String::length)
            .max(Comparator.comparingInt(a -> a))
            .orElse(0);
        return Math.max(maxNameLength, statisticName.length());
    }

    private static int getMaxValuesLength(String[] values, String valuesName) {
        int maxValuesLength = Arrays.stream(values)
            .map(String::length)
            .max(Comparator.comparingInt(a -> a))
            .orElse(0);
        return Math.max(maxValuesLength, valuesName.length());
    }
}
