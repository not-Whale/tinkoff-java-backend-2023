package edu.homework5.regex_excercises;

import edu.homework5.regex_excercies.RegexExPro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("В строке нет последовательных 1.")
public class RegexExProTask7Test {
    @ParameterizedTest
    @ValueSource(strings = {
        "0",
        "1",
        "000001000101010101010",
        "0001",
        "1000"
    })
    @DisplayName("Валидная строка.")
    void validate(String input) {
        // given
        String inputString = input;

        // when
        boolean valid = RegexExPro.task7(inputString);

        // then
        assertThat(valid).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "11",
        "0000110001010",
        "110",
        "011"
    })
    @DisplayName("Невалидная строка.")
    void validateIncorrectString(String input) {
        // given
        String inputString = input;

        // when
        boolean valid = RegexExPro.task7(inputString);

        // then
        assertThat(valid).isFalse();
    }
}
