package edu.homework10.cache_proxy.classes;

public class FC implements FibCalculator {
    @Override
    public long fib(int number) {
        if (number < 1) {
            throw new IllegalArgumentException();
        }
        long first = 0;
        long second = 1;
        if (number == 1) {
            return first;
        }
        for (int i = 2; i < number; i++) {
            long next = first + second;
            first = second;
            second = next;
        }
        return second;
    }
}
