package edu.homework6.port_scanner;

import java.io.IOException;
import java.net.BindException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PortScanner {
    private static final int START_PORT = 1;

    private static final int END_PORT = 49151;

    private static final Map<Integer, String> SERVICES_PER_PORT = new HashMap<>() {{
        put(1, "TCP Port Service Multiplexer");
        put(20, "File Transfer [Default Data]");
        put(21, "File Transfer Protocol [Control]");
        put(22, "The Secure Shell (SSH) Protocol");
        put(23, "Telnet");
        put(25, "Simple Mail Transfer");
        put(29, "MSG ICP");
        put(31, "MSG Authentication");
        put(53, "Domain Name Server");
        put(80, "World Wide Web HTTP");
        put(107, "Remote Telnet Service");
        put(110, "Post Office Protocol - Version 3");
        put(118, "SQL Services");
        put(137, "NETBIOS Name Service");
        put(138, "NETBIOS Datagram Service");
        put(139, "NETBIOS Session Service");
        put(143, "Internet Message Access Protocol");
        put(443, "http protocol over TLS/SSL");
        put(445, "Microsoft-DS");
        put(3306, "MySQL");
        put(5432, "PostgreSQL Database");
        put(8432, "PostgreSQL Backup");
        put(27017, "Mongo database system");
    }};

    private PortScanner() {}

    public static PortState[] scan() {
        return scan(START_PORT, END_PORT);
    }

    public static PortState[] scan(int startPort, int endPort) {
        ArrayList<PortState> portStates = new ArrayList<>();
        int currentPort = startPort;
        while (currentPort < endPort) {
            portStates.add(getTCPPortState(currentPort));
            portStates.add(getUDPPortState(currentPort));
            currentPort++;
        }
        return portStates.toArray(PortState[]::new);
    }

    private static PortState getTCPPortState(int port) {
        String service = SERVICES_PER_PORT.get(port);
        service = service == null ? "N/A" : service;
        try (ServerSocket tcp = new ServerSocket(port)) {
            return new PortState(Protocol.TCP, port, Status.OPENED, "");
        } catch (BindException e) {
            return new PortState(Protocol.TCP, port, Status.CLOSED, service);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static PortState getUDPPortState(int port) {
        String service = SERVICES_PER_PORT.get(port);
        service = service == null ? "N/A" : service;
        try (DatagramSocket udp = new DatagramSocket(port)) {
            return new PortState(Protocol.UDP, port, Status.OPENED, "");
        } catch (BindException e) {
            return new PortState(Protocol.UDP, port, Status.CLOSED, service);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
