package com.lushnikova.homework_3.middleware;

import com.lushnikova.homework_3.dto.req.UserRequest;
import com.lushnikova.homework_3.dto.resp.UserResponse;
import com.lushnikova.homework_3.service.UserService;

/**
 * Класс для проверки {@see UserResponse} реализующий интерфейс {@see Middleware}
 */
public class UserMiddleware extends Middleware {

    /**
     * Конструктор - создание нового объекта
     * @param userService - сервис администраторов
     */
    public UserMiddleware(UserService userService) {
        super(userService);
    }

    /**
     * Операция проверки почты администратора {@link Middleware#checkEmail(Object)}
     * @param object - объект
     * @return возвращает подтверждения совпадений
     */
    @Override
    public boolean checkEmail(Object object) {
        if (object instanceof UserRequest) {

            UserService userService = (UserService) service;

            for(UserResponse user : userService.findAll()) {
                if(user.getEmail().equals(((UserRequest) object).getEmail())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Операция проверки пароля администратора {@link Middleware#checkPassword(String, Object)}
     * @param password - пароль администратора
     * @param object - объект
     * @return возвращает подтверждения совпадений
     */
    /*@Override
    public boolean checkPassword(String password, Object object) {
        if (object instanceof User) {
            return ((User) object).getPassword().equals(password);
        }
        return false;
    }*/

}
