package com.project.daniel.couponproject.database.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Stack;

import static com.project.daniel.couponproject.constants.Constants.*;

//-----------------------------------------------connection-pool-class--------------------------------------------------
public class ConnectionPool {

    private static ConnectionPool instance = null;
    private final Stack<Connection> connections = new Stack<>();

    private ConnectionPool() throws SQLException {
        System.out.println("Created new connection pool");
        openAllConnections();
    }

    //checking if instance not use before return Instance
    public static ConnectionPool getInstance() throws SQLException {
        if (instance == null) {
            synchronized (ConnectionPool.class) {
                if (instance == null) {
                    instance = new ConnectionPool();
                }
            }
        }
        return instance;
    }

    // open connection with connection details to DB
    private void openAllConnections() throws SQLException {
        for (int counter = 0; counter < NUMBER_OF_CONNECTIONS; counter++) {
            final Connection connection = DriverManager.getConnection(SQL_URL, SQL_USER, SQL_PASS);
            connections.push(connection);
        }
    }

    // close all connection
    public void closeAllConnections() throws InterruptedException {
        synchronized (connections) {
            while (connections.size() < NUMBER_OF_CONNECTIONS) {
                connections.wait();
            }
            connections.removeAllElements();
        }
    }

    public Connection getConnection() throws InterruptedException {
        synchronized (connections) {
            while (connections.isEmpty()) {
                connections.wait();
            }
            return connections.pop();
        }
    }

    public void returnConnection(final Connection connection) {
        synchronized (connections) {
            if (connection == null) {
                return;
            }
            connections.push(connection);
            connections.notify();
        }
    }
}