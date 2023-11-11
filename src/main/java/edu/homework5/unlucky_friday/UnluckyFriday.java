package edu.homework5.unlucky_friday;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class UnluckyFriday {

    public static final int UNLUCKY_DAY_OF_MONTH = 13;
    public static final int LAST_DAY_OF_DECEMBER = 31;
    public static final int FIRST_DAY_OF_JANUARY = 1;
    public static final int FRIDAY = 5;

    private UnluckyFriday() {
    }

    public static LocalDate[] findUnluckyFridaysByYear(int year) throws IllegalArgumentException {
        if (year < 1) {
            throw new IllegalArgumentException("Год должен быть положительным числом!");
        }
        List<LocalDate> unluckyFridays = new ArrayList<>();
        boolean reachedFriday = false;
        LocalDate endDate = LocalDate.of(year, Month.DECEMBER, LAST_DAY_OF_DECEMBER);
        LocalDate currentDate = LocalDate.of(year, Month.JANUARY, FIRST_DAY_OF_JANUARY);
        while (currentDate.isBefore(endDate)) {
            if (currentDate.get(ChronoField.DAY_OF_WEEK) == FRIDAY) {
                reachedFriday = true;
                if (currentDate.getDayOfMonth() == UNLUCKY_DAY_OF_MONTH) {
                    unluckyFridays.add(currentDate);
                }
            }
            if (reachedFriday) {
                currentDate = currentDate.plusWeeks(1);
            } else {
                currentDate = currentDate.plusDays(1);
            }
        }
        return unluckyFridays.toArray(LocalDate[]::new);
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
