package edu.homework6.directory_stream_filter;

import java.nio.file.DirectoryStream;
import java.nio.file.Path;

public interface AbstractFilter extends DirectoryStream.Filter<Path> {
    String NULL_FILTER_EXCEPTION_MESSAGE = "Фильтр не может быть null!";

    default AbstractFilter and(AbstractFilter other) {
        if (other == null) {
            throw new IllegalArgumentException(NULL_FILTER_EXCEPTION_MESSAGE);
        }
        return (t) -> accept(t) && other.accept(t);
    }

    default AbstractFilter or(AbstractFilter other) {
        if (other == null) {
            throw new IllegalArgumentException(NULL_FILTER_EXCEPTION_MESSAGE);
        }
        return (t) -> accept(t) || other.accept(t);
    }

    default AbstractFilter negate() {
        return (t) -> !accept(t);
    }

    static AbstractFilter not(AbstractFilter target) {
        if (target == null) {
            throw new IllegalArgumentException(NULL_FILTER_EXCEPTION_MESSAGE);
        }
        return target.negate();
    }
}
