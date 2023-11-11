package edu.homework5.regex_excercises;

import edu.homework5.regex_excercies.RegexExPro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Строка содержит не менее двух 0 и не более одной 1.")
public class RegexExProTask6Test {
    @ParameterizedTest
    @ValueSource(strings = {
        "00",
        "00000",
        "001",
        "100",
        "0000001000"
    })
    @DisplayName("Валидная строка.")
    void validate(String input) {
        // given
        String inputString = input;

        // when
        boolean valid = RegexExPro.task6(inputString);

        // then
        assertThat(valid).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "01",
        "0011",
        "011"
    })
    @DisplayName("Невалидная строка.")
    void validateIncorrectString(String input) {
        // given
        String inputString = input;

        // when
        boolean valid = RegexExPro.task6(inputString);

        // then
        assertThat(valid).isFalse();
    }
}
