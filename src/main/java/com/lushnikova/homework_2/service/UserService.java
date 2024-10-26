package com.lushnikova.homework_2.service;

import com.lushnikova.homework_2.dto.request.UserRequest;
import com.lushnikova.homework_2.dto.response.UserResponse;

import java.sql.SQLException;
import java.util.List;

/**
 * Интерфейс Service по управлению пользователями и их привычек
 */
public interface UserService extends Service {

    /** Процедура сохранения пользователя
     * @param userRequest - пользователя
     * @return возвращает объект пользователя
     * @throws SQLException
     */
    UserResponse save(UserRequest userRequest) throws SQLException;

    /** Функция получения пользователя по
     * @param id - id пользователя
     * @return возвращает объект пользователя
     * @throws SQLException
     */
    UserResponse findById(Long id) throws SQLException;

    /**
     * Процедура обновления имени пользователя
     * @param id - id пользователя
     * @param name - новое имя пользователя
     * @throws SQLException
     */
    void updateName(Long id, String name) throws SQLException;

    /**
     * Процедура обновления почты пользователя
     * @param id - id пользователя
     * @param email - новое имя пользователя
     * @throws SQLException
     */
    void updateEmail(Long id, String email) throws SQLException;

    /**
     * Процедура обновления пароля пользователя
     * @param id - id пользователя
     * @param password - новый пароль пользователя
     * @throws SQLException
     */
    void updatePassword(Long id, String password) throws SQLException;

    /**
     * Процедура удаления пользователя,
     * @param id - id пользователя
     * @throws SQLException
     */
    void delete(Long id) throws SQLException;

    /**
     * Функция получения списка администраторов
     * @return возвращает копию списка администраторов
     * @throws SQLException
     */
    List<UserResponse> findAll() throws SQLException;

}
