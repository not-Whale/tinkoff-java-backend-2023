package edu.homework10.cache_proxy;

import edu.homework10.cache_proxy.classes.FC;
import edu.homework10.cache_proxy.classes.FCDisk;
import edu.homework10.cache_proxy.classes.FibCalculator;
import edu.homework10.cache_proxy.classes.FibDiskCalculator;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class CacheProxyTest {
    private static final Path CACHE_PATH = Path.of("src", "main", "resources", "homework10");

    @Test
    void fibonacciDisk() {
        FCDisk fibonacciCalculator1 = new FCDisk();
        FCDisk fibonacciCalculator2 = new FCDisk();
        try (CacheProxy cacheProxy = new CacheProxy()) {
            FibDiskCalculator proxy1 = (FibDiskCalculator) cacheProxy.create(
                fibonacciCalculator1,
                FibDiskCalculator.class
            );
            assertThat(CACHE_PATH.toFile().list().length).isEqualTo(1);

            proxy1.fib(10);
            proxy1.fib(10);
            assertThat(CACHE_PATH.toFile().list().length).isEqualTo(1);

            FibDiskCalculator proxy2 = (FibDiskCalculator) cacheProxy.create(
                fibonacciCalculator2,
                FibDiskCalculator.class
            );
            assertThat(CACHE_PATH.toFile().list().length).isEqualTo(2);

            proxy1.fib(5);
            proxy1.fib(6);
            assertThat(CACHE_PATH.toFile().list().length).isEqualTo(2);
        }
        assertThat(CACHE_PATH.toFile().exists()).isFalse();
    }

    @Test
    void fibonacci() {
        FC fibonacciCalculator1 = new FC();
        FC fibonacciCalculator2 = new FC();
        try (CacheProxy cacheProxy = new CacheProxy()) {
            FibCalculator proxy1 = (FibCalculator) cacheProxy.create(
                fibonacciCalculator1,
                FibCalculator.class
            );
            assertThat(CACHE_PATH.toFile().exists()).isFalse();

            proxy1.fib(10);
            proxy1.fib(10);
            assertThat(CACHE_PATH.toFile().exists()).isFalse();

            FibCalculator proxy2 = (FibCalculator) cacheProxy.create(
                fibonacciCalculator2,
                FibCalculator.class
            );
            assertThat(CACHE_PATH.toFile().exists()).isFalse();

            proxy1.fib(5);
            proxy1.fib(6);
            assertThat(CACHE_PATH.toFile().exists()).isFalse();
        }
        assertThat(CACHE_PATH.toFile().exists()).isFalse();
    }
}
