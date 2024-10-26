package com.lushnikova.homework_2.middleware;

import com.lushnikova.homework_2.dto.resp.UserResponse;
import com.lushnikova.homework_2.model.User;

/**
 * Класс для проверки {@see UserResponse} реализующий интерфейс {@see Middleware}
 */
public class UserMiddleware implements Middleware {

    /**
     * Операция проверки почты администратора {@link Middleware#checkEmail(String, Object)}
     * @param email - почта администратора
     * @param object - объект
     * @return возвращает подтверждения совпадений
     */
    @Override
    public boolean checkEmail(String email, Object object) {
        if (object instanceof UserResponse) {
            return ((UserResponse) object).getEmail().equals(email);
        }
        return false;
    }

    /**
     * Операция проверки пароля администратора {@link Middleware#checkPassword(String, Object)}
     * @param password - пароль администратора
     * @param object - объект
     * @return возвращает подтверждения совпадений
     */
    @Override
    public boolean checkPassword(String password, Object object) {
        if (object instanceof User) {
            return ((User) object).getPassword().equals(password);
        }
        return false;
    }

}
