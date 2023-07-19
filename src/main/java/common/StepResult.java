package common;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import utils.Loggers;

public class StepResult {

//    private static final Logger logger = LogManager.getLogger(StepResult.class);

    private String userLastname;
    private String scenarioName;
    private String stepName;
    private String platform;
    private String status;
    private String errorMessage;

    public StepResult(String userLastname, String scenarioName, String stepName, String status, String platform, String errorMessage) {
        this.userLastname = userLastname;
        this.scenarioName = scenarioName;
        this.stepName = stepName;
        this.status = status;
        this.platform = platform;
        this.errorMessage = errorMessage;

        // Логгируем создание объекта StepResult
       Loggers.info("Created new StepResult with userLastname={}, scenarioName={}, stepName={}, status={}, platform={} and errorMessage={}" + userLastname + scenarioName + stepName + status + platform + errorMessage);
    }

    public String getUserLastname() {
        return userLastname;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public String getStepName() {
        return stepName;
    }

    public String getStatus() {
        return status;
    }

    public String getPlatform() {
        return platform;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setStatus(String status) {
        // Логгируем изменение статуса StepResult
        Loggers.info("Updated status of StepResult with userLastName='{}', stepName='{}' and scenarioName='{}', platform='{}' to {}, errorMessage='{}'" + userLastname + stepName + scenarioName + platform + status +errorMessage);
        this.status = status;

    }
    @Override
    public String toString() {
        return "StepResult{" +
                ", userLastname='" + userLastname + '\'' +
                ", scenarioName='" + scenarioName + '\'' +
                ", stepName='" + stepName + '\'' +
                ", status='" + status + '\'' +
                ", platform='" + platform + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}