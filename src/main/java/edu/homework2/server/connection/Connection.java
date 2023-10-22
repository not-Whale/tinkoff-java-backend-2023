package edu.homework2.server.connection;

public interface Connection extends AutoCloseable {
    void execute(String command);
}
