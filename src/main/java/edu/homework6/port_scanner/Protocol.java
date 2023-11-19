package edu.homework6.port_scanner;

public enum Protocol {
    UDP("UDP"),
    TCP("TCP");

    private final String verbal;

    Protocol(String verbal) {
        this.verbal = verbal;
    }

    public String verbal() {
        return verbal;
    }
}
