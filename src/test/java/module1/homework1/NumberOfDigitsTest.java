package module1.homework1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Подсчет количества цифр в числе.")
public class NumberOfDigitsTest {
    @Test
    @DisplayName("Happy path. Ввод: 4666, вывод: 4.")
    void countDigitsPassed() {
        // given
        int number = 4666;

        // when
        int numberOfDigits = NumberOfDigits.countDigits(number);

        // then
        assertThat(numberOfDigits).isEqualTo(4);
    }

    @Test
    @DisplayName("Нуль на входе. Ввод: 0, вывод: 0.")
    void countDigitsZero() {
        // given
        int number = 0;

        // when
        int numberOfDigits = NumberOfDigits.countDigits(number);

        // then
        assertThat(numberOfDigits).isEqualTo(0);
    }

    @Test
    @DisplayName("Одна цифра. Ввод: 2, вывод: 1.")
    void countDigitsOneDigit() {
        // given
        int number = 2;

        // when
        int numberOfDigits = NumberOfDigits.countDigits(number);

        // then
        assertThat(numberOfDigits).isEqualTo(1);
    }

    @Test
    @DisplayName("Отрицательное число. Ввод: -151, вывод: 3.")
    void countDigitsNegative() {
        // given
        int number = -151;

        // when
        int numberOfDigits = NumberOfDigits.countDigits(number);

        // then
        assertThat(numberOfDigits).isEqualTo(3);
    }
}
