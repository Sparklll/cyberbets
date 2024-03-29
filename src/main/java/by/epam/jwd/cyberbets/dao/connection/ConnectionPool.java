package by.epam.jwd.cyberbets.dao.connection;

import by.epam.jwd.cyberbets.dao.connection.exception.ConnectionPoolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static by.epam.jwd.cyberbets.dao.connection.ConnectionParameter.*;

public enum ConnectionPool {
    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(ConnectionPool.class);

    private final BlockingQueue<ProxyConnection> freeConnections;
    private final BlockingQueue<ProxyConnection> givenAwayConnections;

    private final int poolSize;

    ConnectionPool() {
        ConnectionResourceManager rm = ConnectionResourceManager.INSTANCE;

        String driver = rm.getValue(DB_DRIVER);
        String url = rm.getValue(DB_URL);
        String user = rm.getValue(DB_USERNAME);
        String password = rm.getValue(DB_PASSWORD);
        poolSize = Integer.parseInt(rm.getValue(POOL_SIZE));

        freeConnections = new LinkedBlockingQueue<>(poolSize);
        givenAwayConnections = new LinkedBlockingQueue<>(poolSize);

        try {
            Class.forName(driver);
            for (int i = 0; i < poolSize; i++) {
                ProxyConnection connection = new ProxyConnection(DriverManager.getConnection(url, user, password));
                freeConnections.add(connection);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            throw new ConnectionPoolException("Connection pool creation error");
        }
    }

    public Connection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = freeConnections.take();
            givenAwayConnections.offer(connection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Failed to take connection from 'ConnectionPool'", e);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection.getClass() == ProxyConnection.class) {
            givenAwayConnections.remove(connection);
            freeConnections.offer((ProxyConnection) connection);
        } else {
            throw new ConnectionPoolException("Attempt to close connection not from connection pool");
        }
    }

    public void destroyPool() {
        for (int i = 0; i < poolSize; i++) {
            try {
                freeConnections.take().reallyClose();
            } catch (SQLException | InterruptedException e) {
                throw new ConnectionPoolException("Unable to destroy pool connection");
            }
        }
    }
}