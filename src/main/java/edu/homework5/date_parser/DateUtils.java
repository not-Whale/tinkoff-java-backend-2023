package edu.homework5.date_parser;

import edu.homework5.date_parser.parsers.DaysAgoParser;
import edu.homework5.date_parser.parsers.FormatParser;
import edu.homework5.date_parser.parsers.VerbalParser;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;

public class DateUtils {
    private DateUtils() {}

    public static Optional<LocalDate> parseDate(String date) {
        return Stream.of(
            new FormatParser("yyyy-MM-dd"),
            new FormatParser("yyyy-MM-d", "^(\\d{1,4})-(\\d{2})-([1-9]|[1-3][0-9])$"),
            new FormatParser("d/M/yyyy", "^([1-9]|[1-3][0-9])/([1-9]|1[0-2])/([0-9]{4})$"),
            new FormatParser("d/M/yy", "^([1-9]|[1-3][0-9])/([1-9]|1[0-2])/([0-9]{2})$"),
            new VerbalParser(),
            new DaysAgoParser()
        )
            .filter(item -> item.canFormatDate(date))
            .map(item -> item.formatDate(date))
            .findFirst();
    }
}
