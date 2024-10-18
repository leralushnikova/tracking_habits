package com.lushnikova.homework_1.service;

import com.lushnikova.homework_1.dto.req.AdminRequest;
import com.lushnikova.homework_1.dto.resp.AdminResponse;
import com.lushnikova.homework_1.dto.resp.UserResponse;
import com.lushnikova.homework_1.exception.ModelNotFound;

import java.util.List;
import java.util.UUID;

/**
 * Интерфейс Service по управлению администраторами и пользователями
 */
public interface AdminService {

    /**
     * Процедура сохранения администратора
     * @param adminRequest - объект администратора
     */
    void saveAdmin(AdminRequest adminRequest);

    /**
     * Функция получения администратора
     * @param id - id администратора
     * @return возвращает объект администратора
     */
    AdminResponse findById(UUID id);

    /**
     * Процедура обновления пароля администратора
     * @param id - объект id администратора
     * @param newPassword - пароль администратора
     */
    void updatePassword(UUID id, String newPassword);

    /**
     * Функция получения списка администраторов
     * @return возвращает списка администраторов
     */
    List<AdminResponse> findAllAdmins();

    /**
     * Функция получения списка пользователей
     * @return возвращает список пользователй
     */
    List<UserResponse> findAllUsers();

    /**
     * Функция получения пользователя
     * @param idUser - id пользователя
     * @return возвращает объект пользователя
     */
    UserResponse findByIdUser(UUID idUser);

    /**
     * Процедура удаления пользователя
     * @param idUser - id пользователя
     */
    void deleteUser(UUID idUser);

    /**
     * Процедура блокирования пользователя
     * @param idUser - id пользователя
     * @param isActive - блокировка пользователя
     * @throws ModelNotFound
     */
    void blockByIdUser(UUID idUser, boolean isActive) throws ModelNotFound;

}
