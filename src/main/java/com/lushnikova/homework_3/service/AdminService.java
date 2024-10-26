package com.lushnikova.homework_3.service;

import com.lushnikova.homework_3.dto.resp.UserResponse;
import com.lushnikova.homework_3.exception.ModelNotFound;
import com.lushnikova.homework_3.dto.req.AdminRequest;
import com.lushnikova.homework_3.dto.resp.AdminResponse;

import java.util.List;

/**
 * Интерфейс Service по управлению администраторами и пользователями
 */
public interface AdminService extends Service{

    /**
     * Процедура сохранения администратора
     * @param adminRequest - объект администратора
     */
    void save(AdminRequest adminRequest);

    /**
     * Функция получения администратора
     * @param id - id администратора
     * @return возвращает объект администратора
     * @throws ModelNotFound
     */
    AdminResponse findById(Long id) throws ModelNotFound;

    /**
     * Процедура обновления пароля администратора
     * @param id - объект id администратора
     * @param newPassword - пароль администратора
     * @throws ModelNotFound
     */
    void updatePassword(Long id, String newPassword) throws ModelNotFound;

    /**
     * Процедура удаления администратора
     * @param id - объект id администратора
     * @throws ModelNotFound
     */
    void delete(Long id) throws ModelNotFound;

    /**
     * Функция получения списка администраторов
     * @return возвращает списка администраторов
     */
    List<AdminResponse> findAll();

    /**
     * Функция получения списка пользователей
     * @return возвращает список пользователй
     */
    List<UserResponse> findAllUsers();


    /**
     * Процедура удаления пользователя
     * @param idUser - id пользователя
     * @throws ModelNotFound
     */
    void deleteUser(Long idUser)  throws ModelNotFound;

    /**
     * Процедура блокирования пользователя
     * @param idUser - id пользователя
     * @param isActive - блокировка пользователя
     * @throws ModelNotFound
     */
    void blockByIdUser(Long idUser, boolean isActive)  throws ModelNotFound;
}
