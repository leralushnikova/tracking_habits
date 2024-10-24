package com.lushnikova.homework_2.service.impl;

import com.lushnikova.homework_2.dto.req.AdminRequest;
import com.lushnikova.homework_2.dto.resp.AdminResponse;
import com.lushnikova.homework_2.dto.resp.UserResponse;
import com.lushnikova.homework_2.mapper.AdminMapper;
import com.lushnikova.homework_2.mapper.UserMapper;
import com.lushnikova.homework_2.model.Admin;
import com.lushnikova.homework_2.repository.AdminRepository;
import com.lushnikova.homework_2.repository.UserRepository;
import com.lushnikova.homework_2.service.AdminService;

import java.sql.SQLException;
import java.util.List;

/**
 * Класс Service по управлению администраторами и пользователями
 */
public class AdminServiceImpl implements AdminService {
    /** Поле репозиторий пользователей*/
    private final UserRepository userRepository;

    /** Поле репозиторий администраторов*/
    private final AdminRepository adminRepository;

    /** Поле преобразования администраторов*/
    private final AdminMapper adminMapper;

    /** Поле преобразования пользователей*/
    private final UserMapper userMapper;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param userRepository - сервис пользователей
     * инициализация поля преобразования администраторов
     */
    public AdminServiceImpl(UserRepository userRepository, AdminRepository repository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.adminRepository = repository;
        this.userMapper = userMapper;
        this.adminMapper = AdminMapper.INSTANCE;
    }

    /**
     * Процедура сохранения администратора {@link AdminRepository#save(Admin)}
     * @param adminRequest - объект администратора
     * @throws SQLException
     */
    @Override
    public void save(AdminRequest adminRequest) throws SQLException {
        adminRepository.save(adminMapper.mapToEntity(adminRequest));
    }

    /**
     * Функция получения администратора {@link AdminRepository#findById(Long)}
     * @param id - id администратора
     * @return возвращает объект администратора
     * @throws SQLException
     */
    @Override
    public AdminResponse findById(Long id) throws SQLException {
        return adminMapper.mapToResponse(adminRepository.findById(id));
    }

    /**
     * Процедура обновления пароля администратора {@link AdminRepository#updatePassword(Long, String)}
     * @param id - объект id администратора
     * @param newPassword - пароль администратора
     * @throws SQLException
     */
    @Override
    public void updatePassword(Long id, String newPassword) throws SQLException {
        adminRepository.updatePassword(id, newPassword);
    }

    /**
     * Функция получения списка администраторов {@link AdminRepository#findAll}
     * @return возвращает списка администраторов
     * @throws SQLException
     */
    @Override
    public List<AdminResponse> findAll() throws SQLException {
        return adminRepository.findAll().stream().map(adminMapper::mapToResponse).toList();
    }

    @Override
    public UserResponse findByIdUser(Long idUser) throws SQLException {
        return userMapper.mapToResponse(userRepository.findById(idUser));
    }

    /**
     * Функция получения пользователя {@link UserRepository#findAll}
     * @return возвращает объект пользователя
     * @throws SQLException
     */
    @Override
    public List<UserResponse> findAllUsers() throws SQLException {
        return userRepository.findAll().stream().map(userMapper::mapToResponse).toList();
    }

    /**
     * Процедура удаления пользователя {@link UserRepository#delete(Long)}
     * @param idUser - id пользователя
     * @throws SQLException
     */
    @Override
    public void deleteUser(Long idUser) throws SQLException {
        userRepository.delete(idUser);
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

}
