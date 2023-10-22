package edu.homework2.server;

import edu.homework2.server.connection_manager.DefaultConnectionManager;
import edu.homework2.server.connection_manager.FaultyConnectionManager;
import edu.homework2.server.exceptions.ConnectionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Проверка программы, выполняющей запросы на сервер.")
public class ExecutorTest {
    @Test
    @DisplayName("Превышение количества попыток.")
    void executorExceededNumberOfAttempts() {
        // given
        PopularCommandExecutor popularCommandExecutor = new PopularCommandExecutor(
            new FaultyConnectionManager(),
            5
        );

        // when
        Exception exception = assertThrows(
            ConnectionException.class,
            popularCommandExecutor::updatePackages
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(ConnectionException.class);
        assertThat(exception.getCause()).isNotNull().isInstanceOf(ConnectionException.class);
        assertThat(exception.getMessage()).isEqualTo("Failed to execute command after 5 attempts!");
    }

    @Test
    @DisplayName("Успешное выполнение.")
    void executorPassed() {
        // given
        PopularCommandExecutor popularCommandExecutor = new PopularCommandExecutor(
            new DefaultConnectionManager(),
            5
        );
        Throwable exception = null;

        // when
        try {
            popularCommandExecutor.updatePackages();
        } catch (Throwable throwable) {
            exception = throwable;
        }

        // then
        assertThat(exception).isNull();
    }

    @Test
    @DisplayName("Иницилизация исполнителя некорректным числом попыток выполнения.")
    void executorIllegalArgument() {
        // given
        // --

        // when
        Exception exception = assertThrows(
            IllegalArgumentException.class,
            () -> new PopularCommandExecutor(new DefaultConnectionManager(), 0)
        );

        // then
        assertThat(exception).isNotNull().isInstanceOf(IllegalArgumentException.class);
        assertThat(exception.getMessage()).isEqualTo("Number of attempts must be positive!");
    }
}
