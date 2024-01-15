package edu.homework7.parallel_factorial;

import java.util.stream.LongStream;

public class ParallelFactorial {
    private ParallelFactorial() {}

    public static long calc(int base) {
        if (base < 0) {
            return 0;
        }
        return LongStream.rangeClosed(1, base).parallel().reduce(1L, (a, b) -> a * b);
    }
}
