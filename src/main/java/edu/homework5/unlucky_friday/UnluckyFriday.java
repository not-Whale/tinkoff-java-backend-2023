package edu.homework5.unlucky_friday;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class UnluckyFriday {

    public static final int UNLUCKY_DAY_OF_MONTH = 13;

    public static final int DAYS_IN_WEEK = 7;

    private UnluckyFriday() {
    }

    public static Date[] findUnluckyFridaysByYear(int year) throws IllegalArgumentException {
        if (year < 1) {
            throw new IllegalArgumentException("Год должен быть положительным числом!");
        }

        String datePattern = "yyyy-MM-dd";
        String startDateString = year + "-01-01";
        String endDateString = year + "-12-31";
        ArrayList<Date> unluckyFridays = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat(datePattern);

        try {
            GregorianCalendar calendar = new GregorianCalendar();
            Date startDate = sdf.parse(startDateString);
            Date endDate = sdf.parse(endDateString);
            calendar.setTime(startDate);
            boolean reachedFriday = false;
            while (calendar.getTime().before(endDate)) {
                if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
                    reachedFriday = true;
                    if (calendar.get(Calendar.DAY_OF_MONTH) == UNLUCKY_DAY_OF_MONTH) {
                        unluckyFridays.add(calendar.getTime());
                    }
                }
                if (reachedFriday) {
                    calendar.add(Calendar.DAY_OF_YEAR, DAYS_IN_WEEK);
                } else {
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                }
            }
        } catch (ParseException e) {
            throw new IllegalArgumentException("Формат даты: \"yyyy-MM-dd\"");
        }
        return unluckyFridays.toArray(new Date[] {});
    }

    public static LocalDate getNextUnluckyFriday(LocalDate currentDate) throws IllegalArgumentException {
        if (currentDate == null) {
            throw new IllegalArgumentException("Дата не может быть null!");
        }
        return currentDate.with((temporal -> {
            Temporal nextFriday = temporal.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
            while (nextFriday.get(ChronoField.DAY_OF_MONTH) != UNLUCKY_DAY_OF_MONTH) {
                nextFriday = nextFriday.plus(1, ChronoUnit.WEEKS);
            }
            return nextFriday;
        }));
    }
}
