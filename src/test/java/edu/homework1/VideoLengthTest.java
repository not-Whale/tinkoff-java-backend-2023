package edu.homework1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Перевод времени видео в секунды.")
public class VideoLengthTest {
    @Test
    @DisplayName("Happy path. Ввод: \"01:01\", вывод: 61.")
    void minutesToSecondsPassed() {
        // given
        String videoLength = "01:01";

        // when
        int seconds = VideoLength.minutesToSeconds(videoLength);

        // then
        assertThat(seconds).isEqualTo(61);
    }

    @Test
    @DisplayName("Все нули. Ввод: \"00:00\", вывод: 0.")
    void minutesToSecondsAllZeros() {
        // given
        String videoLength = "00:00";

        // when
        int seconds = VideoLength.minutesToSeconds(videoLength);

        // then
        assertThat(seconds).isEqualTo(0);
    }

    @Test
    @DisplayName("Секунд больше 59. Ввод: \"01:68\", вывод: -1.")
    void minutesToSecondsMoreThan60Seconds() {
        // given
        String videoLength = "01:68";

        // when
        int seconds = VideoLength.minutesToSeconds(videoLength);

        // then
        assertThat(seconds).isEqualTo(-1);
    }

    @Test
    @DisplayName("Минуты трехзначные. Ввод: \"999:01\", вывод: 59941.")
    void minutesToSecondsThreeDigitMinutes() {
        // given
        String videoLength = "999:01";

        // when
        int seconds = VideoLength.minutesToSeconds(videoLength);

        // then
        assertThat(seconds).isEqualTo(59941);
    }

    @Test
    @DisplayName("Отрицательное число. Ввод: \"-5:00\", вывод: -1.")
    void minutesToSecondsNegInput() {
        // given
        String videoLength = "-5:00";

        // when
        int seconds = VideoLength.minutesToSeconds(videoLength);

        // then
        assertThat(seconds).isEqualTo(-1);
    }

    @Test
    @DisplayName("Строка вместо числа. Ввод: \"ff:00\". Ожидается NumberFormatException.")
    void minutesToSecondsLetterInsteadOfNumber() {
        // given
        String videoLength = "ff:00";

        // when
        Exception exception = Assertions.assertThrows(
            NumberFormatException.class,
            () -> VideoLength.minutesToSeconds(videoLength)
        );

        // then
        assertThat(exception.getMessage()).isEqualTo("For input string: \"ff\"");
    }

    @Test
    @DisplayName("Время с часами. Ввод: \"01:15:00\", вывод: -1.")
    void minutesToSecondsIncorrectFormatWithHours() {
        // given
        String videoLength = "01:15:00";

        // when
        int seconds = VideoLength.minutesToSeconds(videoLength);

        // then
        assertThat(seconds).isEqualTo(-1);
    }

    @Test
    @DisplayName("Время без минут. Ввод: \"05\", вывод: -1.")
    void minutesToSecondsIncorrectFormatWithoutMinutes() {
        // given
        String videoLength = "05";

        // when
        int seconds = VideoLength.minutesToSeconds(videoLength);

        // then
        assertThat(seconds).isEqualTo(-1);
    }

    @Test
    @DisplayName("Null вместо строки. Ввод: \"null\". Ожидается IllegalArgumentException.")
    void minutesToSecondsNullString() {
        // given
        String videoLength = null;

        // when
        Exception exception = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> VideoLength.minutesToSeconds(videoLength)
        );

        // then
        assertThat(exception.getMessage()).isEqualTo("Null string!");
    }
}
