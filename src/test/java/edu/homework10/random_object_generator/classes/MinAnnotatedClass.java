package edu.homework10.random_object_generator.classes;

import edu.homework10.random_object_generator.annotations.Min;

public class MinAnnotatedClass {
    private int value;

    public MinAnnotatedClass(@Min(intValue = 100) int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
