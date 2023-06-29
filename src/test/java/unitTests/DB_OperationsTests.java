package unitTests;

import utils.DatabaseUtils.DatabaseConnector;
import utils.DatabaseUtils.DatabaseManager;
import config.ConfigReader;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import common.StepResult;
import org.junit.jupiter.api.*;
import utils.Loggers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DB_OperationsTests {
//    private static final Logger logger = LogManager.getLogger(DB_OperationsTests.class);
    private Connection connection;
    private static DatabaseManager dbManager;
    private static StepResult stepResult;

    private static String userLastname = ConfigReader.getPropertyValue("userLastname");

    @BeforeEach
    public void setUp() throws SQLException {
        dbManager = new DatabaseManager();
        connection = DatabaseConnector.getConnection();
    }

//    @AfterEach
//    public void clean(){
//        dbManager.deleteStepResults(userLastname);
//    }

    @AfterAll
    public static void tearDown() throws SQLException {
        DatabaseConnector.disconnect();
    }

    @Test
    public void testSaveStepResultWithValidArguments() throws SQLException {
        Loggers.info("---------------------------------------------");
        Loggers.info("Starting testSaveStepResultWithValidArguments");
        Loggers.info("---------------------------------------------");

        stepResult = new StepResult(
                userLastname,
                "web",
                "открываем приложение",
                "passed",
                "web",
                "Passed");

        dbManager.saveStepResult(stepResult);

        List<StepResult> stepResults = dbManager.getStepResults(userLastname);
        assertEquals(1, stepResults.size());
        StepResult result = stepResults.get(0);
        assertEquals(userLastname, result.getUserLastname());
        assertEquals(stepResult.getScenarioName(), result.getScenarioName());
        assertEquals(stepResult.getStepName(), result.getStepName());
        assertEquals(stepResult.getStatus(), result.getStatus());
        assertEquals(stepResult.getPlatform(), result.getPlatform());
        assertEquals(stepResult.getErrorMessage(), result.getErrorMessage());

        Loggers.info("---------------------------------------------");
        Loggers.info("Ending testSaveStepResultWithValidArguments");
        Loggers.info("---------------------------------------------");
    }

    @Test
    public void testGetStepResultsWithValidUser() throws SQLException {

        Loggers.info("---------------------------------------------");
        Loggers.info("Starting testGetStepResultsWithValidUserId");
        Loggers.info("---------------------------------------------");

        List<StepResult> stepResults = dbManager.getStepResults(userLastname);
        assertEquals(1, stepResults.size());

        Loggers.info("---------------------------------------------");
        Loggers.info("Ending testGetStepResultsWithValidUserId");
        Loggers.info("---------------------------------------------");
    }

    @Test
    public void testDeleteAllStepResults() throws SQLException {

        Loggers.info("---------------------------------------------");
        Loggers.info("Starting testDeleteAllStepResults");
        Loggers.info("---------------------------------------------");

        // Сначала очищаем таблицу step_results
        dbManager.deleteStepResults(userLastname);

        // Проверяем, что после очистки база данных не содержит никаких записей о результатах шагов
        List<StepResult> stepResults = dbManager.getStepResults(userLastname);
        assertEquals(0, stepResults.size());

        Loggers.info("---------------------------------------------");
        Loggers.info("Ending testDeleteAllStepResults");
        Loggers.info("---------------------------------------------");
    }
}