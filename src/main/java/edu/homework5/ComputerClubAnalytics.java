package edu.homework5;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ComputerClubAnalytics {
    private ComputerClubAnalytics() {}

    public static Duration calculateAverageGameTime(String[] sessions) {
        String dttm = "yyyy-MM-dd, hh:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(dttm);

        Double averageSessionDuration = Arrays.stream(sessions)
            .map(sessionString -> {
                String[] startEndDate = sessionString.split(" - ");
                if (startEndDate.length != 2) {
                    throw new IllegalArgumentException("Формат сессии: \"yyyy-MM-dd, hh:mm - yyyy-MM-dd, hh:mm\"!");
                }
                return startEndDate;
            })
            .map(startEndDate -> {
                try {
                    return Duration.between(
                        sdf.parse(startEndDate[0]).toInstant(),
                        sdf.parse(startEndDate[1]).toInstant()
                    );
                } catch (ParseException e) {
                    throw new IllegalArgumentException("Формат даты: \"yyyy-MM-dd, hh:mm\"", e);
                }
            })
            .collect(Collectors.averagingLong(Duration::toMillis));

        return Duration.ofMillis(averageSessionDuration.longValue());
    }
}
