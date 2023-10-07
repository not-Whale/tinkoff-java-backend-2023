package edu.homework1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Исправление строки с переставленными соседними символами.")
public class BrokenStringTest {
    @Test
    @DisplayName("Happy path. Ввод: \"hTsii  s aimex dpus rtni.g\", вывод: \"This is a mixed up string.\".")
    void fixStringPassed() {
        // given
        String brokenString = "hTsii  s aimex dpus rtni.g";

        // when
        String fixedString = BrokenString.fixString(brokenString);

        // then
        assertThat(fixedString).isEqualTo("This is a mixed up string.");
    }

    @Test
    @DisplayName("Пустая строка. Ввод: \"\", вывод: \"\".")
    void fixStringEmpty() {
        // given
        String brokenString = "";

        // when
        String fixedString = BrokenString.fixString(brokenString);

        // then
        assertThat(fixedString).isEqualTo("");
    }

    @Test
    @DisplayName("Один символ. Ввод: \"A\", вывод: \"A\".")
    void fixStringOneChar() {
        // given
        String brokenString = "A";

        // when
        String fixedString = BrokenString.fixString(brokenString);

        // then
        assertThat(fixedString).isEqualTo("A");
    }

    @Test
    @DisplayName("Четная длина. Ввод: \"0123\", вывод: \"1032\".")
    void fixStringEven() {
        // given
        String brokenString = "0123";

        // when
        String fixedString = BrokenString.fixString(brokenString);

        // then
        assertThat(fixedString).isEqualTo("1032");
    }

    @Test
    @DisplayName("Нечетная длина. Ввод: \"0123456\", вывод: \"1032546\".")
    void fixStringOdd() {
        // given
        String brokenString = "0123456";

        // when
        String fixedString = BrokenString.fixString(brokenString);

        // then
        assertThat(fixedString).isEqualTo("1032546");
    }

    @Test
    @DisplayName("Null-строка. Ввод: \"0123456\", вывод: \"1032546\".")
    void fixStringNull() {
        // given
        String brokenString = null;

        // when
        String fixedString = BrokenString.fixString(brokenString);

        // then
        assertThat(fixedString).isEqualTo(null);
    }
}
