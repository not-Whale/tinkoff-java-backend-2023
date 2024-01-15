package edu.project3.readers;

public class LogReader implements Reader {
    private final URLReader urlReader;

    private final FileReader fileReader;

    private LogReader(String path) {
        this.urlReader = new URLReader(path);
        this.fileReader = new FileReader(path);
    }

    public static Reader from(String path) {
        if (path == null) {
            throw new IllegalArgumentException("Path must not be null!");
        }
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
        return urlReader.canRead() ? urlReader.read() : fileReader.read();
    }
}
