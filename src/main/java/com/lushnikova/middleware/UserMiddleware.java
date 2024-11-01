package com.lushnikova.middleware;

import com.lushnikova.dto.request.UserRequest;
import com.lushnikova.dto.response.UserResponse;
import com.lushnikova.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Класс для проверки {@see UserResponse} реализующий интерфейс {@see Middleware}
 */
@Component
public class UserMiddleware extends Middleware {

    /**
     * Конструктор - создание нового объекта
     * @param userService - сервис администраторов
     */
    @Autowired
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

            for(UserResponse user : service.findAll()) {
                if(user.getEmail().equals(((UserRequest) object).getEmail())) {
                    return true;
                }
            }
        }
        return false;
    }


}