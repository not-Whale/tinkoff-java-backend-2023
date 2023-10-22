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
            throw new IllegalArgumentException();
        }

        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    private void tryExecute(String command) {
        int attempts = 0;
        Connection connection = this.manager.getConnection();
        Throwable cause = null;

        while (attempts != this.maxAttempts && cause == null) {
            try (connection) {
                connection.execute(command);
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
