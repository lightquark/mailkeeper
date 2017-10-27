package org.lightquark.mailkeeper.logging;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;

/**
 * Created by Light Quark.
 */
public class LoggingUtils
{
    public static void setLogLevel(String logLevel)
    {
        LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
        Configuration config = ctx.getConfiguration();
        LoggerConfig loggerConfig = config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME);
        loggerConfig.setLevel(Level.toLevel(logLevel, Level.ERROR));
        ctx.updateLoggers();  // This causes all Loggers to refetch information from their LoggerConfig.
    }
}
