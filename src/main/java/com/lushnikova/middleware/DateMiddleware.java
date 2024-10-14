package com.lushnikova.middleware;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class DateMiddleware {

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
