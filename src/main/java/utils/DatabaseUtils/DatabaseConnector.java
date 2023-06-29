package utils.DatabaseUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseConnector {
    private static final Logger logger = LogManager.getLogger(DatabaseConnector.class);
    private static Connection connection;
    private static final String PROJECT_DIR = System.getProperty("user.dir");
    private static final String CONNECTION_STRING = "jdbc:sqlite:" + PROJECT_DIR + "/database/testDB.db";

    public static void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(CONNECTION_STRING);
            logger.info("Connected to database successfully");
            System.out.println("Connected to database successfully");
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("Error connecting to database: {}", e.getMessage());
        }
    }

    public static void disconnect() {
        if (connection != null) {
            try {
                connection.close();
                logger.info("Disconnected from database successfully");
            } catch (SQLException e) {
                logger.error("Error disconnecting from database: {}", e.getMessage());
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