package com.lushnikova.homework_2.config;

import com.lushnikova.Solution;
import lombok.Getter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Класс констант для подключения к БД
 */
public class Environment {
    @Getter
    private static String PASSWORD;
    @Getter
    private static String USER;
    @Getter
    private static String URL ;

    static {
        String configLiquibase = "db/changelog/liquibase.properties";

        Properties prop = new Properties();
        try (InputStream in = Environment.class.getClassLoader().getResourceAsStream(configLiquibase)){
            prop.load(in);

            URL = prop.getProperty("url");
            USER = prop.getProperty("username");
            PASSWORD = prop.getProperty("password");

        } catch (IOException e) {
            System.err.println("Не удалось прочитать файл");
        }
    }
}
