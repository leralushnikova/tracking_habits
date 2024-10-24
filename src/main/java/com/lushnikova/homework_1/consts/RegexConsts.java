package com.lushnikova.homework_1.consts;

/**
 * Класс констант для регулярных выражений
 */
public class RegexConsts {
    public static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,}$";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
}
