package edu.project3.readers;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Чтение логов по ссылке.")
public class LogURLReaderTest {
    private static final int PORT = 18080;

    private static final String URL = "http://localhost";

    private static final String INCORRECT_URL = "!a.txt/a\\a a .t x~t";

    private static final String LOG =
        "217.168.17.5 - - [17/May/2015:08:05:12 +0000] \"GET /downloads/product_2 HTTP/1.1\" 200 3316 \"-\" \"-\"";

    @BeforeAll
    public static void startServer() {
        Thread serverThread = new Thread(() -> {
            try (ServerSocket server = new ServerSocket(PORT)) {
                while (true) {
                    Socket client = server.accept();
                    try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()))) {
                        writer.write("HTTP/1.1 200 OK\n");
                        writer.write("Content-Type: text/text; charset=utf-8\n\n");
                        writer.write(LOG);
                        writer.flush();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();
    }

    @Test
    @DisplayName("Чтение по ссылке.")
    void readURL() {
        // given
        Reader reader = new LogURLReader(URL + ":" + PORT);

        // when
        assertThat(reader.canRead()).isTrue();
        String[] logs = reader.read();

        // then
        assertThat(logs).isNotNull().isNotEmpty().containsOnly(LOG);
    }

    @Test
    @DisplayName("Чтение по ссылке. Порт для localhost не указан (ошибка подключения).")
    void readLocalhostWithoutPort() {
        // given
        Reader reader = new LogURLReader(URL);

        // when
        assertThat(reader.canRead()).isTrue();
        Exception exception = assertThrows(
            RuntimeException.class,
            reader::read
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(RuntimeException.class);
        assertThat(exception).hasMessage("URL requesting failed. Reason: java.net.ConnectException");
    }

    @Test
    @DisplayName("Неправильный формат ссылки.")
    void readIncorrectURL() {
        // given
        Reader reader = new LogURLReader(INCORRECT_URL);

        // when
        assertThat(reader.canRead()).isFalse();
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            reader::read
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).hasMessage("Incorrect path to read from!");
    }

    @Test
    @DisplayName("На вход вместо ссылки передан null.")
    void readNullURL() {
        // given
        String nullUrl = null;

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> new LogURLReader(nullUrl)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).hasMessage("Path must not be null!");
    }
}
