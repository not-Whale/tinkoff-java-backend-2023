package edu.homework4;

import java.util.Objects;

public class ValidationError extends Error {
    private final String message;

    private final String field;

    ValidationError(String message, String field) {
        this.message = message;
        this.field = field;
    }

    public String message() {
        return this.message;
    }

    public String field() {
        return this.field;
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof ValidationError
            && ((ValidationError) other).field.equals(this.field)
            && ((ValidationError) other).message.equals(this.message));
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.field, this.message);
    }
}
