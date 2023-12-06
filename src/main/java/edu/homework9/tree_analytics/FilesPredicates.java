package edu.homework9.tree_analytics;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;
import org.apache.commons.io.FilenameUtils;

public class FilesPredicates {
    private FilesPredicates() {}

    public static Predicate<Path> largerThan(long size) {
        return (Path path) -> {
            try {
                return (Files.size(path) > size);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static Predicate<Path> smallerThan(long size) {
        return (Path path) -> {
            try {
                return (Files.size(path) < size);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static Predicate<Path> hasSize(long size) {
        return (Path path) -> {
            try {
                return (Files.size(path) == size);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

    public static Predicate<Path> hasExtension(String extension) {
        return (Path path) -> {
            if (path.toFile().isDirectory()) {
                return false;
            }
            return FilenameUtils.getExtension(path.toString()).equals(extension);
        };
    }
}
