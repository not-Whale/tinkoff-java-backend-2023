package edu.homework9.tree_analytics;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;

public class FilesFilter extends RecursiveTask<Path[]> {
    private final Path path;

    private final Predicate<Path> filter;

    public FilesFilter(Path path, Predicate<Path> filter) {
        this.path = path;
        this.filter = filter;
    }

    @Override
    public Path[] compute() {
        String[] nestedFilesNames = path.toFile().list();
        if (nestedFilesNames == null) {
            if (filter.test(path)) {
                return new Path[] {path};
            } else {
                return new Path[0];
            }
        }

        FilesFilter[] tasks = getRecursiveTasks(nestedFilesNames);
        List<Path> filteredFiles = new ArrayList<>(List.of(getNestedFilteredFiles(nestedFilesNames)));
        for (FilesFilter task : tasks) {
            Path[] nestedFilteredFiles = task.join();
            filteredFiles.addAll(List.of(nestedFilteredFiles));
        }
        return filteredFiles.toArray(Path[]::new);
    }

    private Path[] getNestedFilteredFiles(String[] nestedFilesNames) {
        List<Path> filteredFiles = new ArrayList<>();
        for (String name : nestedFilesNames) {
            Path currentPath = path.resolve(name);
            if (currentPath.toFile().isFile() && filter.test(currentPath)) {
                filteredFiles.add(currentPath);
            }
        }
        return filteredFiles.toArray(Path[]::new);
    }

    private FilesFilter[] getRecursiveTasks(String[] nestedFilesNames) {
        List<FilesFilter> tasks = new ArrayList<>();
        for (String name : nestedFilesNames) {
            Path currentPath = path.resolve(name);
            if (currentPath.toFile().isDirectory()) {
                FilesFilter nestedFilesFilterTask = new FilesFilter(currentPath, filter);
                nestedFilesFilterTask.fork();
                tasks.add(nestedFilesFilterTask);
            }
        }
        return tasks.toArray(FilesFilter[]::new);
    }
}
