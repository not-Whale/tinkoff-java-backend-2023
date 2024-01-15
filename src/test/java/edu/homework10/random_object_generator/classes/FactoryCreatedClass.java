package edu.homework10.random_object_generator.classes;

public class FactoryCreatedClass {
    private final boolean booleanField;

    private final char charField;

    private FactoryCreatedClass(boolean booleanField, char charField) {
        this.booleanField = booleanField;
        this.charField = charField;
    }

    public static FactoryCreatedClass create(boolean booleanField, char charField) {
        return new FactoryCreatedClass(booleanField, charField);
    }
}
