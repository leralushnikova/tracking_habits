package com.lushnikova.homework_2.service.impl;

import com.lushnikova.homework_2.dto.request.UserRequest;
import com.lushnikova.homework_2.dto.response.UserResponse;
import com.lushnikova.homework_2.mapper.UserMapper;
import com.lushnikova.homework_2.model.User;
import com.lushnikova.homework_2.repository.UserRepository;
import com.lushnikova.homework_2.service.UserService;

import java.sql.SQLException;
import java.util.List;

/**
 * Класс Service по управлению пользователями и их привычек
 */
public class UserServiceImpl implements UserService {

    /** Поле преобразования пользователей*/
    private final UserMapper userMapper;

    /** Поле репозиторий пользователей*/
    private final UserRepository userRepository;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param userMapper - инструмент преобразования пользователей
     * @param userRepository - репозиторий пользователей
     * инициализация поля преобразования привычек
     */
    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    /** Процедура сохранения пользователя {@link UserRepository#save(User)}
     * @param userRequest - пользователя
     * @return возвращает объект пользователя
     * @throws SQLException
     */
    @Override
    public UserResponse save(UserRequest userRequest) throws SQLException {
        User user = userMapper.mapToEntity(userRequest);
        return userMapper.mapToResponse(userRepository.save(user));
    }

    /** Функция получения пользователя {@link UserRepository#findById(Long)}
     * @param id - id пользователя
     * @return возвращает объект пользователя
     * @throws SQLException
     */
    @Override
    public UserResponse findById(Long id) throws SQLException {
        return userMapper.mapToResponse(userRepository.findById(id));
    }

    /**
     * Процедура обновления имени пользователя {@link UserRepository#updateName(Long, String)}
     * @param id - id пользователя
     * @param name - новое имя пользователя
     * @throws SQLException
     */
    @Override
    public void updateName(Long id, String name) throws SQLException {
        userRepository.updateName(id, name);
    }

    /**
     * Процедура обновления почты пользователя {@link UserRepository#updateEmail(Long, String)}
     * @param id - id пользователя
     * @param email - новое имя пользователя
     * @throws SQLException
     */
    @Override
    public void updateEmail(Long id, String email) throws SQLException {
        userRepository.updateEmail(id, email);
    }

    /**
     * Процедура обновления почты пользователя {@link UserRepository#updatePassword(Long, String)}
     * @param id - id пользователя
     * @param password - новое имя пользователя
     * @throws SQLException
     */
    @Override
    public void updatePassword(Long id, String password) throws SQLException {
        userRepository.updatePassword(id, password);
    }

    /**
     * Процедура удаления пользователя {@link UserRepository#delete(Long)}
     * @param id - id пользователя
     * @throws SQLException
     */
    @Override
    public void delete(Long id) throws SQLException {
        userRepository.delete(id);
    }

    /**
     * Процедура блокирования пользователя {@link UserRepository#setIsActive(Long, boolean)}
     * @param idUser - id пользователя
     * @param isActive - блокировка пользователя
     * @throws SQLException
     */
    @Override
    public void blockByIdUser(Long idUser, boolean isActive) throws SQLException {
        userRepository.setIsActive(idUser, isActive);
    }


    /**
     * Функция получения списка администраторов {@link UserRepository#findAll()}
     * @return возвращает копию списка администраторов
     * @throws SQLException
     */
    @Override
    public List<UserResponse> findAll() throws SQLException {
        return userRepository.findAll().stream().map(userMapper::mapToResponse).toList();
    }
}
