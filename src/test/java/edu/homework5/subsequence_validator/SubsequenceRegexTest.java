package edu.homework5.subsequence_validator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Поиск подпоследовательности в строке.")
public class SubsequenceRegexTest {
    @ParameterizedTest
    @CsvSource(value = {
        "achfdbaabgabcaabg, abc",
        "aaaaaaalkfgdbbbbbbbb, ab",
        "abc, abc"
    })
    @DisplayName("Подпоследовательность найдена.")
    void isSubsequence(String inputSequence, String inputSubsequence) {
        // given
        String sequence = inputSequence;
        String subsequence = inputSubsequence;

        // when
        boolean isSubsequence = SubsequenceRegex.isSubsequence(sequence, subsequence);

        // then
        assertThat(isSubsequence).isTrue();
    }

    @ParameterizedTest
    @CsvSource(value = {
        "abc, abbc",
        "abcd, cb",
        "adcd, efg"
    })
    @DisplayName("Подпоследовательность найдена.")
    void isSubsequenceNotSubsequence(String inputSequence, String inputSubsequence) {
        // given
        String sequence = inputSequence;
        String subsequence = inputSubsequence;

        // when
        boolean isSubsequence = SubsequenceRegex.isSubsequence(sequence, subsequence);

        // then
        assertThat(isSubsequence).isFalse();
    }
}
