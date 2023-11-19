package edu.homework6.stream_composition;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

public class StreamComposition {
    public static final String FILE_PATH = "src/main/resources/stream_composition/quote_file.txt";

    public static final String QUOTE = "Programming is learned by writing programs. ― Brian Kernighan";

    private StreamComposition() {}

    public static void compose() {
        File file = createFile();
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            CheckedOutputStream checkedOutputStream = new CheckedOutputStream(fileOutputStream, new CRC32());
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(checkedOutputStream);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(bufferedOutputStream);
            PrintWriter printWriter = new PrintWriter(outputStreamWriter);
            printWriter.println(QUOTE);
            printWriter.close();
            outputStreamWriter.close();
            bufferedOutputStream.close();
            checkedOutputStream.close();
            fileOutputStream.close();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
    }

    public static void deleteFile() {
        try {
            Files.delete(Path.of(FILE_PATH));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static File createFile() {
        try {
            Path filePath = Path.of(FILE_PATH);
            Files.createDirectories(filePath.getParent());
            return Files.createFile(filePath).toFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
