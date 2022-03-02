package com.project.daniel.database;

import com.project.daniel.config.Configurer;
import com.project.daniel.logging.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class DbConfig extends Configurer {
    static DbInitMode dbInitMode;
    public static String sqlUrl;
    public static String sqlUser;
    public static final String sqlPassword = System.getenv("SQL_PASSWORD");

    private static final String DB_INIT_MODE_PROPERTY = "db_init_mode";
    private static final String SQL_URL_PROPERTY = "sql_url";
    private static final String SQL_USER_PROPERTY = "sql_user";
    private static final Logger logger = Logger.getLogger(DbConfig.class);

    static {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(DB_INIT_MODE_PROPERTY)) {
                    dbInitMode = DbInitMode.valueOf(extractPropertyValue(line));
                    logger.info(DB_INIT_MODE_PROPERTY + " configured to " + dbInitMode);
                }

                if (line.contains(SQL_URL_PROPERTY)) {
                    sqlUrl=extractPropertyValue(line);
                    logger.info(SQL_URL_PROPERTY + " configured to " + sqlUrl);
                }

                if (line.contains(SQL_USER_PROPERTY)) {
                    sqlUser=extractPropertyValue(line);
                    logger.info(SQL_USER_PROPERTY + " configured to " + sqlUser);
                }
            }
            reader.close();
        } catch (IOException e) {
            logger.error("Failed to read " + DB_INIT_MODE_PROPERTY + " defaulting to REGULAR");
            dbInitMode = DbInitMode.REGULAR;
        }
    }
}
