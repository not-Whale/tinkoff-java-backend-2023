package edu.homework2.server.exceptions;

public class ConnectionException extends RuntimeException {
    public ConnectionException() { }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
