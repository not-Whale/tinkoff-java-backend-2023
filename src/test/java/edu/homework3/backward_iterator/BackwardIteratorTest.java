package edu.homework3.backward_iterator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.NoSuchElementException;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Обратный итератор.")
public class BackwardIteratorTest {
    @Test
    @DisplayName("Возврат значений в обратном порядке.")
    void backwardIterator() {
        // given
        List<Integer> list = List.of(1, 2, 3);

        // when
        BackwardIterator<Integer> backwardIterator = new BackwardIterator<>(list);

        // then
        assertThat(backwardIterator.next()).isEqualTo(3);
        assertThat(backwardIterator.next()).isEqualTo(2);
        assertThat(backwardIterator.next()).isEqualTo(1);
    }

    @Test
    @DisplayName("Вызов next() от пустой коллекции.")
    void backwardIteratorEmptyNext() {
        // given
        List<String> list = List.of();
        BackwardIterator<String> backwardIterator = new BackwardIterator<>(list);

        // when
        Exception exception = assertThrows(
            NoSuchElementException.class,
            backwardIterator::next
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(NoSuchElementException.class);
    }
}
