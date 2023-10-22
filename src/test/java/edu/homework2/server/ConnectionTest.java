package edu.homework2.server;

import edu.homework2.server.connection.FaultyConnection;
import edu.homework2.server.connection.StableConnection;
import edu.homework2.server.exceptions.ConnectionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Проверка двух типов соединений: стабильного и дефектного.")
public class ConnectionTest {
    @Nested
    @DisplayName("Стабильное соединение.")
    class StableConnectionTest {
        @RepeatedTest(10)
        @DisplayName("Соединение работает всегда.")
        void stableConnectionExecute() {
            // given
            StableConnection connection = new StableConnection();
            Throwable exception = null;

            // when
            try (connection) {
                connection.execute("apt update && apt upgrade -y");
            } catch (ConnectionException connectionException) {
                exception = connectionException;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            // then
            assertThat(exception).isNull();
        }
    }

    @Nested
    @DisplayName("Дефектное соединение.")
    class FaultyConnectionTest {
        @ParameterizedTest
        @DisplayName("Ошибка выкидывается каждую третью попытку на выполнение.")
        @CsvSource(value = {
            "3, 1",
            "4, 2",
            "5, 2",
            "6, 2",
            "7, 3"
        })
        void faultyConnectionExecute(int attempts, int exceptions) {
            // given
            FaultyConnection connection = new FaultyConnection();
            Throwable exception = null;
            int exceptionCounter = 0;

            // when
            for (int i = 0; i < attempts; i++) {
                try (connection) {
                    connection.execute("apt update && apt upgrade -y");
                } catch (ConnectionException connectionException) {
                    exception = connectionException;
                    exceptionCounter++;
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

            // then
            assertThat(exception).isNotNull().isInstanceOf(ConnectionException.class);
            assertThat(exceptionCounter).isEqualTo(exceptions);
        }
    }
}
