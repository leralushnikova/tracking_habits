package com.lushnikova.homework_1.middleware;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            format.parse(date.toString());
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}
