package com.lushnikova.homework_3.middleware;


import com.lushnikova.homework_3.consts.RegexConsts;
import com.lushnikova.homework_3.service.Service;

/**
 * Операции проверки
 */
public abstract class Middleware {

    /** Поле сервис*/
    protected Service service;

    /**
     * Конструктор - создание нового объекта
     * @param service - сервис
     */
    public Middleware(Service service) {
        this.service = service;
    }

    /**
     * Операция проверки почты
     * @param object - объект
     * @return возвращает подтверждения совпадений
     */
    public abstract boolean checkEmail(Object object);

    /**
     * Операция проверки пароля
     * @param password - пароль
     * @param object - объект
     * @return возвращает подтверждения совпадений
     */
//    public abstract boolean checkPassword(String password, Object object);

    /**
     * Операция проверки пароля с регулярным выражение {@link RegexConsts#REGEX_PASSWORD}
     * @param password - пароль
     * @return возвращает подтверждения совпадений
     */
    public boolean checkPassword(String password) {
        return password.matches(RegexConsts.REGEX_PASSWORD);
    }
}