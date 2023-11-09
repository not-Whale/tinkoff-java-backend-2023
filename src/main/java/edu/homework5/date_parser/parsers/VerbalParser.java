package edu.homework5.date_parser.parsers;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.util.HashMap;

public class VerbalParser implements Parser {
    private static final HashMap<String, TemporalAdjuster> INACCURATE_TIME_LIST =
        new HashMap<String, TemporalAdjuster>() {{
            put("day before yesterday", temporal -> temporal.minus(2, ChronoUnit.DAYS));
            put("yesterday", temporal -> temporal.minus(1, ChronoUnit.DAYS));
            put("today", temporal -> temporal);
            put("now", temporal -> temporal);
            put("tomorrow", temporal -> temporal.plus(1, ChronoUnit.DAYS));
            put("day after tomorrow", temporal -> temporal.plus(2, ChronoUnit.DAYS));
        }};

    @Override
    public boolean canFormatDate(String date) {
        return formatDate(date) != null;
    }

    @Override
    public LocalDate formatDate(String date) {
        if (date == null) {
            return null;
        }

        String key = date.strip().toLowerCase();
        if (INACCURATE_TIME_LIST.containsKey(key)) {
            return LocalDate.now().with(INACCURATE_TIME_LIST.get(key));
        }

        return null;
    }
}
