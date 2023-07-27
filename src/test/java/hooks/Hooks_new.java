package hooks;

import api.AddToTheBasket_api;
import api.ClearBasket_api;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import common.Auth_api;
import io.cucumber.core.backend.TestCaseState;
import io.cucumber.datatable.DataTable;
import io.cucumber.plugin.event.Result;
import io.cucumber.plugin.event.Status;
import utils.ApiUtils;
import utils.DatabaseUtils.DatabaseConnector;
import utils.DatabaseUtils.DatabaseManager;
import common.StepResult;
import config.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import java.io.*;
import java.lang.reflect.Field;

import io.cucumber.java.*;
import io.cucumber.plugin.event.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;

import org.openqa.selenium.Cookie;
import io.qameta.allure.Allure;
import utils.Loggers;

import java.util.Objects;

public class Hooks_new {
    private Auth_api authApi;
    private ApiUtils apiUtils;
    private ClearBasket_api clearBasket_api;
    private AddToTheBasket_api addToTheBasket_api;
    private Map<String, String> cookies;
    private final String userLastname = ConfigReader.getPropertyValue("userLastname");
    private final Logger logger = LogManager.getLogger(Hooks_new.class);
    private final ThreadLocal<AppiumDriver<MobileElement>> mobileDriver = new ThreadLocal<>();
    private final DatabaseManager databaseManager = new DatabaseManager();
    private String platform;
    private int stepNumber;
    private String stepName;
    private Scenario scenario;
    private Step currentStep;
    private StepResult stepResult;
    private String scenarioName;
    public static String csrfToken;
    public static String sessionValue;
    public static String csrfTokenFromHTML;
    private DataTable dataTable;

    @Before()
    public void beforeScenario(Scenario scenario) {
        this.scenario = scenario;
        scenarioName = scenario.getName();

        Collection<String> tags = scenario.getSourceTagNames();
        if (tags.contains("@web")) {
            // Для запуска на вебе
            Loggers.info("===============================================");
            Loggers.info(String.format("Start web scenario '%s'", scenario.getName()));
            Loggers.info("===============================================");

            // Читаем настройки браузера и базовый URL из файлов настроек
            Configuration.browser = ConfigReader.getPropertyValue("browserForWeb");
            Configuration.browserSize = ConfigReader.getPropertyValue("browserSizeForWeb");
            Configuration.baseUrl = ConfigReader.getPropertyValue("baseUrl");

            platform = "web";

        } else {
            // Если тест не помечен как @web или @mobile, генерируем ошибку
            Loggers.error(String.format("Scenario '%s' is not tagged as '@web' or '@mobile'", scenario.getName()));
            throw new IllegalArgumentException(String.format("Scenario '%s' is not tagged as '@web' or '@mobile'", scenario.getName()));
        }

        // Авторизация пользователя
        authApi = new Auth_api();
        authApi.auth(ConfigReader.getPropertyValue("username"), ConfigReader.getPropertyValue("password"));

        // Получение CSRF токена и сессии для авторизации через cookie
        csrfToken = authApi.getCsrfToken();
        sessionValue = authApi.getSessionValue();
        csrfTokenFromHTML = authApi.getxCsrfToken();

        //Очистка корзины для тестового юзера перед прогоном тестов
        clearBasket_api = new ClearBasket_api();
        clearBasket_api.clearBasket(csrfTokenFromHTML, sessionValue, csrfToken);

        //Добавление товара в корзину перед прогоном сценария, если initial_num > 0
        if (scenarioName.contains("initial_num")) {
            String numValue = scenarioName.split("initial_num=")[1];
            int initialNum = Integer.parseInt(numValue);

            if (initialNum > 0) {
                addToTheBasket_api = new AddToTheBasket_api();
                addToTheBasket_api.addToTheBasket(csrfTokenFromHTML, sessionValue, csrfToken);
            }
        }

        if (platform.equals("web")) {

            Selenide.open(Configuration.baseUrl);
            // Установка CSRF токена и сессии в cookie перед каждым запросом\
            WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("_csrf", csrfToken));
            WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("PHPSESSID", sessionValue));
            WebDriverRunner.getWebDriver().navigate().to(Configuration.baseUrl);
        }
    }

    @After()
    public void afterScenario(Scenario scenario) throws NoSuchFieldException, IllegalAccessException, FileNotFoundException {
        try {

            if (Objects.equals(platform, "web")) {
                Loggers.info("===============================================");
                Loggers.info(String.format("Finish web scenario '%s', status: %s", scenario.getName(), scenario.getStatus()));
                Loggers.info("===============================================");
            }
            // Логируем скриншот в Allure при падении теста
            if (scenario.isFailed()) {
                Field delegateField = scenario.getClass().getDeclaredField("delegate");
                delegateField.setAccessible(true);
                TestCaseState testCaseState = (TestCaseState) delegateField.get(scenario);

                Field stepResultsField = testCaseState.getClass().getDeclaredField("stepResults");
                stepResultsField.setAccessible(true);
                ArrayList<Result> stepResults = (ArrayList<Result>) stepResultsField.get(testCaseState);

                for (Result result : stepResults) {
                    if (result.getStatus().is(Status.FAILED)) {
                        Loggers.error("Scenario failed: " + result.getError().getMessage());
                    }
                }

                final String name = String.format("Screenshot_%s", scenario.getName());
                String screenshotFolderPath = "D:/alfa.notebook-store/build/reports/tests";
                String screenshotFilePath = screenshotFolderPath + "/" + name + ".png";

                File screenshotFile = new File(screenshotFilePath);

                Selenide.screenshot(name);
                File defaultScreenshotFile = Screenshots.takeScreenShotAsFile();

//                boolean moveSuccessful = defaultScreenshotFile.renameTo(screenshotFile);
//
//                if (moveSuccessful) {
//                    try {
//                        FileInputStream fileInputStream = new FileInputStream(screenshotFile);
//                        Allure.addAttachment(name, fileInputStream);
//                    } catch (FileNotFoundException e) {
//                        Loggers.error("Failed to attach screenshot: " + e.getMessage());
//                    }
//                } else {
//                    Loggers.error("Failed to save screenshot");
//                }
            }
            closeWebDriver();
        } finally {
            Loggers.info("Database connection closed");
            DatabaseConnector.disconnect();
        }
    }
}
