package edu.homework5.unlucky_friday;

import java.time.LocalDate;
import java.time.Month;
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
        void findUnluckyFridaysByYear() {
            // given
            int year = 1925;

            // when
            LocalDate[] fridays = UnluckyFriday.findUnluckyFridaysByYear(year);

            // then
            assertThat(fridays).isNotNull().containsExactly(
                LocalDate.of(1925, 2, 13),
                LocalDate.of(1925, 3, 13),
                LocalDate.of(1925, 11, 13)
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
            // --

            // when
            Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> UnluckyFriday.getNextUnluckyFriday(null)
            );

            // then
            assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
            assertThat(exception.getMessage()).isEqualTo("Дата не может быть null!");
        }
    }
}
