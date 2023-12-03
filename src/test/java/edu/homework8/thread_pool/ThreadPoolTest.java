package edu.homework8.thread_pool;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Собственный FixedThreadPool.")
public class ThreadPoolTest {
    @ParameterizedTest
    @CsvSource(value = {
        "0, 0",
        "1, 1",
        "2, 1",
        "3, 2",
        "4, 3",
        "5, 5",
        "6, 8",
        "7, 13",
        "8, 21",
        "9, 34",
        "10, 55",
        "11, 89",
        "12, 144",
        "13, 233",
        "14, 377",
        "15, 610",
        "16, 987",
        "17, 1597",
        "18, 2584",
        "19, 4181",
        "20, 6765",
        "21, 10946",
        "22, 17711",
        "23, 28657",
        "24, 46368",
        "25, 75025",
        "26, 121393",
        "27, 196418",
        "28, 317811",
        "29, 514229",
        "30, 832040",
        "31, 1346269",
        "32, 2178309",
        "33, 3524578",
        "34, 5702887",
        "35, 9227465",
        "36, 14930352",
        "37, 24157817",
        "38, 39088169",
        "39, 63245986",
        "40, 102334155",
        "41, 165580141",
        "42, 267914296",
        "43, 433494437",
        "44, 701408733",
        "45, 1134903170",
        "46, 1836311903",
        "47, 2971215073",
        "48, 4807526976",
        "49, 7778742049",
        "50, 12586269025"
    })
    @DisplayName("Параллельное вычисление чисел Фибоначчи.")
    void fibonacciCalculating(int number, long fibonacci) {
        ThreadPool threadPool = FixedThreadPool.create(8);
        threadPool.start();
        for (int i = 0; i < 50; i++) {
            threadPool.execute(new Fibonacci(number, fibonacci));
        }
        try {
            threadPool.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static class Fibonacci implements Runnable {
        private final int number;

        private final long fibonacci;

        public Fibonacci(int number, long fibonacci) {
            this.number = number;
            this.fibonacci = fibonacci;
        }

        @Override
        public void run() {
            long fibonacci = switch (number) {
                case 0 -> 0;
                case 1 -> 1;
                default -> {
                    long first = 0;
                    long second = 1;
                    long third = 1;
                    int counter = 2;
                    while (counter != number) {
                        first = second;
                        second = third;
                        third = first + second;
                        counter++;
                    }
                    yield third;
                }
            };
            assertThat(fibonacci).isEqualTo(this.fibonacci);
        }
    }
}
