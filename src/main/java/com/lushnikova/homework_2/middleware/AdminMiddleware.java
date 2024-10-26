package com.lushnikova.homework_2.middleware;

import com.lushnikova.homework_2.dto.response.AdminResponse;
import com.lushnikova.homework_2.model.Admin;

/**
 * Класс для проверки {@see AdminResponse} реализующий интерфейс {@see Middleware}
 */
public class AdminMiddleware implements Middleware {

    /**
     * Операция проверки почты администратора {@link Middleware#checkEmail(String, Object)}
     * @param email - почта администратора
     * @param object - объект
     * @return возвращает подтверждения совпадений
     */
    @Override
    public boolean checkEmail(String email, Object object) {
        if (object instanceof AdminResponse) {
            return ((AdminResponse) object).getEmail().equals(email);
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
        if (object instanceof Admin) {
            return ((Admin) object).getPassword().equals(password);
        }
        return false;
    }

}
