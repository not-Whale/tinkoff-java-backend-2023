package edu.homework5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Валидация пароля.")
public class PasswordValidatorTest {
    @Nested
    @DisplayName("Только один символ.")
    class HasOnlyOneSymbolTest {
        @ParameterizedTest
        @ValueSource(strings = {
            "password~",
            "passwor!d",
            "passwo@rd",
            "passw#ord",
            "pass$word",
            "pas%sword",
            "pa^ssword",
            "p&assword",
            "*password",
            "|"
        })
        @DisplayName("Соответствие.")
        void validate(String input) {
            // given
            String password = input;

            // when
            boolean valid = PasswordValidator.hasOnlyOneSymbol(password);

            // then
            assertThat(valid).isTrue();
        }

        @Test
        @DisplayName("Несколько искомых символов.")
        void validateNotOnlyOne() {
            // given
            String password = "pas|swo^rd";

            // when
            boolean valid = PasswordValidator.hasOnlyOneSymbol(password);

            // then
            assertThat(valid).isFalse();
        }

        @Test
        @DisplayName("Нет искомых символов.")
        void validateNotContainsTargetSymbols() {
            // given
            String password = "password";

            // when
            boolean valid = PasswordValidator.hasOnlyOneSymbol(password);

            // then
            assertThat(valid).isFalse();
        }
    }

    @Nested
    @DisplayName("Хотя бы один символ.")
    class HasAtLeastOneSymbol {
        @ParameterizedTest
        @ValueSource(strings = {
            "password~",
            "passwor!@d",
            "passwo#$%rd",
            "pa^&s*swo|rd"
        })
        @DisplayName("Соответствие.")
        void validate(String input) {
            // given
            String password = input;

            // when
            boolean valid = PasswordValidator.hasAtLeastOneSymbol(password);

            // then
            assertThat(valid).isTrue();
        }

        @Test
        @DisplayName("Нет искомых символов.")
        void validateNotContainsTargetSymbols() {
            // given
            String password = "password";

            // when
            boolean valid = PasswordValidator.hasAtLeastOneSymbol(password);

            // then
            assertThat(valid).isFalse();
        }
    }
}
