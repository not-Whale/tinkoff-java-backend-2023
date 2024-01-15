package edu.homework5.computer_club;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ComputerClubAnalytics {
    private ComputerClubAnalytics() {}

    public static String calculateAverageGameTime(String[] sessions) throws IllegalArgumentException {
        if (sessions == null) {
            throw new IllegalArgumentException("Список сессий не может быть null!");
        }
        Double averageSessionTime = Arrays.stream(sessions)
            .map(ComputerClubAnalytics::sessionStringToStartEndDate)
            .map(ComputerClubAnalytics::startEndDateToDuration)
            .collect(Collectors.averagingLong(Duration::toMillis));
        Duration averageSessionDuration = Duration.ofMillis(averageSessionTime.longValue());
        long hours = averageSessionDuration.toHours();
        int minutes = averageSessionDuration.toMinutesPart();
        return String.format("%dч %dм", hours, minutes);
    }

    private static String[] sessionStringToStartEndDate(String sessionString) throws IllegalArgumentException {
        String[] startEndDate = sessionString.split(" - ");
        if (startEndDate.length != 2) {
            throw new IllegalArgumentException("Формат сессии: \"yyyy-MM-dd, hh:mm - yyyy-MM-dd, hh:mm\"!");
        }
        return startEndDate;
    }

    private static Duration startEndDateToDuration(String[] startEndDate) throws IllegalArgumentException {
        String dtmFormat = "yyyy-MM-dd, hh:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(dtmFormat);
        try {
            return Duration.between(
                sdf.parse(startEndDate[0]).toInstant(),
                sdf.parse(startEndDate[1]).toInstant()
            );
        } catch (ParseException e) {
            throw new IllegalArgumentException("Формат даты: \"yyyy-MM-dd, hh:mm\"", e);
        }
    }
}
