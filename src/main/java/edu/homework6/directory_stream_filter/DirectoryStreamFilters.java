package edu.homework6.directory_stream_filter;

import java.io.FileInputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Pattern;
import org.apache.commons.io.FilenameUtils;

public class DirectoryStreamFilters {
    private DirectoryStreamFilters() {}

    public static DirectoryStream.Filter<Path> isDirectory() {
        return Files::isDirectory;
    }

    public static DirectoryStream.Filter<Path> isRegularFile() {
        return Files::isRegularFile;
    }

    public static DirectoryStream.Filter<Path> isReadable() {
        return Files::isReadable;
    }

    public static DirectoryStream.Filter<Path> isWritable() {
        return Files::isWritable;
    }

    public static DirectoryStream.Filter<Path> isExecutable() {
        return Files::isExecutable;
    }

    public static DirectoryStream.Filter<Path> isHidden() {
        return Files::isHidden;
    }

    public static DirectoryStream.Filter<Path> largerThan(long size) {
        return (path) -> (Files.size(path) > size);
    }

    public static DirectoryStream.Filter<Path> smallerThan(long size) {
        return (path) -> (Files.size(path) < size);
    }

    public static DirectoryStream.Filter<Path> hasSize(long size) {
        return (path) -> (Files.size(path) == size);
    }

    public static DirectoryStream.Filter<Path> hasExtension(String extension) {
        return (path) -> FilenameUtils.isExtension(path.toFile().getAbsolutePath(), extension);
    }

    public static DirectoryStream.Filter<Path> nameMatches(String regex) {
        return (path) -> Pattern.matches(regex, path.toFile().getName());
    }

    public static DirectoryStream.Filter<Path> pathMatcher(String regex) {
        return (path) -> Pattern.matches(regex, path.toFile().getPath());
    }

    public static DirectoryStream.Filter<Path> magicNumbers(int...magicNumbers) {
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
