package edu.project3.readers;

public class LogReader {
    private LogReader() {}

    public static String[] read(String path) {
        LogURLReader urlReader = new LogURLReader(path);
        if (urlReader.canRead()) {
            return urlReader.read();
        }
        LogFileReader fileReader = new LogFileReader(path);
        if (fileReader.canRead()) {
            return fileReader.read();
        }
        return new String[0];
    }
}
