package com.lushnikova.homework_2.service;

import com.lushnikova.homework_2.dto.req.AdminRequest;
import com.lushnikova.homework_2.dto.resp.AdminResponse;
import com.lushnikova.homework_2.dto.resp.UserResponse;

import java.sql.SQLException;
import java.util.List;

/**
 * Интерфейс Service по управлению администраторами и пользователями
 */
public interface AdminService extends Service {

    /**
     * Процедура сохранения администратора
     * @param adminRequest - объект администратора
     * @throws SQLException
     */
    void save(AdminRequest adminRequest) throws SQLException;

    /**
     * Функция получения администратора
     * @param id - id администратора
     * @return возвращает объект администратора
     * @throws SQLException
     */
    AdminResponse findById(Long id) throws SQLException;

    /**
     * Процедура обновления пароля администратора
     * @param id - объект id администратора
     * @param newPassword - пароль администратора
     * @throws SQLException
     */
    void updatePassword(Long id, String newPassword) throws SQLException;

    /**
     * Функция получения списка администраторов
     * @return возвращает списка администраторов
     * @throws SQLException
     */
    List<AdminResponse> findAll() throws SQLException;

    /**
     * Функция получения списка пользователей
     * @return возвращает список пользователй
     * @throws SQLException
     */
    List<UserResponse> findAllUsers() throws SQLException;

    /**
     * Функция получения пользователя
     * @param idUser - id пользователя
     * @return возвращает объект пользователя
     * @throws SQLException
     */
    UserResponse findByIdUser(Long idUser) throws SQLException;

    /**
     * Процедура удаления пользователя
     * @param idUser - id пользователя
     * @throws SQLException
     */
    void deleteUser(Long idUser) throws SQLException;

    /**
     * Процедура блокирования пользователя
     * @param idUser - id пользователя
     * @param isActive - блокировка пользователя
     * @throws SQLException
     */
    void blockByIdUser(Long idUser, boolean isActive) throws SQLException;

}
