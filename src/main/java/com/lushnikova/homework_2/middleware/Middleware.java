package com.lushnikova.homework_2.middleware;


import com.lushnikova.homework_2.consts.RegexConsts;

/**
 * Операции проверки
 */
public interface Middleware {

    /**
     * Операция проверки почты
     * @param email - почта
     * @param object - объект
     * @return возвращает подтверждения совпадений
     */
    boolean checkEmail(String email, Object object);

    /**
     * Операция проверки пароля
     * @param password - пароль
     * @param object - объект
     * @return возвращает подтверждения совпадений
     */
    boolean checkPassword(String password, Object object);

    /**
     * Операция проверки пароля с регулярным выражение {@link RegexConsts#REGEX_PASSWORD}
     * @param password - пароль
     * @return возвращает подтверждения совпадений
     */
    default boolean checkPassword(String password) {
        return password.matches(RegexConsts.REGEX_PASSWORD);
    }
}