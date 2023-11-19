package edu.project3.formatters;

import java.util.Arrays;

public class FormatUtils {
    private FormatUtils() {}

    public static String reportToAdoc(String[] names, String[] values, String statisticName, String valuesName) {
        if (names.length != values.length) {
            throw new IllegalArgumentException();
        }

        long tableLength = names.length;
        long lastLine = tableLength - 1;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
            .append("cols=\"1,1\"\n")
            .append("|===\n")
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
            stringBuilder.append("|===\n");
        }

        return stringBuilder.toString();
    }

    public static String reportToMarkdown(String[] names, String[] values, String statisticName, String valuesName) {
        if (names.length != values.length) {
            throw new IllegalArgumentException();
        }

        long tableLength = names.length;

        int maxNameLength = Arrays.stream(names)
            .map(String::length)
            .max((a, b) -> b - a)
            .orElse(0);
        maxNameLength = Math.max(maxNameLength, statisticName.length());

        int maxValuesLength = Arrays.stream(values)
            .map(String::length)
            .max((a, b) -> b - a)
            .orElse(0);
        maxValuesLength = Math.max(maxValuesLength, valuesName.length());

        long lastLine = tableLength - 1;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
            .append("|")
            .append(statisticName)
            .repeat(" ", maxNameLength - statisticName.length())
            .append("|")
            .append(valuesName)
            .repeat(" ", maxValuesLength - valuesName.length())
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
}
