package edu.homework8.client_server_cyberbullying.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {
    private static final String SERVER_HOST = "localhost";

    private static final int SERVER_PORT = 18080;

    private final String message;

    public Client(String message) {
        this.message = message;
    }

    public String request() {
        try (Socket clientSocket = new Socket(SERVER_HOST, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {
            out.write(message + "\n");
            out.flush();
            return in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
