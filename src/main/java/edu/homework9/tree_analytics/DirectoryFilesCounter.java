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
        String[] nestedFilesNames = path.toFile().list();
        if (nestedFilesNames == null) {
            return new DirectoryState(1, new Path[0]);
        }

        int nestedFilesCounter = getNestedFilesNumber(nestedFilesNames);
        DirectoryFilesCounter[] recursiveTasks = getRecursiveTasks(nestedFilesNames);

        List<Path> nestedDirectories = new ArrayList<>();
        for (DirectoryFilesCounter task : recursiveTasks) {
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

    private int getNestedFilesNumber(String[] nestedFilesNames) {
        int nestedFilesCounter = 0;
        for (String name : nestedFilesNames) {
            Path currentPath = path.resolve(name);
            if (!currentPath.toFile().isDirectory()) {
                nestedFilesCounter++;
            }
        }
        return nestedFilesCounter;
    }

    private DirectoryFilesCounter[] getRecursiveTasks(String[] nestedFilesNames) {
        List<DirectoryFilesCounter> tasks = new ArrayList<>();
        for (String name : nestedFilesNames) {
            Path currentPath = path.resolve(name);
            if (currentPath.toFile().isDirectory()) {
                DirectoryFilesCounter nestedDirTask = new DirectoryFilesCounter(currentPath, nestedFilesRequired);
                nestedDirTask.fork();
                tasks.add(nestedDirTask);
            }
        }
        return tasks.toArray(DirectoryFilesCounter[]::new);
    }
}
