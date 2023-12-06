package edu.homework9.tree_analytics;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class DirectoryFilesCounter extends RecursiveTask<DirectoryState> {
    private final Path path;

    private final int nestedFilesRequired;

    public DirectoryFilesCounter(Path path, int nestedFilesRequired) {
        this.path = path;
        this.nestedFilesRequired = nestedFilesRequired;
    }

    @Override
    public DirectoryState compute() {
        int nestedFilesCounter = 0;
        List<Path> nestedDirectories = new ArrayList<>();
        List<DirectoryFilesCounter> tasks = new ArrayList<>();
        String[] nestedFilesNames = path.toFile().list();
        if (nestedFilesNames == null) {
            return new DirectoryState(1, new Path[0]);
        }
        for (String name : nestedFilesNames) {
            Path currentPath = path.resolve(name);
            if (currentPath.toFile().isDirectory()) {
                DirectoryFilesCounter nestedDirTask = new DirectoryFilesCounter(currentPath, nestedFilesRequired);
                nestedDirTask.fork();
                tasks.add(nestedDirTask);
            } else {
                nestedFilesCounter++;
            }
        }
        for (DirectoryFilesCounter task : tasks) {
            DirectoryState currentState = task.join();
            if (currentState.nestedFiles() > nestedFilesRequired) {
                nestedDirectories.addAll(List.of(currentState.nestedDirectories()));
            }
            nestedFilesCounter += currentState.nestedFiles();
        }
        if (nestedFilesCounter > nestedFilesRequired) {
            nestedDirectories.add(path);
        }
        return new DirectoryState(nestedFilesCounter, nestedDirectories.toArray(Path[]::new));
    }
}
