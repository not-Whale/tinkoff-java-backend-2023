package edu.project3.readers;

import java.io.BufferedReader;
import java.io.File;
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
    private static final String GLOB_PATH_PATTERN = ".*((\\*\\*)|([*?\\[\\]])).*";

    private final String pathString;

    public LogFileReader(String path) {
        if (path == null) {
            throw new IllegalArgumentException("Path must be not null!");
        }
        this.pathString = path;
    }

    @Override
    public boolean canRead() {
        try {
            File target = Path.of(pathString).toFile();
            return isGlob() || (target.exists() && target.isFile() && target.canRead());
        } catch (InvalidPathException e) {
            return false;
        }
    }

    @Override
    public String[] read() {
        List<String> logs = new ArrayList<>();
        Path[] paths = getGlobPaths();
        for (Path currentPath : paths) {
            String[] currentLogs = readLogsFromFile(currentPath);
            logs.addAll(List.of(currentLogs));
        }
        return logs.toArray(String[]::new);
    }

    private boolean isGlob() {
        return pathString.matches(GLOB_PATH_PATTERN);
    }

    private String[] readLogsFromFile(Path path) {
        ArrayList<String> logs = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                logs.add(currentLine);
            }
        } catch (IOException e) {
            throw new RuntimeException("File reading failed. Reason: " + e.getMessage());
        }
        return logs.toArray(String[]::new);
    }

    private Path[] getGlobPaths() {
        List<Path> paths = new ArrayList<>();
        try {
            PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + pathString);
            Files.walkFileTree(Path.of(""), new SimpleFileVisitor<>() {
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
