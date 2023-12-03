package edu.homework8.client_server_cyberbullying.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Client {
    private final Logger LOGGER = LogManager.getLogger();

    private static final String SERVER_HOST = "localhost";

    private static final int SERVER_PORT = 18080;

    private final String message;

    private String answer;

    public Client(String message) {
        this.message = message;
    }

    public void start() {
        try (Socket clientSocket = new Socket(SERVER_HOST, SERVER_PORT)) {
            LOGGER.info("Start!");

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

            out.write(message + "\n");
            out.flush();
            LOGGER.info("Write to server: " + message);

            answer = in.readLine();
            LOGGER.info("Read from server: " + answer);

            clientSocket.close();
            in.close();
            out.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            LOGGER.info("Shutdown!");
        }
    }

    public String getAnswer() {
        return answer;
    }
}
