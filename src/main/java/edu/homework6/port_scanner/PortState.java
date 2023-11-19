package edu.homework6.port_scanner;

public record PortState(Protocol protocol, int port, Status status, String service) {}
