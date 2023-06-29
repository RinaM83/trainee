package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Loggers {
    private static final Logger LOG = LogManager.getLogger(Logger.class);

    public static void info(String message) {
        LOG.info(message);
    }

    public static void debug(String message) {
        LOG.debug(message);
    }

    public static void warn(String message) {
        LOG.warn(message);
    }

    public static void error(String message) {
        LOG.error(message);
    }

    public static void trace(String message) {
        LOG.trace(message);
    }
}
