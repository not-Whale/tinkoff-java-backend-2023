package edu.project3.logs;

public enum RequestType {
    GET("GET"),
    HEAD("HEAD"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    CONNECT("CONNECT"),
    TRACE("TRACE"),
    PATCH("PATCH");

    private final String verbal;

    RequestType(String verbal) {
        this.verbal = verbal;
    }

    String verbal() {
        return verbal;
    }
}
