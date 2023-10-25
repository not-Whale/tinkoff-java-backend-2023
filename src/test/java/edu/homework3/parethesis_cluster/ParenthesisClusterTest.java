package edu.homework3.parethesis_cluster;

import edu.homework3.parenthesis_cluster.ParenthesisCluster;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Кластеризация скобок.")
public class ParenthesisClusterTest {
    @ParameterizedTest
    @ArgumentsSource(CorrectParenSeqProvider.class)
    @DisplayName("Правильные скобочные последовательности.")
    void clusterizeCorrectParenSeq(String input, String[] answer) {
        // given
        String parenSeq = input;

        // when
        String[] clusterizedParenSeq = ParenthesisCluster.clusterize(parenSeq);

        // then
        assertThat(clusterizedParenSeq).isNotNull().isEqualTo(answer);
    }

    static class CorrectParenSeqProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                Arguments.of("()()()", new String[] {"()", "()", "()"}),
                Arguments.of("((()))", new String[] {"((()))"}),
                Arguments.of("((()))(())()()(()())", new String[] {"((()))", "(())", "()", "()", "(()())"}),
                Arguments.of("((())())(()(()()))", new String[] {"((())())", "(()(()()))"})
            );
        }
    }

    @ParameterizedTest
    @ArgumentsSource(IncorrectParenSeqProvider.class)
    @DisplayName("Неправильные скобочные последовательности.")
    void clusterizeIncorrectParenSeq(String input) {
        // given
        String parenSeq = input;

        // when
        String[] clusterizedParenSeq = ParenthesisCluster.clusterize(parenSeq);

        // then
        assertThat(clusterizedParenSeq).isNull();
    }

    static class IncorrectParenSeqProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                Arguments.of("((()"),
                Arguments.of("()))"),
                Arguments.of("(())()(()((()))"),
                Arguments.of("("),
                Arguments.of(")")
            );
        }
    }

    @Test
    @DisplayName("Пустая последовательность.")
    void clusterizeEmptySeq() {
        // given
        String parenSeq = "";
        String[] emptyStringArray = new String[] {};

        // when
        String[] clusterizedParenSeq = ParenthesisCluster.clusterize(parenSeq);

        // then
        assertThat(clusterizedParenSeq).isNotNull().isEqualTo(emptyStringArray);
    }

    @Test
    @DisplayName("Null-аргумент")
    void clusterizeNullInput() {
        // given
        String parenSeq = null;

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> ParenthesisCluster.clusterize(parenSeq)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
    }
}
