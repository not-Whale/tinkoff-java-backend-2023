package edu.homework2.server;

import edu.homework2.server.connection.Connection;
import edu.homework2.server.connection_manager.ConnectionManager;
import edu.homework2.server.exceptions.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class PopularCommandExecutor {
    private final static Logger LOGGER = LogManager.getLogger();

    private final ConnectionManager manager;

    private final int maxAttempts;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("Number of attempts must be positive!");
        }

        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    private void tryExecute(String command) {
        int attempts = 0;
        Throwable cause = null;
        boolean isExecuted = false;
        Connection connection = this.manager.getConnection();

        while (attempts != this.maxAttempts && !isExecuted) {
            try (connection) {
                connection.execute(command);
                isExecuted = true;
                attempts++;

            } catch (ConnectionException connectionException) {
                cause = connectionException;

            } catch (Exception e) {
                throw new RuntimeException();
            }
        }

        if (cause != null) {
            throw new ConnectionException(
                "Failed to execute command after " + maxAttempts + " attempts!",
                cause
            );
        }
    }
}
