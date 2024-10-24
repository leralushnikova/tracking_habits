package com.lushnikova.homework_2.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Класс констант для подключения к БД
 */
public class Environment {
    private static String PASSWORD;
    private static String USER;
    private static String URL = "jdbc:postgresql://localhost:5432/postgres";

    static {
        try {
            String configFilePath = ".env";
            FileInputStream propsInput = new FileInputStream(configFilePath);
            Properties prop = new Properties();
            prop.load(propsInput);

            USER = prop.getProperty("DATABASE_USERNAME");
            PASSWORD = prop.getProperty("DATABASE_PASSWORD");
            URL = prop.getProperty("JDBC_DRIVER_VERSION");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUSER() {
        return USER;
    }
    public static String getPassword() {
        return PASSWORD;
    }
    public static String getURL() {
        return URL;
    }

}
