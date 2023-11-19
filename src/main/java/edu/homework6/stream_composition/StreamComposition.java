package edu.homework6.stream_composition;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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
        FileOutputStream fileOutputStream = getFileOutputStream(file);
        CheckedOutputStream checkedOutputStream = getCheckedOutputStream(fileOutputStream);
        BufferedOutputStream bufferedOutputStream = getBufferedOutputStream(checkedOutputStream);
        OutputStreamWriter outputStreamWriter = getOutputStreamWriter(bufferedOutputStream);
        PrintWriter printWriter = getPrintWriter(outputStreamWriter);
        printWriter.println(QUOTE);
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

    private static FileOutputStream getFileOutputStream(File file) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            return fos;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static CheckedOutputStream getCheckedOutputStream(FileOutputStream fos) {
        try (CheckedOutputStream chos = new CheckedOutputStream(fos, new CRC32())) {
            return chos;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static BufferedOutputStream getBufferedOutputStream(CheckedOutputStream chos) {
        try (BufferedOutputStream bos = new BufferedOutputStream(chos)) {
            return bos;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static OutputStreamWriter getOutputStreamWriter(BufferedOutputStream bos) {
        try (OutputStreamWriter osw = new OutputStreamWriter(bos, StandardCharsets.UTF_8)) {
            return osw;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static PrintWriter getPrintWriter(OutputStreamWriter osw) {
        try (PrintWriter printWriter = new PrintWriter(osw)) {
            return printWriter;
        }
    }
}
