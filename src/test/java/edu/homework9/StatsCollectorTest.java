package edu.homework9;

import edu.homework9.stats_collector.MetricType;
import edu.homework9.stats_collector.StatsCollector;
import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static edu.homework9.stats_collector.MetricType.AVG;
import static edu.homework9.stats_collector.MetricType.MAX;
import static edu.homework9.stats_collector.MetricType.MIN;
import static edu.homework9.stats_collector.MetricType.SUM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Многопоточная система сбора статистики.")
public class StatsCollectorTest {
    @Test
    @DisplayName("Данные записываются корректно.")
    void correctWrite() {
        // given
        StatsCollector statsCollector = new StatsCollector(true);
        var addAvgTask = getAddTask(statsCollector, 10_000, 50, AVG);
        var addSumTask = getAddTask(statsCollector, 50_000, 10, SUM);
        var addMinTask = getAddTask(statsCollector, 25_000, 20, MIN);
        var addMaxTask = getAddTask(statsCollector, 10_000, 50, MAX);

        // when
        ExecutorService executorService = Executors.newCachedThreadPool();
        assertDoesNotThrow(() -> {
            var futures = executorService.invokeAll(
                List.of(addAvgTask, addSumTask, addMaxTask, addMinTask));
            for (var future : futures) {
                future.get();
            }
        });
        executorService.close();

        // then
        assertThat(statsCollector.stats()).isNotNull().isNotEmpty();
        assertThat(statsCollector.stats().size()).isEqualTo(95_000);
    }

    @Test
    @DisplayName("Многопоточный вариант чтения.")
    void readParallel() {
        // given
        StatsCollector statsCollector = new StatsCollector(true);

        // when
        for (int i = 0; i < 1_000; i++) {
            SecureRandom random = new SecureRandom();
            double[] data = new double[10];
            for (int j = 0; j < 10; j++) {
                data[j] = random.nextDouble();
            }
            statsCollector.push(AVG, data);
            statsCollector.push(SUM, data);
            statsCollector.push(MIN, data);
            statsCollector.push(MAX, data);
        }

        // then
        ExecutorService executorService = Executors.newFixedThreadPool(8);
        var readTask = getReadTasks(statsCollector, 100_000);
        assertDoesNotThrow(() -> {
            var tasks = Stream.generate(() -> readTask).limit(16).toList();
            var futures = executorService.invokeAll(tasks);
            for (var future : futures) {
                future.get();
            }
        });
    }

    @Test
    @DisplayName("Однопоточный вариант чтения.")
    void readNotParallel() {
        // given
        StatsCollector statsCollector = new StatsCollector(true);

        // when
        for (int i = 0; i < 1_000; i++) {
            SecureRandom random = new SecureRandom();
            double[] data = new double[10];
            for (int j = 0; j < 10; j++) {
                data[j] = random.nextDouble();
            }
            statsCollector.push(AVG, data);
            statsCollector.push(SUM, data);
            statsCollector.push(MIN, data);
            statsCollector.push(MAX, data);
        }

        // then
        for (int i = 0; i < 1_600_000; i++) {
            for (var metric : statsCollector.stats()) {
                // some metrics analytics
            }
        }
    }

    private static Callable<Void> getReadTasks(StatsCollector statsCollector, int iter) {
        return () -> {
            for (int i = 0; i < iter; i++) {
                for (var metric : statsCollector.stats()) {
                    // some metrics analytics
                }
            }
            return null;
        };
    }

    private static Callable<Void> getAddTask(StatsCollector statsCollector, int iter, int dataSize, MetricType type) {
        return () -> {
            for (int i = 0; i < iter; i++) {
                ThreadLocalRandom random = ThreadLocalRandom.current();
                double[] data = new double[dataSize];
                for (int j = 0; j < dataSize; j++) {
                    data[j] = random.nextDouble();
                }
                statsCollector.push(type, data);
            }
            return null;
        };
    }
}
