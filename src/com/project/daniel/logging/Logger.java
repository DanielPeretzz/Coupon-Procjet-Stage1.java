package com.project.daniel.logging;


import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class Logger {
    private static final Map<Class<?>, Logger> loggers = new HashMap<>();

    public static Logger getLogger(Class<?> sourceClass) {
        Logger logger = loggers.get(sourceClass);
        if (logger == null) {
            logger = new Logger(sourceClass);
            loggers.put(sourceClass, logger);
        }
        return logger;
    }

    private Logger(final Class<?> sourceClass) {
        this.sourceClass = sourceClass;
    }

    private final Class<?> sourceClass;

    public void info(String msg) {
        log(msg, LogLevel.INFO);
    }

    public void debug(String msg) {
        log(msg, LogLevel.DEBUG);
    }

    public void warn(String msg) {
        log(msg, LogLevel.WARNING);
    }

    public void error(String msg) {
        log(msg, LogLevel.ERROR);
    }

    private void log(final String msg, final LogLevel logLevel)  {
        if (logLevel.getValue() > LoggerConfig.logLevel.getValue()) {
            return;
        }

        final String time = LocalTime.now().truncatedTo(ChronoUnit.MILLIS).format(DateTimeFormatter.ISO_TIME);
        final String log = time + " | " + this.sourceClass.getSimpleName() + " | " + logLevel + " | '" + msg + "'";
        try {
            if (LoggerConfig.logMode == LogMode.FILE) {
                LoggerConfig.writer.write(log + "\n");
                LoggerConfig.writer.flush();
            } else {
                final PrintStream printStream = logLevel == LogLevel.ERROR ? System.err : System.out;
                printStream.println(log);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}