package edu.homework6;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileCloner {
    private static final String FILE_PATH_REGEX = "^(\\w+[\\w\\s-]+)( - копия (\\d)+)?(.[A-Za-z]+)$";

    private static final int FILE_TYPE_GROUP = 3;

    private static final int FILE_COPY_GROUP = 4;

    private static final int FILE_NAME_GROUP = 1;

    private FileCloner() {}

    public static void cloneFile(Path path) throws IOException {
        int maxCopyNumber = 0;
        Path parent = path.getParent();
        File[] siblings = parent.toFile().listFiles();
        Pattern filePathPattern = Pattern.compile(FILE_PATH_REGEX);
        for (File sibling : siblings) {
            Matcher matcher = filePathPattern.matcher(sibling.getName());
            if (matcher.find()) {
                String currentCopyNumber = matcher.group(FILE_TYPE_GROUP);
                if (currentCopyNumber != null) {
                    maxCopyNumber = Math.max(maxCopyNumber, Integer.parseInt(currentCopyNumber));
                }
            }
        }
        Matcher matcher = filePathPattern.matcher(path.toFile().getName());
        if (matcher.find()) {
            Files.createFile(Path.of(
                parent.toFile().getPath(),
                matcher.group(FILE_NAME_GROUP)
                    + " - копия "
                    + (maxCopyNumber + 1)
                    + matcher.group(FILE_COPY_GROUP)
            ));
        }
    }
}
