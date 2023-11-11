package edu.homework5.regex_excercises;

import edu.homework5.regex_excercies.RegexExPro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Количество 0 в строке кратно 3.")
public class RegexExProTask3Test {
    @ParameterizedTest
    @ValueSource(strings = {
        "1",
        "101111001",
        "000000",
        "100011000111000"
    })
    @DisplayName("Валидная строка.")
    void validate(String input) {
        // given
        String inputString = input;

        // when
        boolean valid = RegexExPro.task3(inputString);

        // then
        assertThat(valid).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "01",
        "1001",
        "0000"
    })
    @DisplayName("Невалидная строка.")
    void validateIncorrectString(String input) {
        // given
        String inputString = input;

        // when
        boolean valid = RegexExPro.task3(inputString);

        // then
        assertThat(valid).isFalse();
    }
}
