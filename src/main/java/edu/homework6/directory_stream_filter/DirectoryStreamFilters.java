package edu.homework6.directory_stream_filter;

import java.io.FileInputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;

public class DirectoryStreamFilters {
    private DirectoryStreamFilters() {}

    public static AbstractFilter isDirectory() {
        return Files::isDirectory;
    }

    public static AbstractFilter isRegularFile() {
        return Files::isRegularFile;
    }

    public static AbstractFilter isReadable() {
        return Files::isReadable;
    }

    public static AbstractFilter isWritable() {
        return Files::isWritable;
    }

    public static AbstractFilter isExecutable() {
        return Files::isExecutable;
    }

    public static AbstractFilter isHidden() {
        return Files::isHidden;
    }

    public static AbstractFilter largerThan(long size) {
        return (path) -> (Files.size(path) > size);
    }

    public static AbstractFilter smallerThan(long size) {
        return (path) -> (Files.size(path) < size);
    }

    public static AbstractFilter hasSize(long size) {
        return (path) -> (Files.size(path) == size);
    }

    public static AbstractFilter hasExtension(String extension) {
        return (path) -> FilenameUtils.isExtension(path.toFile().getAbsolutePath(), extension);
    }

    public static AbstractFilter nameMatches(String regex) {
        return (path) -> Pattern.matches(regex, path.toFile().getName());
    }

    public static AbstractFilter pathMatcher(String regex) {
        return (path) -> Pattern.matches(regex, path.toFile().getPath());
    }

    public static AbstractFilter magicNumbers(int...magicNumbers) {
        return (path) -> {
            try (FileInputStream fileInputStream = new FileInputStream(path.toFile())) {
                for (int magicNumber : magicNumbers) {
                    if (fileInputStream.read() != magicNumber) {
                        return false;
                    }
                }
                return true;
            }
        };
    }
}
