package com.lushnikova.homework_2.middleware;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import static com.lushnikova.homework_2.consts.RegexConsts.DATE_FORMAT;

/**
 * Класс проверки формата даты
 */
public class DateMiddleware {

    /**
     * Операция проверки даты ввода
     * @param date - дата
     * @return возвращает подтверждения совпадений
     */
    //проверка даты
    public boolean checkDate(LocalDate date) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        try {
            format.parse(date.toString());
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
