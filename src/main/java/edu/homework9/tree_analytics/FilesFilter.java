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
    protected Path[] compute() {
        List<Path> filteredFiles = new ArrayList<>();
        List<FilesFilter> tasks = new ArrayList<>();
        String[] nestedFilesNames = path.toFile().list();

        if (nestedFilesNames == null) {
            if (filter.test(path)) {
                filteredFiles.add(path);
            }
        } else {
            for (String name : nestedFilesNames) {
                Path currentPath = path.resolve(name);
                if (currentPath.toFile().isDirectory()) {
                    FilesFilter nestedFilesFilterTask = new FilesFilter(currentPath, filter);
                    nestedFilesFilterTask.fork();
                    tasks.add(nestedFilesFilterTask);
                } else {
                    if (filter.test(currentPath)) {
                        filteredFiles.add(currentPath);
                    }
                }
            }
        }
        for (FilesFilter task : tasks) {
            Path[] nestedFilteredFiles = task.join();
            filteredFiles.addAll(List.of(nestedFilteredFiles));
        }
        return filteredFiles.toArray(Path[]::new);
    }
}
