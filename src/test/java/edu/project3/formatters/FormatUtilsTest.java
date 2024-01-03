package edu.project3.formatters;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Создание текстов отчетов в различных форматах.")
public class FormatUtilsTest {
    private static final String[] names = new String[] {
        "Первая", "Вторая", "Третья"
    };
    private static final String[] values = new String[] {
        "15", "20", "10"
    };
    private static final String statisticsName = "Метрика";
    private static final String valuesName = "Значение";

    @Test
    @DisplayName("В строковом формате.")
    void reportToString() {
        // given
        // ---

        // when
        String report = FormatUtils.reportToString(names, values, statisticsName, valuesName);

        // then
        assertThat(report).isNotNull().isEqualTo("""
            ┌───────┬────────┐
            │Метрика│Значение│
            ├───────┼────────┤
            |Первая │15      │
            ├───────┼────────┤
            |Вторая │20      │
            ├───────┼────────┤
            |Третья │10      │
            └───────┴────────┘
            """);
    }

    @Test
    @DisplayName("В формате markdown.")
    void reportToMarkdown() {
        // given
        // ---

        // when
        String report = FormatUtils.reportToMarkdown(names, values, statisticsName, valuesName);

        // then
        assertThat(report).isNotNull().isEqualTo("""
            |Метрика|Значение|
            |:-----:|:------:|
            |Первая |15      |
            |Вторая |20      |
            |Третья |10      |
            """);
    }

    @Test
    @DisplayName("В формате adoc.")
    void reportToAdoc() {
        // given
        // ---

        // when
        String report = FormatUtils.reportToADOC(names, values, statisticsName, valuesName);

        // then
        assertThat(report).isNotNull().isEqualTo("""
            cols="1,1"
            |===
            |Метрика |Значение

            |Первая
            |15

            |===
            |Вторая
            |20

            |===
            |Третья
            |10
            |===
            """);
    }

    @Test
    @DisplayName("На вход вместо names подан null.")
    void reportWithNullNames() {
        // given
        String[] nullNames = null;

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> FormatUtils.reportToString(nullNames, values, statisticsName, valuesName)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).hasMessage("Names must not be null!");
    }

    @Test
    @DisplayName("На вход вместо values подан null.")
    void reportWithNullValues() {
        // given
        String[] nullValues = null;

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> FormatUtils.reportToString(names, nullValues, statisticsName, valuesName)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).hasMessage("Values must not be null!");
    }

    @Test
    @DisplayName("На вход вместо заголовка для names подан null.")
    void reportWithNullStatisticsName() {
        // given
        String nullTitle = null;

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> FormatUtils.reportToString(names, values, nullTitle, valuesName)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).hasMessage("Statistic name must not be null!");
    }

    @Test
    @DisplayName("На вход вместо заголовка для values подан null.")
    void reportWithNullValuesName() {
        // given
        String nullTitle = null;

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> FormatUtils.reportToString(names, values, statisticsName, nullTitle)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).hasMessage("Values name must not be null!");
    }

    @Test
    @DisplayName("Несоответствие между количеством ключей и значений.")
    void unmatchedNumberOfNamesAndValues() {
        // given
        String[] newNames = new String[] {
            "один", "два", "три"
        };
        String[] newValues = new String[] {
            "четыре", "пять"
        };

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> FormatUtils.reportToString(newNames, newValues, statisticsName, valuesName)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).hasMessage("Column's names must match values!");
    }
}
