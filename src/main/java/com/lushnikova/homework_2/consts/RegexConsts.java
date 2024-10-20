package com.lushnikova.homework_2.consts;

/**
 * Класс констант для регулярных выражений
 */
public class RegexConsts {
    public static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,}$";
}
