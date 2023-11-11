package edu.homework5.computer_club;

import java.time.Duration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Расчет средней длительности сеанса.")
public class ComputerClubAnalyticsTest {
    @Test
    @DisplayName("Средняя длительность сеанса до суток.")
    void calculateAverageGameTime() {
        // given
        String[] sessions = {
            "2022-03-12, 20:20 - 2022-03-12, 23:50",
            "2022-04-01, 21:30 - 2022-04-02, 01:20"
        };

        // when
        Duration averageSessionDuration = ComputerClubAnalytics.calculateAverageGameTime(sessions);

        // then
        assertThat(averageSessionDuration).isNotNull();
        assertThat(averageSessionDuration.toMillis()).isEqualTo((3 * 60 + 40) * 60 * 1000);
    }

    @Test
    @DisplayName("Средняя длительность сеанса более суток.")
    void calculateAverageGameTimeMoreThenOneDay() {
        // given
        String[] sessions = {
            "2022-03-10, 20:20 - 2022-03-12, 23:50",
            "2022-04-01, 21:30 - 2022-04-04, 01:20"
        };

        // when
        Duration averageSessionDuration = ComputerClubAnalytics.calculateAverageGameTime(sessions);

        // then
        assertThat(averageSessionDuration).isNotNull();
        assertThat(averageSessionDuration.toMillis()).isEqualTo(((48 + 3) * 60 + 40) * 60 * 1000);
    }

    @Test
    @DisplayName("Некорректный формат сессии.")
    void calculateAverageGameTimeIncorrectDurationFormat() {
        // given
        String[] sessions = {
            "from 2022-03-10, 20:20 to 2022-03-12, 23:50",
            "2022-04-01, 21:30 -- 2022-04-04, 01:20"
        };

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> ComputerClubAnalytics.calculateAverageGameTime(sessions)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Формат сессии: \"yyyy-MM-dd, hh:mm - yyyy-MM-dd, hh:mm\"!");
    }

    @Test
    @DisplayName("Некорректный формат даты.")
    void calculateAverageGameTimeIncorrectDateFormat() {
        // given
        String[] sessions = {
            "2022/03/10, 20:20 - 2022-03-12, 23:50",
            "2022-04-01, 21:30 - 2022.04.04, 01:20"
        };

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> ComputerClubAnalytics.calculateAverageGameTime(sessions)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Формат даты: \"yyyy-MM-dd, hh:mm\"");
    }

    @Test
    @DisplayName("Null-список.")
    void calculateAverageGameTimeNullSessions() {
        // given
        String[] sessions = null;

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> ComputerClubAnalytics.calculateAverageGameTime(sessions)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Список сессий не может быть null!");
    }
}
