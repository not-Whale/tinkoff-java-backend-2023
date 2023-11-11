package edu.homework5.regex_excercises;

import edu.homework5.regex_excercies.RegexExPro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Строка начинается с 0 и имеет нечетную длину, или начинается с 1 и имеет четную длину.")
public class RegexExProTask2Test {
    @ParameterizedTest
    @ValueSource(strings = {
        "0",
        "000",
        "01111",
        "10",
        "1111"
    })
    @DisplayName("Валидная строка.")
    void validate(String input) {
        // given
        String inputString = input;

        // when
        boolean valid = RegexExPro.task2(inputString);

        // then
        assertThat(valid).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {
        "00",
        "",
        "1",
        "111"
    })
    @DisplayName("Невалидная строка.")
    void validateIncorrectString(String input) {
        // given
        String inputString = input;

        // when
        boolean valid = RegexExPro.task2(inputString);

        // then
        assertThat(valid).isFalse();
    }
}
