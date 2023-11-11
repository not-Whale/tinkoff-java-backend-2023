package edu.homework5.unlucky_friday;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Пятницы, выпадающие на 13 число.")
public class UnluckyFridayTest {
    @Nested
    @DisplayName("Пятницы в заданном году.")
    class FindUnluckyFridaysByYear {
        @Test
        @DisplayName("За 1925 год.")
        void findUnluckyFridaysByYear() throws ParseException {
            // given
            int year = 1925;

            // when
            Date[] fridays = UnluckyFriday.findUnluckyFridaysByYear(year);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // then
            assertThat(fridays).isNotNull().containsExactly(
                simpleDateFormat.parse("1925-02-13"),
                simpleDateFormat.parse("1925-03-13"),
                simpleDateFormat.parse("1925-11-13")
            );
        }

        @Test
        @DisplayName("Год отрицательный.")
        void findUnluckyFridaysByYearNonPositiveYear() {
            // given
            int year = 0;

            // when
            Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> UnluckyFriday.findUnluckyFridaysByYear(year)
            );

            // then
            assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
            assertThat(exception.getMessage()).isEqualTo("Год должен быть положительным числом!");
        }
    }

    @Nested
    @DisplayName("Следующая пятница.")
    class GetNextUnluckyFriday {
        @Test
        @DisplayName("Для 11 ноября 2023 года.")
        void getNextUnluckyFriday() {
            // given
            LocalDate date = LocalDate.of(2023, Month.NOVEMBER, 9);

            // when
            LocalDate friday = UnluckyFriday.getNextUnluckyFriday(date);

            // then
            assertThat(friday).isNotNull().isEqualTo(LocalDate.of(2024, Month.SEPTEMBER, 13));
        }

        @Test
        @DisplayName("Null-дата.")
        void getNextUnluckyFridayNullDate() {
            // given
            LocalDate date = null;

            // when
            Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> UnluckyFriday.getNextUnluckyFriday(date)
            );

            // then
            assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
            assertThat(exception.getMessage()).isEqualTo("Дата не может быть null!");
        }
    }
}
