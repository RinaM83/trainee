package utils.DatabaseUtils;

import common.StepResult;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final Logger logger = LogManager.getLogger(DatabaseManager.class);
    private Connection connection;
    private StepResult stepResult;

    public DatabaseManager() {
        this.connection = DatabaseConnector.getConnection();
    }

    public synchronized void saveStepResult(StepResult stepResult) {
        if (stepResult.getStepName() == null || stepResult.getStatus() == null) {
            throw new IllegalArgumentException("Step name and Step status must not be null");
        }

        try (PreparedStatement statement = connection.prepareStatement(
                "INSERT INTO step_results (user_lastname, scenario_name, step_name, status, platform, error_message) VALUES (?, ?, ?, ?, ?, ?)")) {
            statement.setString(1, stepResult.getUserLastname());
            statement.setString(2, stepResult.getScenarioName());
            statement.setString(3, stepResult.getStepName());
            statement.setString(4, stepResult.getStatus());
            statement.setString(5, stepResult.getPlatform());
            statement.setString(6, stepResult.getErrorMessage());

            statement.executeUpdate();
            logger.debug("Saved step result for scenario {}, platform {}, stepName {}: {} = {}", stepResult.getScenarioName(), stepResult.getPlatform(), stepResult.getStepName(), stepResult.getStatus(), stepResult.getErrorMessage());
        } catch (SQLException e) {
            logger.error("Error saving step result", e);
        }
    }

    public synchronized List<StepResult> getStepResults(String userLastname) {
        List<StepResult> results = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM step_results WHERE user_lastname = ?")) {
            statement.setString(1, userLastname);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String scenarioName = resultSet.getString("scenario_name");
                    String stepName = resultSet.getString("step_name");
                    String status = resultSet.getString("status");
                    String platform = resultSet.getString("platform");
                    String errorMessage = resultSet.getString("error_message");

                    StepResult stepResult = new StepResult(userLastname, scenarioName, stepName, status, platform, errorMessage);
                    results.add(stepResult);
                }
            }
            logger.debug("Retrieved {} step results for user {}", results.size(), userLastname);
        } catch (SQLException e) {
            logger.error("Error getting step results for user {}: {}", userLastname, e.getMessage());
        }

        return results;
    }

    public synchronized void deleteStepResults(String userLastname) {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM step_results WHERE user_lastname = ?")) {
            statement.setString(1, userLastname);
            int deletedRows = statement.executeUpdate();
            logger.debug("Deleted {} step results for user {}", deletedRows, userLastname);
        } catch (SQLException e) {
            logger.error("Error deleting step results for user {}: {}", userLastname, e.getMessage());
        }
    }

    public synchronized void deleteStepResultsBeforeScenario(Scenario scenario, String platform) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "DELETE FROM step_results WHERE scenario_name = ? AND platform = ?")) {
            statement.setString(1, scenario.getName());
            statement.setString(2, platform);
            int deletedRows = statement.executeUpdate();
            logger.debug("Deleted {} step results for scenario {} and platform {}", deletedRows, scenario.getName(), platform);
        } catch (SQLException e) {
            logger.error("Error deleting step results for scenario {} and platform {}: {}", scenario.getName(), platform, e.getMessage());
            throw e;
        }
    }
}