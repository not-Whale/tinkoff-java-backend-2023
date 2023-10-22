package edu.homework2.server;

import edu.homework2.server.connection.Connection;
import edu.homework2.server.connection.FaultyConnection;
import edu.homework2.server.connection.StableConnection;
import edu.homework2.server.connection_manager.DefaultConnectionManager;
import edu.homework2.server.connection_manager.FaultyConnectionManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Проверка двух типов менеджеров соединений: по-умолчанию и дефектного.")
public class ConnectionManagerTest {
    @Nested
    @DisplayName("Менеджер соединений по-умолчанию.")
    class DefaultConnectionManagerTest {
        @ParameterizedTest
        @DisplayName("Faulty connection выкидывается каждый 5 запрос на соединение.")
        @CsvSource(value = {
            "1, 0",
            "5, 1",
            "6, 1",
            "10, 2",
            "11, 2",
            "15, 3",
            "16, 3"

        })
        void getDefaultManagerConnection(int attempts, int faulty) {
            // given
            DefaultConnectionManager defaultConnectionManager = new DefaultConnectionManager();
            int stableConnectionCounter = 0;
            int faultyConnectionCounter = 0;

            // when
            for (int i = 0; i < attempts; i++) {
                Connection connection = defaultConnectionManager.getConnection();

                if (connection instanceof StableConnection) {
                    stableConnectionCounter++;
                }

                if (connection instanceof FaultyConnection) {
                    faultyConnectionCounter++;
                }
            }

            // then
            assertThat(stableConnectionCounter + faultyConnectionCounter).isEqualTo(attempts);
            assertThat(stableConnectionCounter).isEqualTo(attempts - faulty);
            assertThat(faultyConnectionCounter).isEqualTo(faulty);
        }
    }

    @Nested
    @DisplayName("Дефектный менеджер соединений.")
    class FaultyConnectionManagerTest {
        @RepeatedTest(10)
        @DisplayName("Faulty connection выкидывается каждый раз.")
        void getFaultyManagerConnection() {
            // given
            FaultyConnectionManager faultyConnectionManager = new FaultyConnectionManager();

            // when
            Connection connection = faultyConnectionManager.getConnection();

            // then
            assertThat(connection).isInstanceOf(FaultyConnection.class);
        }
    }
}
