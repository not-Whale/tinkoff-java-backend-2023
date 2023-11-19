package edu.project3.readers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class LogFileReader implements Reader {
    private final String pathString;

    public LogFileReader(String path) {
        this.pathString = path;
    }

    @Override
    public boolean canRead() {
        try {
            Path.of(pathString);
            return true;
        } catch (InvalidPathException e) {
            return false;
        }
    }

    @Override
    public String[] readLogs() {
        if (!canRead()) {
            return new String[0];
        }
        List<String> logs = new ArrayList<>();
        Path[] paths = getGlobPaths(pathString);
        for (Path currentPath : paths) {
            String[] currentLogs = readLogsFromFile(currentPath);
            logs.addAll(List.of(currentLogs));
        }
        return logs.toArray(String[]::new);
    }

    private String[] readLogsFromFile(Path path) {
        ArrayList<String> logs = new ArrayList<>();
        try (FileReader fileReader = new FileReader(path.toFile());
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                logs.add(currentLine);
            }
        } catch (FileNotFoundException e) {
            return new String[0];
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return logs.toArray(String[]::new);
    }

    private Path[] getGlobPaths(String glob) {
        List<Path> paths = new ArrayList<>();
        try {
            PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + glob);
            Files.walkFileTree(Path.of(""), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) {
                    if (pathMatcher.matches(path)) {
                        paths.add(path);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            return new Path[0];
        }
        return paths.toArray(Path[]::new);
    }
}
