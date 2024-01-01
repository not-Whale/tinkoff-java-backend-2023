package edu.project3.writers;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class LogFileWriter implements Writer {
    private final String pathString;

    public LogFileWriter(String pathString) {
        if (pathString == null) {
            throw new IllegalArgumentException("Path must not be null!");
        }
        this.pathString = pathString;
    }

    @Override
    public void write(String report) {
        Path savePath = Path.of(pathString);
        resolvePath(savePath);
        createFileIfDoesNotExists(savePath);
        try (FileWriter writer = new FileWriter(savePath.toFile(), true)) {
            writer.write(report);
        } catch (IOException e) {
            throw new RuntimeException("Report writing failed. Reason: " + e.getMessage());
        }
    }

    private void resolvePath(Path savePath) {
        try {
            Files.createDirectories(savePath.getParent());
        } catch (IOException e) {
            throw new RuntimeException("Path resolving failed. Reason: " + e.getMessage());
        }
    }

    private void createFileIfDoesNotExists(Path savePath) {
        if (!savePath.toFile().exists()) {
            try {
                Files.createFile(savePath);
            } catch (IOException e) {
                throw new RuntimeException("File creating failed. Reason: " + e.getMessage());
            }
        }
    }
}
