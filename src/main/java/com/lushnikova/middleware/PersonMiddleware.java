package com.lushnikova.middleware;

import com.lushnikova.consts.RegexConsts;
import com.lushnikova.dto.resp.PersonResponse;


public class PersonMiddleware {

    //проверка почты
    public boolean checkEmail(String email, PersonResponse person) {
        return person.getEmail().equals(email);
    }

    //проверка почты
    public boolean checkPassword(String password, PersonResponse person) {
        return person.getPassword().equals(password);
    }

    //проверка пароля при создании пользователя
    public boolean checkPassword(String password) {
        return password.matches(RegexConsts.REGEX_PASSWORD);
    }

}
