//package hooks;
//
//import com.codeborne.selenide.Configuration;
//import com.codeborne.selenide.Selenide;
//import common.Auth_api;
//import utils.DatabaseUtils.DatabaseConnector;
//import utils.DatabaseUtils.DatabaseManager;
//import config.ConfigReader;
//import io.appium.java_client.AppiumDriver;
//import io.appium.java_client.MobileElement;
//import io.appium.java_client.android.AndroidDriver;
//import java.net.URL;
//import java.net.MalformedURLException;
//import io.cucumber.java.After;
//import io.cucumber.java.Before;
//import io.cucumber.java.BeforeStep;
//import io.cucumber.java.Scenario;
//import io.cucumber.java.AfterStep;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.util.Collection;
//import java.util.Map;
//import static com.codeborne.selenide.Selenide.open;
//import static com.codeborne.selenide.WebDriverRunner.closeWebDriver;
//import io.qameta.allure.Allure;
//import org.openqa.selenium.OutputType;
//import utils.Loggers;
//
//import java.io.ByteArrayInputStream;
//
//public class Hooks {
//    private Auth_api authApi;
//    private Map<String, String> cookies;
//    private String userLastname = ConfigReader.getPropertyValue("userLastname");
//    private Logger logger = LogManager.getLogger(Hooks.class);
//    private ThreadLocal<AppiumDriver<MobileElement>> mobileDriver = new ThreadLocal<>();
//    private final DatabaseManager databaseManager = new DatabaseManager();
//    private String platform;
//
//    @Before
//    public void deleteStepResultsBeforeScenario(Scenario scenario) throws SQLException {
//        // Удаление всех записей из базы данных перед началом выполнения тестового сценария
//        databaseManager.deleteStepResultsBeforeScenario(scenario, platform);
//    }
//
//    @Before("@web, @mobile")
//    public void beforeScenario(Scenario scenario) {
//        Collection<String> tags = scenario.getSourceTagNames();
//        if (tags.contains("@web")) {
//            // Для запуска на вебе
//            Loggers.info("===============================================");
//            Loggers.info(String.format("Start web scenario '%s'", scenario.getName()));
//            Loggers.info("===============================================");
//
//            // Читаем настройки браузера и базовый URL из файлов настроек
//
//            Configuration.browser = ConfigReader.getPropertyValue("browserForWeb");
//            Configuration.browserSize = ConfigReader.getPropertyValue("browserSizeForWeb");
//            Configuration.baseUrl = ConfigReader.getPropertyValue("url");
//
//            // Авторизация пользователя
//            authApi = new Auth_api();
//            authApi.auth(ConfigReader.getPropertyValue("username"), ConfigReader.getPropertyValue("password"));
//
//            Selenide.open("/");
//            platform = "web";
//
//        } else if (tags.contains("@mobile")) {
//            // Для запуска на мобилке
//            Loggers.info("===============================================");
//            Loggers.info(String.format("Start mobile scenario '%s'", scenario.getName()));
//            Loggers.info("===============================================");
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
//                // Авторизация пользователя
//                authApi = new Auth_api();
//                authApi.auth(ConfigReader.getPropertyValue("username"), ConfigReader.getPropertyValue("password"));
//
//                // Открываем нужную страницу
//                open(Configuration.baseUrl);
//
//                platform = "mobile";
//
//            } catch (MalformedURLException e) {
//                Loggers.error("Ошибка при создании URL для AppiumDriver: " + e.getMessage());
//            }
//        }
//    }
//        @BeforeStep
//        public void beforeStep (Scenario scenario){
//            if (mobileDriver.get() != null) {
//                // Логируем скриншот в Allure для мобильных тестов
//                Allure.addAttachment("Screenshot", new ByteArrayInputStream(mobileDriver.get().getScreenshotAs(OutputType.BYTES)));
//            } else {
//                // Логируем скриншот в Allure для веб-тестов
//                Selenide.screenshot("Screenshot");
//            }
//
//            // Логируем название шага перед его выполнением
//            Loggers.info("Starting step: " + scenario.getName());
//        }
//
//        @AfterStep
//        public void afterStep (Scenario scenario){
//            // Получаем название шага из тега @step в feature-файле
//            String stepName = "";
//            for (String tag : scenario.getSourceTagNames()) {
//                if (tag.startsWith("@step")) {
//                    stepName = tag.replace("@step", "");
//                    break;
//                }
//            }
//
//            // Логируем результат выполнения шага после его выполнения
//            Loggers.info("Finished step: " + stepName + ", status: " + scenario.getStatus() + ", platform: " + platform);
//
//            // Сохраняем результат выполнения тестового шага в базу данных
//            databaseManager.saveStepResult(userLastname, scenario.getName(), stepName, scenario.getStatus().toString(), platform, scenario.isFailed() ? scenario.getStatus().toString() : null);
//        }
//
//        @After("@web, @mobile")
//        public void afterScenario (Scenario scenario){
//            if (mobileDriver.get() != null && mobileDriver.get().getSessionId() != null) {
//                Loggers.info("===============================================");
//                Loggers.info(String.format("Finish mobile scenario '%s', status: %s", scenario.getName(), scenario.getStatus()));
//                Loggers.info("===============================================");
//
//                // Логируем скриншот в Allure при падении теста
//                if (scenario.isFailed()) {
//                    String failedStep = "";
//                    for (String tag : scenario.getSourceTagNames()) {
//                        if (tag.startsWith("@step")) {
//                            failedStep = tag.replace("@step", "");
//                            break;
//                        }
//                    }
//                    Allure.addAttachment("Screenshot", new ByteArrayInputStream(mobileDriver.get().getScreenshotAs(OutputType.BYTES)));
//                    Allure.addAttachment("Failed step", failedStep);
//                }
//
//                mobileDriver.get().quit();
//
//            } else {
//                Loggers.info("===============================================");
//                Loggers.info(String.format("Finish web scenario '%s', status: %s", scenario.getName(), scenario.getStatus()));
//                logger.info("===============================================");
//
//                // Логируем скриншот в Allure при падении теста
//                if (scenario.isFailed()) {
//                    String failedStep = "";
//                    for (String tag : scenario.getSourceTagNames()) {
//                        if (tag.startsWith("@step")) {
//                            failedStep = tag.replace("@step", "");
//                            break;
//                        }
//                    }
//                    Selenide.screenshot("Failure screenshot");
//                    Allure.addAttachment("Screenshot", new ByteArrayInputStream(Selenide.screenshot(OutputType.BYTES)));
//                    Allure.addAttachment("Failed step", failedStep);
//                }
//
//                closeWebDriver();
//            }
//            // Закрытие соединения с базой данных после выполнения каждого тестового сценария
//            // Закрытие соединения с базой данных после выполнения каждого тестового сценария
//            try (Connection connection = DatabaseConnector.getConnection()) {
//                // do nothing
//            } catch (SQLException e) {
//                logger.error("Error closing database connection", e);
//            }
//        }
//    }
