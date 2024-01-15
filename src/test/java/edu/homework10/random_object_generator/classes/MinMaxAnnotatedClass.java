package edu.homework10.random_object_generator.classes;

import edu.homework10.random_object_generator.annotations.Max;
import edu.homework10.random_object_generator.annotations.Min;

public class MinMaxAnnotatedClass {
    private float value;

    public MinMaxAnnotatedClass(@Min(floatValue = -10) @Max(floatValue = 10) float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }
}
