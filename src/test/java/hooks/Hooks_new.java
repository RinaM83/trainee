package hooks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import common.Auth_api;
import utils.DatabaseUtils.DatabaseConnector;
import utils.DatabaseUtils.DatabaseManager;
import common.StepResult;
import config.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

import java.net.URL;
import java.net.MalformedURLException;
import io.cucumber.java.*;
import io.cucumber.plugin.event.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Map;
import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;

import org.openqa.selenium.Cookie;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import utils.Loggers;

import java.io.ByteArrayInputStream;
import java.util.Objects;

public class Hooks_new {
    private Auth_api authApi;
    private Map<String, String> cookies;
    private final String userLastname = ConfigReader.getPropertyValue("userLastname");
    private  final Logger logger = LogManager.getLogger(Hooks_new.class);
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
//            Configuration.browserSize = ConfigReader.getPropertyValue("browserSizeForWeb");
            Configuration.baseUrl = ConfigReader.getPropertyValue("url");

            platform = "web";

//        } else if (tags.contains("@mobile")) {
//            // Для запуска на мобилке
//            logger.info("===============================================");
//            logger.info(String.format("Start mobile scenario '%s'", scenario.getName()));
//            logger.info("===============================================");
//
//            // Читаем настройки базового URL из файла настроек
//            Configuration.baseUrl = ConfigReader.getPropertyValue("url");
//            Configuration.browserSize = ConfigReader.getPropertyValue("browserSizeForMobile");
//
//            // Настраиваем Appium
//            DesiredCapabilities capabilities = new DesiredCapabilities();
//            capabilities.setCapability("platformName", "Android");
//            capabilities.setCapability("platformVersion", "13");
//            capabilities.setCapability("deviceName", "vivo V21");
//            capabilities.setCapability("browserName", "Chrome"); // Указываем имя браузера, который будет использоваться для тестирования.
//            capabilities.setCapability("noReset", true); // Указываем, что не нужно очищать кэш браузера перед каждым запуском.
//
//            try {
//                AppiumDriver<MobileElement> driver = new AndroidDriver<>(new URL("http://localhost:4723/wd/hub"), capabilities);
//                mobileDriver.set(driver);
//
//                platform = "mobile";
//
//            } catch (MalformedURLException e) {
//                logger.error(String.format("Failed to create Appium driver with error: %s", e.getMessage()));
//            }

        } else {
            // Если тест не помечен как @web или @mobile, генерируем ошибку
            logger.error(String.format("Scenario '%s' is not tagged as '@web' or '@mobile'", scenario.getName()));
            throw new IllegalArgumentException(String.format("Scenario '%s' is not tagged as '@web' or '@mobile'", scenario.getName()));
        }

        // Авторизация пользователя
        authApi = new Auth_api();
        authApi.auth(ConfigReader.getPropertyValue("username"), ConfigReader.getPropertyValue("password"));

        // Получение CSRF токена и сессии для авторизации через cookie

        csrfToken = authApi.getCsrfToken();
        sessionValue = authApi.getSessionValue();
        csrfTokenFromHTML = authApi.getxCsrfToken();

        if (platform.equals("web")) {

            Selenide.open(Configuration.baseUrl);
            // Установка CSRF токена и сессии в cookie перед каждым запросом\
            WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("_csrf", csrfToken));
            WebDriverRunner.getWebDriver().manage().addCookie(new Cookie("PHPSESSID", sessionValue));
            WebDriverRunner.getWebDriver().navigate().to(Configuration.baseUrl);

//        } else if (platform.equals("mobile")) {
//            // Открываем нужную страницу в браузере мобильного устройства
//            String url = Configuration.baseUrl;
//            mobileDriver.get().navigate().to(url);
//
//            // Установка CSRF токена и сессии в cookie перед каждым запросом
//            Cookie csrfCookie = new Cookie("_csrf", csrfToken);
//            Cookie sessionCookie = new Cookie("PHPSESSID", sessionValue);
//            mobileDriver.get().manage().addCookie(csrfCookie);
//            mobileDriver.get().manage().addCookie(sessionCookie);
//        }
//        // Удаление всех записей из базы данных перед началом выполнения тестового сценария
//        try {
//            databaseManager.deleteStepResultsBeforeScenario(scenario, platform);
//
//        } catch (SQLException e) {
//            logger.error("Error connecting to database: " + e.getMessage());
//        }
    }

//    @BeforeStep
//    public void beforeStep(Step step) {
//        currentStep = step;
//        int stepNumber = step.getLine();
//        String stepName = step.getText();
//        Loggers.info("Starting step number " + stepNumber + ": " + stepName);
//    }
//
//    @AfterStep
//    public void afterStep(Scenario scenario) {
//        int stepNumber = currentStep.getLine();
//        String stepName = currentStep.getText();
//        Loggers.info("Завершение шага " + stepNumber + ": " + stepName);
//        stepResult = new StepResult(
//                userLastname,
//                scenario.getName(),
//                stepName,
//                scenario.getStatus().toString(),
//                platform,
//                scenario.isFailed() ? scenario.getStatus().toString() : "Passed");
//
//        //         Сохраняем результат выполнения тестового шага в базу данных - ДОБАВИТЬ НОМЕР ШАГА в методы
//        databaseManager.saveStepResult(stepResult);
    }

    @After()
    public void afterScenario(Scenario scenario) {
        try {
//            if (Objects.equals(platform, "mobile")) {
//                Loggers.info("===============================================");
//                Loggers.info(String.format("Finish mobile scenario '%s', status: %s", scenario.getName(), scenario.getStatus()));
//                Loggers.info("===============================================");
//            }
//            // Логируем скриншот в Allure при падении теста
//            if (scenario.isFailed()) {
//                Loggers.info(String.format("Failed on step %d: %s", stepNumber, stepName));
//                Selenide.screenshot("Failure mobile screenshot");
//                Allure.addAttachment("Screenshot", new ByteArrayInputStream(mobileDriver.get().getScreenshotAs(OutputType.BYTES)));
//                Allure.addAttachment("Failed step", String.valueOf(stepNumber), stepName);
//            }
//            mobileDriver.get().quit();

            if (Objects.equals(platform, "web")) {
                Loggers.info("===============================================");
                Loggers.info(String.format("Finish web scenario '%s', status: %s", scenario.getName(), scenario.getStatus()));
                logger.info("===============================================");
            }
            // Логируем скриншот в Allure при падении теста
            if (scenario.isFailed()) {
                Loggers.info(String.format("Failed on step %d: %s", stepNumber, stepName));
                Selenide.screenshot("Failure web screenshot");
                Allure.addAttachment("Screenshot", new ByteArrayInputStream(Selenide.screenshot(OutputType.BYTES)));
                Allure.addAttachment("Failed step", String.valueOf(stepNumber), stepName);
            }
            closeWebDriver();

        } finally {
//             Закрытие соединения с базой данных после каждого сценария
            DatabaseConnector.disconnect();
        }
    }
}
