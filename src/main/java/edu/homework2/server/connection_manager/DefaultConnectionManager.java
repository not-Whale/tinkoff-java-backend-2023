package edu.homework2.server.connection_manager;

import edu.homework2.server.connection.Connection;
import edu.homework2.server.connection.FaultyConnection;
import edu.homework2.server.connection.StableConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DefaultConnectionManager implements ConnectionManager {
    private final static Logger LOGGER = LogManager.getLogger();

    private int attempt = 0;

    private static final int FAULTY_CONNECTION_RETURN_CYCLE_BASE = 5;

    @Override
    public Connection getConnection() {
        attempt = (attempt + 1) % FAULTY_CONNECTION_RETURN_CYCLE_BASE;

        if (attempt == 1) {
            return new FaultyConnection();
        }

        return new StableConnection();
    }
}
