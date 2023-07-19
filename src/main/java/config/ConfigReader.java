package config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import utils.Loggers;

public class ConfigReader {

    public static Properties getProperties(String filePath) {
       Properties properties = new Properties();
        try {
           FileInputStream inputStream = new FileInputStream(filePath);
                properties.load(inputStream);
                Loggers.info("File '{}' has been loaded" + filePath);
            } catch (FileNotFoundException e) {
            Loggers.error("Error reading file '{}' : {}" + filePath + e.getMessage()); // добавляем запись в лог при ошибке чтения файла
            e.printStackTrace();
            } catch (IOException e) {
                Loggers.error("Error reading file '{}' : {}" + filePath + e.getMessage());
                throw new RuntimeException("Unable to load properties", e);
            }
        return properties;
        }
    // Метод для получения значения свойства из файла настроек
    public static String getPropertyValue(String propertyName) {
        String propertyFileName = "D:/alfa.notebook-store/src/main/resources/config.properties";
        Properties props = getProperties(propertyFileName);
        String value = props.getProperty(propertyName);
        Loggers.info("Property '{}' has value '{}'" + propertyName + value);
        return value;
    }
}