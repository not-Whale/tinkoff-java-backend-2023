package edu.homework8.client_server_cyberbullying.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private static final int SERVER_PORT = 18080;

    private static final int CORE_NUMBER = 8;

    public Server() {}

    public void run() {
        try (ServerSocket server = new ServerSocket(SERVER_PORT)) {
            ExecutorService executorService = Executors.newFixedThreadPool(CORE_NUMBER);
            while (true) {
                Socket client = server.accept();
                executorService.submit(() -> acceptClient(client));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void acceptClient(Socket client) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            String word = in.readLine();
            String answer = Dictionary.getQuote(word);
            out.write(answer + "\n");
            out.flush();
            client.close();
            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
