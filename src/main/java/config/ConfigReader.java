package config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    // Метод для чтения файла настроек
    public static Properties getProperties(String filePath) {
       Properties properties = new Properties();
        try {
           FileInputStream inputStream = new FileInputStream(filePath);
                properties.load(inputStream);
                logger.info("File '{}' has been loaded", filePath);
            } catch (FileNotFoundException e) {
            logger.error("Error reading file '{}' : {}", filePath, e.getMessage()); // добавляем запись в лог при ошибке чтения файла
            e.printStackTrace();
            } catch (IOException e) {
                logger.error("Error reading file '{}' : {}", filePath, e.getMessage());
                throw new RuntimeException("Unable to load properties", e);
            }
        return properties;
        }
    // Метод для получения значения свойства из файла настроек
    public static String getPropertyValue(String propertyName) {
        String propertyFileName = "D:/alfa.notebook-store/src/main/resources/config.properties";
        Properties props = getProperties(propertyFileName);
        String value = props.getProperty(propertyName);
        logger.info("Property '{}' has value '{}'", propertyName, value);
        return value;
    }
}