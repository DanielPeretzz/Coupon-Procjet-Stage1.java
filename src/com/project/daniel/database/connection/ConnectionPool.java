package com.project.daniel.database.connection;

import com.project.daniel.database.DbConfig;
import com.project.daniel.logging.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Stack;


public class ConnectionPool {
    public static final String SQL_URL = "jdbc:mysql://localhost:3306/coupon?useSSL=false&serverTimezone=UTC";
    public static final String SQL_USER = "root";
    public static String SQL_PASS = System.getenv("SQL_PASSWORD");
    private static final int NUMBER_OF_CONNECTIONS = 5;
    private static ConnectionPool instance = null;
    private final Stack<Connection> connections = new Stack<>();

   /* private static final Logger logger = Logger.getLogger(ConnectionPool.class);*/

    private ConnectionPool() throws SQLException {
        System.out.println("Created new connection pool");
        openAllConnections();
    }

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

    private void openAllConnections() throws SQLException {
        for (int counter = 0; counter < NUMBER_OF_CONNECTIONS; counter++) {
            final Connection connection = DriverManager.getConnection(SQL_URL, SQL_USER, SQL_PASS);
            connections.push(connection);
        }
    }

    public void closeAllConnections() throws InterruptedException {
        synchronized (connections) {
            while (connections.size() < NUMBER_OF_CONNECTIONS){
                connections.wait();
            }
            connections.removeAllElements();
        }
    }

    public Connection getConnection() throws InterruptedException {
        synchronized (connections) {
            final long start = Calendar.getInstance().getTimeInMillis();
            if (connections.isEmpty()) {
               /* logger.debug(Thread.currentThread().getName() + " is waiting for an available connection");*/
            }
            while (connections.isEmpty()) {
                connections.wait();
            }
            final long end = Calendar.getInstance().getTimeInMillis();
            final long duration = end - start;
            /*logger.debug(Thread.currentThread().getName() + " found available connection after " + duration + " ms");*/
            return connections.pop();
        }
    }

    public void returnConnection(final Connection connection) {
        synchronized (connections) {
            if (connection == null) {
                /*logger.warn("Attempt to return null connection terminated");*/
                return;
            }
            connections.push(connection);
            /*logger.debug(Thread.currentThread().getName() + " is returning it's connection, now there are " + connections.size());*/
            connections.notifyAll();
        }
    }
}