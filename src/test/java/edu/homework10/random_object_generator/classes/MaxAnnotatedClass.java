package edu.homework10.random_object_generator.classes;

import edu.homework10.random_object_generator.annotations.Max;

public class MaxAnnotatedClass {
    private long value;

    public MaxAnnotatedClass(@Max(longValue = 100) long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }
}
