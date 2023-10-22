package edu.homework2.server.connection;

import edu.homework2.server.exceptions.ConnectionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FaultyConnection implements Connection {
    private final static Logger LOGGER = LogManager.getLogger();

    private static int attempt = 0;

    private static final int EXCEPTION_THROW_CYCLE_BASE = 3;

    @Override
    public boolean execute(String command) {
        attempt = (attempt + 1) % EXCEPTION_THROW_CYCLE_BASE;

        if (attempt == 1) {
            throw new ConnectionException();
        }

        LOGGER.info("Command " + command + " executed!");
        return true;
    }

    @Override
    public void close() throws Exception {
        LOGGER.info("Faulty connection closed!");
    }
}
