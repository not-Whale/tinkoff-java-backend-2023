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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Server {
    private final Logger LOGGER = LogManager.getLogger();

    private static final int SERVER_PORT = 18080;

    public Server() {}

    public void run() {
        try (ServerSocket server = new ServerSocket(SERVER_PORT)) {
            ExecutorService executorService = Executors.newFixedThreadPool(8);
            LOGGER.info("Start!");
            while (true) {
                Socket client = server.accept();
                executorService.submit(() -> acceptClient(client));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            LOGGER.info("SERVER shutdown!");
        }
    }

    private void acceptClient(Socket client) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));

            String word = in.readLine();
            LOGGER.info("Read from client: " + word);

            String answer = Dictionary.getQuote(word);
            out.write(answer + "\n");
            out.flush();
            LOGGER.info("Write to client: " + answer);

            client.close();
            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
