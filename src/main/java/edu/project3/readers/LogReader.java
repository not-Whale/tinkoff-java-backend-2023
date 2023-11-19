package edu.project3.readers;

public class LogReader {
    private LogReader() {}

    public static String[] parseLogs(String path) {
        LogURLReader urlReader = new LogURLReader(path);
        if (urlReader.canRead()) {
            return urlReader.readLogs();
        }
        LogFileReader fileReader = new LogFileReader(path);
        if (fileReader.canRead()) {
            return fileReader.readLogs();
        }
        return new String[0];
    }
}
