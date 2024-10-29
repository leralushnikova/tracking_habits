package com.lushnikova.homework_3.middleware;

import com.lushnikova.homework_3.dto.request.AdminRequest;
import com.lushnikova.homework_3.dto.response.AdminResponse;
import com.lushnikova.homework_3.service.AdminService;

/**
 * Класс для проверки {@see AdminResponse} реализующий интерфейс {@see Middleware}
 */
public class AdminMiddleware extends Middleware {

    /**
     * Конструктор - создание нового объекта
     * @param adminService - сервис администраторов
     */
    public AdminMiddleware(AdminService adminService) {
        super(adminService);
    }

    /**
     * Операция проверки почты администратора {@link Middleware#checkEmail(Object)}
     * @param object - объект
     * @return возвращает подтверждения совпадений
     */
    @Override
    public boolean checkEmail(Object object) {
        if (object instanceof AdminRequest) {

            AdminService adminService = (AdminService) service;

            for(AdminResponse admin : adminService.findAll()) {
                if(admin.getEmail().equals(((AdminRequest) object).getEmail())) {
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
        if (object instanceof Admin) {
            return ((Admin) object).getPassword().equals(password);
        }
        return false;
    }*/

}
