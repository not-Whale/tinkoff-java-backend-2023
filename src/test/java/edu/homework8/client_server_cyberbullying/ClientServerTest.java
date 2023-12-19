package edu.homework8.client_server_cyberbullying;

import edu.homework8.client_server_cyberbullying.client.Client;
import edu.homework8.client_server_cyberbullying.server.Dictionary;
import edu.homework8.client_server_cyberbullying.server.Server;
import java.security.SecureRandom;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сетевая служба с едкими ответами.")
public class ClientServerTest {
    private static final Logger LOGGER = LogManager.getLogger();

    private static Server server;

    @Test
    @DisplayName("Отправка запросов на сервер в несколько потоков.")
    void sendWordsToServer() throws InterruptedException {
        // create server
        Thread thread = new Thread(() -> {
            server = new Server();
            server.run();
        });
        thread.start();
        Thread.sleep(1000);

        // create clients requests queue
        List<String> words = List.of(
            "личности",
            "личность",
            "оскорбления",
            "глупый",
            "интеллект"
        );
        SecureRandom random = new SecureRandom();
        Callable<Void> sendWord = () -> {
            String word = words.get(random.nextInt(0, words.size()));
            Client client = new Client(word);
            String answer = client.request();
            assertThat(Dictionary.getQuotes(word)).contains(answer);
            return null;
        };
        var tasks = Stream.generate(() -> sendWord).limit(100).toList();
        try (ExecutorService executorService = Executors.newCachedThreadPool()) {
            List<Future<Void>> futures = executorService.invokeAll(tasks);
            for (var future : futures) {
                future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.info("Something went wrong!");
        }
    }
}
