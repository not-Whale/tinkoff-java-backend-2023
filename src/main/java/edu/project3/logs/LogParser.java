package edu.project3.logs;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
    private static final String REMOTE_ADDRESS_PATTERN = "(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})";

    private static final String REMOTE_USER_PATTERN = "(\\s\\w*\\s|\\s)";

    private static final String ZONED_DATE_TIME_PATTERN = "(\\d{2}/[A-Za-z]{3}/\\d{4}:\\d{2}:\\d{2}:\\d{2} \\+\\d{4})";

    private static final String REQUEST_TYPE_PATTERN = "(GET|HEAD|POST|PUT|DELETE|CONNECT|TRACE|PATCH)";

    private static final Map<String, RequestType> REQUEST_TYPES = new HashMap<>() {{
        put("GET", RequestType.GET);
        put("HEAD", RequestType.HEAD);
        put("POST", RequestType.POST);
        put("PUT", RequestType.PUT);
        put("DELETE", RequestType.DELETE);
        put("CONNECT", RequestType.CONNECT);
        put("TRACE", RequestType.TRACE);
        put("PATCH", RequestType.PATCH);
    }};

    private static final String URL_SIMPLE_PATTERN = "([\\w:/.?=&#-]*)";

    private static final String HTTP_VERSION = "(HTTP/\\d(\\.\\d)?)";

    private static final String STATUS_PATTERN = "(\\d{3})";

    private static final String BODY_BYTES_SEND_PATTERN = "(\\d+)";

    private static final String HTTP_USER_AGENT_PATTERN = "([\\w-/.~() ]*)";

    private static final String LOG_PATTERN =
        REMOTE_ADDRESS_PATTERN
            + " -"
            + REMOTE_USER_PATTERN
            + "- \\["
            + ZONED_DATE_TIME_PATTERN
            + "] \""
            + REQUEST_TYPE_PATTERN
            + " "
            + URL_SIMPLE_PATTERN
            + " "
            + HTTP_VERSION
            + "\" "
            + STATUS_PATTERN
            + " "
            + BODY_BYTES_SEND_PATTERN
            + " \""
            + URL_SIMPLE_PATTERN
            + "\" \""
            + HTTP_USER_AGENT_PATTERN
            + "\"";

    private LogParser() {}

    @SuppressWarnings("MagicNumber")
    public static Log parse(String logString) {
        Pattern pattern = Pattern.compile(LOG_PATTERN);
        Matcher matcher = pattern.matcher(logString);
        DateTimeFormatter formatter = getZonedDateTimeFormatter();
        if (matcher.find()) {
            return new Log(
                matcher.group(1),
                matcher.group(2),
                ZonedDateTime.parse(matcher.group(3), formatter),
                REQUEST_TYPES.get(matcher.group(4)),
                matcher.group(5),
                matcher.group(6),
                Integer.parseInt(matcher.group(7)),
                Long.parseLong(matcher.group(8)),
                matcher.group(9),
                matcher.group(10)
            );
        }
        return null;
    }

    // [17/May/2015:08:05:32 +0000]
    @SuppressWarnings("MagicNumber")
    private static DateTimeFormatter getZonedDateTimeFormatter() {
        Map<Long, String> monthOfYear = new HashMap<>() {{
            put(1L, "Jan");
            put(2L, "Feb");
            put(3L, "Mar");
            put(4L, "Apr");
            put(5L, "May");
            put(6L, "Jun");
            put(7L, "Jul");
            put(8L, "Aug");
            put(9L, "Sep");
            put(10L, "Oct");
            put(11L, "Nov");
            put(12L, "Dec");
        }};

        return new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendValue(ChronoField.DAY_OF_MONTH, 1, 2, SignStyle.NOT_NEGATIVE)
            .appendLiteral("/")
            .appendText(ChronoField.MONTH_OF_YEAR, monthOfYear)
            .appendLiteral("/")
            .appendValue(ChronoField.YEAR, 4)
            .appendLiteral(":")
            .appendValue(ChronoField.HOUR_OF_DAY, 2)
            .appendLiteral(":")
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .appendLiteral(":")
            .appendValue(ChronoField.SECOND_OF_MINUTE)
            .appendLiteral(" ")
            .appendOffset("+HHMM", "GMT")
            .toFormatter();
    }
}
