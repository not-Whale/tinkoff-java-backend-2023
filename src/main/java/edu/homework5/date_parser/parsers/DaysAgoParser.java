package edu.homework5.date_parser.parsers;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DaysAgoParser implements Parser {
    private static final String DAY_AGO_PATTERN = "1 day ago";

    private static final String DAYS_AGO_REGEX = "^(\\d) days ago$";

    @Override
    public boolean canFormatDate(String date) {
        return formatDate(date) != null;
    }

    @Override
    public LocalDate formatDate(String date) {
        if (date == null) {
            return null;
        }

        Pattern daysAgoPattern = Pattern.compile(DAYS_AGO_REGEX);
        Matcher daysAgoMatcher = daysAgoPattern.matcher(date.toLowerCase());
        if (daysAgoMatcher.find()) {
            return LocalDate.now().minusDays(Integer.parseInt(daysAgoMatcher.group(1)));
        }

        if (date.equalsIgnoreCase(DAY_AGO_PATTERN)) {
            return LocalDate.now().minusDays(1);
        }

        return null;
    }
}
