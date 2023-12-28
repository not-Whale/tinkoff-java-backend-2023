package edu.project3.readers;

public class LogReader implements Reader {
    private final LogURLReader urlReader;

    private final LogFileReader fileReader;

    private LogReader(String path) {
        this.urlReader = new LogURLReader(path);
        this.fileReader = new LogFileReader(path);
    }

    public static Reader from(String path) {
        return new LogReader(path);
    }

    @Override
    public boolean canRead() {
        return urlReader.canRead() || fileReader.canRead();
    }

    @Override
    public String[] read() {
        if (!canRead()) {
            throw new IllegalArgumentException("Incorrect path to read from!");
        }
        if (urlReader.canRead()) {
            return urlReader.read();
        } else {
            return fileReader.read();
        }
    }
}
