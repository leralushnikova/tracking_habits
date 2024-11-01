package com.lushnikova.middleware;

import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.lushnikova.consts.RegexConsts.DATE_FORMAT;
import static com.lushnikova.consts.RegexConsts.TIME_FORMAT;

/**
 * Класс проверки формата даты
 */
@Component
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