package edu.homework7.shared_counter;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Несколько потоков увеличивают счетчик.")
public class SharedCounterTest {
    @Test
    @DisplayName("Для 8 потоков по 1_000_000 раз каждый.")
    void incrementCounter() throws InterruptedException, ExecutionException {
        // given
        SharedCounter counter = new SharedCounter();
        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            Callable<Void> inc = () -> {
                for (int i = 0; i < 1_000_000; i++) {
                    counter.increment();
                }
                return null;
            };
            // when
            var incTasks = Stream.generate(() -> inc).limit(8).toList();
            List<Future<Void>> incFutures = executorService.invokeAll(incTasks);
            for (var future : incFutures) {
                future.get();
            }
            executorService.shutdown();
        }
        // then
        assertThat(counter.getValue()).isEqualTo(8_000_000);
    }
}
