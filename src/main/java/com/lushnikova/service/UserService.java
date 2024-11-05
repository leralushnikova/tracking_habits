package com.lushnikova.service;

import com.lushnikova.dto.request.UserRequest;
import com.lushnikova.dto.response.UserResponse;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс Service по управлению пользователями и их привычек
 */
public interface UserService{

    /**
     * Процедура сохранения пользователя
     *
     * @param userRequest - пользователя
     */
    Optional<UserResponse> save(UserRequest userRequest);

    /** Функция получения пользователя по
     * @param id - id пользователя
     * @return возвращает объект пользователя
     */
    Optional<UserResponse> findById(Long id);

    /**
     * Процедура обновления имени пользователя
     * @param id - id пользователя
     * @param name - новое имя пользователя
     */
    void updateName(Long id, String name);

    /**
     * Процедура обновления почты пользователя
     * @param id - id пользователя
     * @param email - новое имя пользователя
     */
    void updateEmail(Long id, String email);

    /**
     * Процедура обновления пароля пользователя
     * @param id - id пользователя
     * @param password - новый пароль пользователя
     */
    void updatePassword(Long id, String password);

    /**
     * Процедура удаления пользователя,
     * @param id - id пользователя
     */
    void delete(Long id);

    /**
     * Процедура блокирования пользователя
     * @param idUser - id пользователя
     * @param isActive - блокировка пользователя
     */
    void blockByIdUser(Long idUser, boolean isActive);

    /**
     * Функция получения списка администраторов
     * @return возвращает копию списка администраторов
     */
    List<UserResponse> findAll();


}
