package module1.homework1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Определение особого палиндрома.")
public class SpecialPalindromeTest {
    @Test
    @DisplayName("Ноль. Ввод: 0, вывод: true.")
    void isPalindromeDescendantZero() {
        // given
        int number = 0;

        // when
        boolean isSpecialPalindrome = SpecialPalindrome.isPalindromeDescendant(number);

        // then
        assertThat(isSpecialPalindrome).isEqualTo(true);
    }

    @Test
    @DisplayName("Одна цифра. Ввод: 1, вывод: true.")
    void isPalindromeDescendantOneDigit() {
        // given
        int number = 1;

        // when
        boolean isSpecialPalindrome = SpecialPalindrome.isPalindromeDescendant(number);

        // then
        assertThat(isSpecialPalindrome).isEqualTo(true);
    }

    @Test
    @DisplayName("Палиндром длины 2. Ввод: 11, вывод: true.")
    void isPalindromeDescendantTwoDigitsTrue() {
        // given
        int number = 11;

        // when
        boolean isSpecialPalindrome = SpecialPalindrome.isPalindromeDescendant(number);

        // then
        assertThat(isSpecialPalindrome).isEqualTo(true);
    }

    @Test
    @DisplayName("Непалиндром длины 2. Ввод: 12, вывод: false.")
    void isPalindromeDescendantTwoDigitsFalse() {
        // given
        int number = 12;

        // when
        boolean isSpecialPalindrome = SpecialPalindrome.isPalindromeDescendant(number);

        // then
        assertThat(isSpecialPalindrome).isEqualTo(false);
    }

    @Test
    @DisplayName("Четный палиндром. Ввод: 11211230, вывод: true.")
    void isPalindromeDescendantPassedTrue() {
        // given
        int number = 11211230;

        // when
        boolean isSpecialPalindrome = SpecialPalindrome.isPalindromeDescendant(number);

        // then
        assertThat(isSpecialPalindrome).isEqualTo(true);
    }

    @Test
    @DisplayName("Четный непалиндром.. Ввод: 11211231, вывод: false.")
    void isPalindromeDescendantPassedFalse() {
        // given
        int number = 11211231;

        // when
        boolean isSpecialPalindrome = SpecialPalindrome.isPalindromeDescendant(number);

        // then
        assertThat(isSpecialPalindrome).isEqualTo(false);
    }

    @Test
    @DisplayName("Нечетный палиндром. Ввод: 11211, вывод: true.")
    void isPalindromeDescendantOddTrue() {
        // given
        int number = 11211;

        // when
        boolean isSpecialPalindrome = SpecialPalindrome.isPalindromeDescendant(number);

        // then
        assertThat(isSpecialPalindrome).isEqualTo(true);
    }

    @Test
    @DisplayName("Нечетный непалиндром, но специальный палиндром. Ввод: 13224, вывод: false.")
    void isPalindromeDescendantOddFalse() {
        // given
        int number = 13224;

        // when
        boolean isSpecialPalindrome = SpecialPalindrome.isPalindromeDescendant(number);

        // then
        assertThat(isSpecialPalindrome).isEqualTo(false);
    }
}
