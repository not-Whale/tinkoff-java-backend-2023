package edu.homework7.shared_counter;

import java.util.concurrent.atomic.AtomicLong;

public class SharedCounter {
    private final AtomicLong counter;

    public SharedCounter(long initialValue) {
        this.counter = new AtomicLong(initialValue);
    }

    public SharedCounter() {
        this(0);
    }

    public void increment() {
        counter.incrementAndGet();
    }

    public long getValue() {
        return counter.get();
    }
}
