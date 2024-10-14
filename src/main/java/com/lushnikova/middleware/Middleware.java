package com.lushnikova.middleware;


import com.lushnikova.consts.RegexConsts;

public interface Middleware {

    //проверка почты
    boolean checkEmail(String email, Object person);

    //проверка почты
    boolean checkPassword(String password, Object person);

    default boolean checkPassword(String password) {
        return password.matches(RegexConsts.REGEX_PASSWORD);
    }
}