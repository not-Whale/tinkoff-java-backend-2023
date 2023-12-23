package edu.project3.formatters;

import java.util.Arrays;
import java.util.Comparator;

public class FormatUtils {
    private FormatUtils() {
    }

    public static String reportToString(String[] names, String[] values, String statisticName, String valuesName) {
        if (names.length != values.length) {
            throw new IllegalArgumentException("Column's names must match values!");
        }

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

    public static String reportToAdoc(String[] names, String[] values, String statisticName, String valuesName) {
        if (names.length != values.length) {
            throw new IllegalArgumentException();
        }

        long tableLength = names.length;
        long lastLine = tableLength - 1;
        String border = "|===\n";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
            .append("cols=\"1,1\"\n")
            .append(border)
            .append("|")
            .append(statisticName)
            .append(" |")
            .append(valuesName)
            .append("\n\n");

        for (int i = 0; i < tableLength; i++) {
            stringBuilder
                .append("|")
                .append(names[i])
                .append("\n")
                .append("|")
                .append(values[i])
                .append("\n");
            if (i != lastLine) {
                stringBuilder
                    .append("\n");
            }
            stringBuilder.append(border);
        }

        return stringBuilder.toString();
    }

    public static String reportToMarkdown(String[] names, String[] values, String statisticName, String valuesName) {
        if (names.length != values.length) {
            throw new IllegalArgumentException();
        }

        long tableLength = names.length;

        int maxNameLength = getMaxNameLength(names, statisticName);
        int maxValuesLength = getMaxValuesLength(values, valuesName);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
            .append("|")
            .append(statisticName)
            .repeat(" ", maxNameLength - statisticName.length())
            .append("|")
            .append(valuesName)
            .repeat(" ", maxValuesLength - valuesName.length())
            .append("|")
            .append("\n");

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

        for (int i = 0; i < tableLength; i++) {
            stringBuilder
                .append("|")
                .append(names[i])
                .repeat(" ", maxNameLength - names[i].length())
                .append("|")
                .append(values[i])
                .repeat(" ", maxValuesLength - values[i].length())
                .append("|")
                .append("\n");
        }

        return stringBuilder.toString();
    }

    private static void appendStringFormattedLastLine(StringBuilder stringBuilder,
        int maxNameLength, int maxValuesLength) {
        stringBuilder
            .append("└")
            .repeat("─", maxNameLength)
            .append("┴")
            .repeat("─", maxValuesLength)
            .append("┘")
            .append("\n");
    }

    private static void appendStringFormattedDataLine(StringBuilder stringBuilder,
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

    private static void appendStringFormattedDelimiter(StringBuilder stringBuilder,
        int maxNameLength, int maxValuesLength) {
        stringBuilder
            .append("├")
            .repeat("─", maxNameLength)
            .append("┼")
            .repeat("─", maxValuesLength)
            .append("┤")
            .append("\n");
    }

    private static void appendStringFormattedTitle(StringBuilder stringBuilder,
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

    private static void appendStringFormattedFirstLine(StringBuilder stringBuilder,
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
