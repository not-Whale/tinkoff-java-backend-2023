package edu.homework3.atbash_cipher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Шифр Атбаш для латинского алфавита.")
public class AtbashCipherTest {
    @Test
    @DisplayName("Ввод заглавных латинских букв.")
    void atbashMapCipherForUppercase() {
        // given
        String inputWord = "UPPERCASEWORD";

        // when
        String outputWord = AtbashCipher.decodeWordWithMap(inputWord);

        // then
        assertThat(outputWord).isEqualTo("FKKVIXZHVDLIW");
    }

    @Test
    @DisplayName("Ввод строчных латинских букв.")
    void atbashMapCipherForLowercase() {
        // given
        String inputWord = "lowercaseword";

        // when
        String outputWord = AtbashCipher.decodeWordWithMap(inputWord);

        // then
        assertThat(outputWord).isEqualTo("oldvixzhvdliw");
    }

    @Test
    @DisplayName("Ввод символов не из латинского алфавита.")
    void atbashMapCipherForIncorrectSymbols() {
        // given
        String inputWord = "Слова кириллицей 123";

        // when
        String outputWord = AtbashCipher.decodeWordWithMap(inputWord);

        // then
        assertThat(outputWord).isEqualTo("Слова кириллицей 123");
    }

    @Test
    @DisplayName("Комбинированный ввод.")
    void atbashMapCipherCombinedWord() {
        // given
        String inputWord = "Multilanguage строка с цифрами 123 и пробелами.\t\n";

        // when
        String outputWord = AtbashCipher.decodeWordWithMap(inputWord);

        // then
        assertThat(outputWord).isEqualTo("Nfogrozmtfztv строка с цифрами 123 и пробелами.\t\n");
    }

    @Test
    @DisplayName("Null-аргумент.")
    void atbashMapCipherForNullInput() {
        // given
        String inputWord = null;

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> AtbashCipher.decodeWordWithMap(inputWord)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
    }
}
