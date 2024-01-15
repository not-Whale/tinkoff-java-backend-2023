package edu.homework5.regex_excercises;

import edu.homework5.regex_excercies.RegexEx;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Регулярные выражения для строк из алфавита {0,1}.")
public class RegexExTest {
    @Nested
    @DisplayName("Строка содержит не менее 3 символов, причем третий символ равен 0.")
    class Task1Test {
        @ParameterizedTest
        @ValueSource(strings = {
            "010",
            "110111",
            "000",
            "110"
        })
        @DisplayName("Валидная строка.")
        void validate(String input) {
            // given
            String inputString = input;

            // when
            boolean valid = RegexEx.task1(inputString);

            // then
            assertThat(valid).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {
            "00",
            "10",
            "00100"
        })
        @DisplayName("Невалидная строка.")
        void validateIncorrectString(String input) {
            // given
            String inputString = input;

            // when
            boolean valid = RegexEx.task1(inputString);

            // then
            assertThat(valid).isFalse();
        }
    }

    @Nested
    @DisplayName("Строка начинается и заканчивается одним и тем же символом.")
    class Task2Test {
        @ParameterizedTest
        @ValueSource(strings = {
            "",
            "0",
            "1",
            "00",
            "010",
            "101010101"
        })
        @DisplayName("Валидная строка.")
        void validate(String input) {
            // given
            String inputString = input;

            // when
            boolean valid = RegexEx.task2(inputString);

            // then
            assertThat(valid).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {
            "01",
            "1000"
        })
        @DisplayName("Невалидная строка.")
        void validateIncorrectString(String input) {
            // given
            String inputString = input;

            // when
            boolean valid = RegexEx.task2(inputString);

            // then
            assertThat(valid).isFalse();
        }
    }

    @Nested
    @DisplayName("Длина строки не менее 1 и не более 3")
    class Task3Test {
        @ParameterizedTest
        @ValueSource(strings = {
            "0",
            "01",
            "101"
        })
        @DisplayName("Валидная строка.")
        void validate(String input) {
            // given
            String inputString = input;

            // when
            boolean valid = RegexEx.task3(inputString);

            // then
            assertThat(valid).isTrue();
        }

        @ParameterizedTest
        @ValueSource(strings = {
            "",
            "0101",
            "00000"
        })
        @DisplayName("Невалидная строка.")
        void validateIncorrectString(String input) {
            // given
            String inputString = input;

            // when
            boolean valid = RegexEx.task3(inputString);

            // then
            assertThat(valid).isFalse();
        }
    }
}
