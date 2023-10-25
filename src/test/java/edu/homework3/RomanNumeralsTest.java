package edu.homework3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Перевод числа в римскую систему счисления.")
public class RomanNumeralsTest {
    @ParameterizedTest
    @CsvSource(value = {
        "2, II",
        "6, VI",
        "13, XIII",
        "56, LVI",
        "202, CCII",
        "503, DIII",
        "3006, MMMVI"
    })
    @DisplayName("Перевод без вычитания из старшей цифры.")
    void convertToRomanWithoutSub(int input, String answer) {
        // given
        int decimalNumber = input;

        // when
        String romanNumber = RomanNumerals.convertToRoman(decimalNumber);

        // then
        assertThat(romanNumber).isEqualTo(answer);
    }

    @Test
    @DisplayName("Перевод с вычитанием из старшей цифры.")
    void convertToRomanWithSub() {
        // given
        int decimalNumber = 1999;

        // when
        String romanNumber = RomanNumerals.convertToRoman(decimalNumber);

        // then
        assertThat(romanNumber).isEqualTo("MCMXCIX");
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 4000})
    @DisplayName("Выход за пределы вычисляемого диапазона.")
    void convertToRomanIncorrectNumber(int input) {
        // given
        int decimalNumber = input;

        // when
        String romanNumber = RomanNumerals.convertToRoman(input);

        // then
        assertThat(romanNumber).isNull();
    }

    @Test
    @DisplayName("На вход подан нуль.")
    void convertToRomanZero() {
        // given
        int decimalNumber = 0;

        // when
        String romanNumber = RomanNumerals.convertToRoman(decimalNumber);

        // then
        assertThat(romanNumber).isEqualTo("N");
    }
}
