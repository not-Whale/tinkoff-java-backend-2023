package edu.homework5.date_parser;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Распознавание различных форматов дат.")
public class MultiplyDateParserTest {
    @ParameterizedTest
    @ArgumentsSource(DateTypesProvider.class)
    @DisplayName("Поддерживаемые форматы.")
    void parseDate(String input, LocalDate answer) {
        // given
        String dateString = input;

        // when
        Optional<LocalDate> date = DateUtils.parseDate(dateString);

        // then
        assertThat(date).isNotNull().isNotEmpty();
        assertThat(date.get()).isEqualTo(answer);
    }

    static class DateTypesProvider implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) {
            return Stream.of(
                Arguments.of("2020-10-10", LocalDate.of(2020, 10, 10)),
                Arguments.of("2020-12-2", LocalDate.of(2020, 12, 2)),
                Arguments.of("1/3/1976", LocalDate.of(1976, 3, 1)),
                Arguments.of("1/3/20", LocalDate.of(2020, 3, 1)),
                Arguments.of("day after tomorrow", LocalDate.now().plusDays(2)),
                Arguments.of("tomorrow", LocalDate.now().plusDays(1)),
                Arguments.of("today", LocalDate.now()),
                Arguments.of("now", LocalDate.now()),
                Arguments.of("yesterday", LocalDate.now().minusDays(1)),
                Arguments.of("day before yesterday", LocalDate.now().minusDays(2)),
                Arguments.of("1 day ago", LocalDate.now().minusDays(1)),
                Arguments.of("2234 days ago", LocalDate.now().minusDays(2234))
            );
        }
    }

    @Test
    @DisplayName("Null-строка.")
    void parseDateNullDate() {
        // given
        String dateString = null;

        // when
        Optional<LocalDate> date = DateUtils.parseDate(dateString);

        // then
        assertThat(date).isNotNull().isEmpty();
    }

    @Test
    @DisplayName("Неподдерживаемый формат.")
    void parseDateIncorrectFormat() {
        // given
        String dateString = "10 NOV 2023";

        // when
        Optional<LocalDate> date = DateUtils.parseDate(dateString);

        // then
        assertThat(date).isNotNull().isEmpty();
    }
}
