package edu.homework9.tree_analytics;

import java.nio.file.Path;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Predicate;

public class TreeAnalytics {
    private final Path rootDir;

    public TreeAnalytics(Path rootDir) {
        this.rootDir = rootDir;
    }

    public Path[] findDirsWithMoreThanNNestedFiles(int nestedFilesRequired) {
        DirectoryFilesCounter task = new DirectoryFilesCounter(rootDir, nestedFilesRequired);
        try (ForkJoinPool forkJoinPool = new ForkJoinPool()) {
            DirectoryState rootDirectoryState = forkJoinPool.invoke(task);
            return rootDirectoryState.nestedDirectories();
        }
    }

    public Path[] findFilesByPredicate(Predicate<Path> filter) {
        FilesFilter task = new FilesFilter(rootDir, filter);
        try (ForkJoinPool forkJoinPool = new ForkJoinPool()) {
            return forkJoinPool.invoke(task);
        }
    }
}
