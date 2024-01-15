package edu.homework5.unlucky_friday;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.List;

public class UnluckyFriday {

    public static final int UNLUCKY_DAY_OF_MONTH = 13;
    public static final int LAST_DAY_OF_DECEMBER = 31;
    public static final int FIRST_DAY_OF_JANUARY = 1;
    public static final int FRIDAY = 5;

    private UnluckyFriday() {}

    public static LocalDate[] findUnluckyFridaysByYear(int year) throws IllegalArgumentException {
        if (year < 1) {
            throw new IllegalArgumentException("Год должен быть положительным числом!");
        }
        List<LocalDate> unluckyFridays = new ArrayList<>();
        LocalDate endDate = LocalDate.of(year, Month.DECEMBER, LAST_DAY_OF_DECEMBER);
        LocalDate currentDate = LocalDate.of(year, Month.JANUARY, FIRST_DAY_OF_JANUARY);
        LocalDate nextUnluckyFriday = getNextUnluckyFriday(currentDate);
        while (nextUnluckyFriday.isBefore(endDate)) {
            unluckyFridays.add(nextUnluckyFriday);
            nextUnluckyFriday = getNextUnluckyFriday(nextUnluckyFriday);
        }
        return unluckyFridays.toArray(LocalDate[]::new);
    }

    public static LocalDate getNextUnluckyFriday(LocalDate currentDate) throws IllegalArgumentException {
        if (currentDate == null) {
            throw new IllegalArgumentException("Дата не может быть null!");
        }
        return currentDate.with((temporal -> {
            Temporal nextUnluckyFriday = temporal;
            int currentDayOfMonth = temporal.get(ChronoField.DAY_OF_MONTH);
            if (nextUnluckyFriday.get(ChronoField.DAY_OF_MONTH) < UNLUCKY_DAY_OF_MONTH) {
                nextUnluckyFriday = nextUnluckyFriday.plus(
                    UNLUCKY_DAY_OF_MONTH - currentDayOfMonth,
                    ChronoUnit.DAYS
                );
            } else {
                nextUnluckyFriday = nextUnluckyFriday.plus(1, ChronoUnit.MONTHS);
                nextUnluckyFriday = nextUnluckyFriday.minus(
                    currentDayOfMonth - UNLUCKY_DAY_OF_MONTH,
                    ChronoUnit.DAYS
                );
            }
            while (nextUnluckyFriday.get(ChronoField.DAY_OF_WEEK) != FRIDAY) {
                nextUnluckyFriday = nextUnluckyFriday.plus(1, ChronoUnit.MONTHS);
            }
            return nextUnluckyFriday;
        }));
    }
}
