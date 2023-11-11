package edu.homework5.regex_excercises;

import edu.homework5.regex_excercies.RegexExPro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

public @DisplayName("Каждый нечетный символ строки равен 1.")
class RegexExProTask5Test {
    @ParameterizedTest
    @ValueSource(strings = {
        "1",
        "101",
        "11111",
        "1010111"
    })
    @DisplayName("Валидная строка.")
    void validate(String input) {
        // given
        String inputString = input;

        // when
        boolean valid = RegexExPro.task5(inputString);

        // then
        assertThat(valid).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "0",
        "001",
        "00101",
        "000",
        "00"
    })
    @DisplayName("Невалидная строка.")
    void validateIncorrectString(String input) {
        // given
        String inputString = input;

        // when
        boolean valid = RegexExPro.task5(inputString);

        // then
        assertThat(valid).isFalse();
    }
}
