package edu.project3;

import org.apache.commons.io.FilenameUtils;
import org.junit.jupiter.api.Test;

public class TTest {
    @Test
    void test() {
        System.out.println(FilenameUtils.getExtension("path/dir/somefile.txt"));
    }
}
