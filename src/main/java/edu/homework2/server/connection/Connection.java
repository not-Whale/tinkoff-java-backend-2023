package edu.homework2.server.connection;

public interface Connection extends AutoCloseable {
    boolean execute(String command);
}
