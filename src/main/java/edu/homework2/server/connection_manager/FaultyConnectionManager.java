package edu.homework2.server.connection_manager;

import edu.homework2.server.connection.Connection;
import edu.homework2.server.connection.FaultyConnection;

public class FaultyConnectionManager implements ConnectionManager {
    @Override
    public Connection getConnection() {
        return new FaultyConnection();
    }
}
