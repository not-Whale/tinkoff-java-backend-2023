package edu.homework9.tree_analytics;

import java.nio.file.Path;

public record DirectoryState(int nestedFiles, Path[] nestedDirectories) {}
