package com.project.daniel.logging;

import com.project.daniel.config.Configurer;

import java.io.*;
import java.time.LocalDate;


public class LoggerConfig extends Configurer {
    public static LogMode logMode;
    public static LogLevel logLevel;
    static BufferedWriter writer = null;
    static final String LOG_FILE = "logs/log_" + LocalDate.now() + ".txt";

    private static final String LOG_MODE_PROPERTY = "log_mode";
    private static final String LOG_LEVEL_PROPERTY = "log_level";
    private static final String APPEND_LOG_PROPERTY = "append_log";

    static {
        boolean appendLog = true;
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(LOG_MODE_PROPERTY)) {
                    logMode = LogMode.valueOf(extractPropertyValue(line));
                    System.out.println(LOG_MODE_PROPERTY + " configured to " + logMode);
                }

                if (line.contains(LOG_LEVEL_PROPERTY)) {
                    logLevel = LogLevel.valueOf(extractPropertyValue(line));
                    System.out.println(LOG_LEVEL_PROPERTY + " configured to " + logLevel);
                }

                if (line.contains(APPEND_LOG_PROPERTY)) {
                    appendLog = Boolean.parseBoolean(extractPropertyValue(line));
                    System.out.println(APPEND_LOG_PROPERTY + " configured to " + appendLog);
                }
            }
            writer = new BufferedWriter(new FileWriter(LOG_FILE, appendLog));
        } catch (IOException e) {
            System.err.println("Failed to configure logger");
            logMode = LogMode.CONSOLE;
        }
    }
}
