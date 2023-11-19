package edu.homework6.port_scanner;

public enum Status {
    OPENED("OPENED"),
    CLOSED("CLOSED");

    private final String verbal;

    Status(String verbal) {
        this.verbal = verbal;
    }

    public String verbal() {
        return verbal;
    }
}
