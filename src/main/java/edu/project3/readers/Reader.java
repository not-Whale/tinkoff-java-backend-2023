package edu.project3.readers;

public interface Reader {
    boolean canRead();

    String[] readLogs();
}
