package com.lushnikova.homework_3.consts;

import lombok.experimental.UtilityClass;

/**
 * Класс констант для регулярных выражений
 */
@UtilityClass
public class RegexConsts {
    public static final String REGEX_PASSWORD = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,}$";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm";
}
