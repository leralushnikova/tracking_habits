package com.lushnikova.homework_3.middleware;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.lushnikova.homework_3.consts.RegexConsts.DATE_FORMAT;
import static com.lushnikova.homework_3.consts.RegexConsts.TIME_FORMAT;

/**
 * Класс проверки формата даты
 */
public class DateMiddleware {

    /**
     * Операция проверки даты ввода
     * @param date - дата
     * @return возвращает подтверждения совпадений
     */
    public boolean checkDate(String date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
        try {
            dtf.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Операция проверки времени ввода
     * @param time - время
     * @return возвращает подтверждения совпадений
     */
    public boolean checkTime(String time) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(TIME_FORMAT);
        try {
            dtf.parse(time);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
