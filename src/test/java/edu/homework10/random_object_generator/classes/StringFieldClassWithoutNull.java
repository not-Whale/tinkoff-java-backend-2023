package edu.homework10.random_object_generator.classes;

import edu.homework10.random_object_generator.annotations.NotNull;

public class StringFieldClassWithoutNull {
    private final String stringField;

    public StringFieldClassWithoutNull(@NotNull String stringField) {
        this.stringField = stringField;
    }

    public String getStringField() {
        return stringField;
    }
}
