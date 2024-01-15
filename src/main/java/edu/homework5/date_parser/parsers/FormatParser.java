package edu.homework5.date_parser.parsers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAccessor;

public class FormatParser implements Parser {
    private final String format;

    private final String formatRegex;

    public FormatParser(String format, String formatRegex) {
        this.format = format;
        this.formatRegex = formatRegex;
    }

    public FormatParser(String format) {
        this(format, ".*");
    }

    @Override
    public boolean canFormatDate(String date) {
        return formatDate(date) != null;
    }

    @Override
    public LocalDate formatDate(String date) {
        if (date == null || !date.matches(formatRegex)) {
            return null;
        }

        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(format);
            TemporalAccessor temporalAccessor = dateTimeFormatter.parse(date);
            return LocalDate.of(
                temporalAccessor.get(ChronoField.YEAR),
                temporalAccessor.get(ChronoField.MONTH_OF_YEAR),
                temporalAccessor.get(ChronoField.DAY_OF_MONTH)
            );
        } catch (RuntimeException e) {
            return null;
        }
    }
}
