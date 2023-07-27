package utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import utils.Loggers;

public class DatabaseConnector {
    private static Connection connection;
    private static final String PROJECT_DIR = System.getProperty("user.dir");
    private static final String CONNECTION_STRING = "jdbc:sqlite:" + PROJECT_DIR + "/database/testDB.db";

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(CONNECTION_STRING);
            Loggers.info("Connected to database successfully");
            System.out.println("Connected to database successfully");
        } catch (ClassNotFoundException | SQLException e) {
            Loggers.error("Error connecting to database: {}" + e.getMessage());
        }
    }

    public static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                Loggers.info("Disconnected from database successfully");
            } catch (SQLException e) {
                Loggers.error("Error disconnecting from database: {}" + e.getMessage());
            }
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            connect();
        }
        return connection;
    }
}