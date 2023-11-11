package edu.homework5.regex_excercises;

import edu.homework5.regex_excercies.RegexExPro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Любая строка, кроме 11 или 111.")
public class RegexExProTask4Test {
    @ParameterizedTest
    @ValueSource(strings = {
        "",
        "1",
        "0",
        "01",
        "00",
        "000",
        "001",
        "010",
        "011",
        "0111",
        "101",
        "110",
        "1110"
    })
    @DisplayName("Валидная строка.")
    void validate(String input) {
        // given
        String inputString = input;

        // when
        boolean valid = RegexExPro.task4(inputString);

        // then
        assertThat(valid).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "11",
        "111"
    })
    @DisplayName("Невалидная строка.")
    void validateIncorrectString(String input) {
        // given
        String inputString = input;

        // when
        boolean valid = RegexExPro.task4(inputString);

        // then
        assertThat(valid).isFalse();
    }
}
