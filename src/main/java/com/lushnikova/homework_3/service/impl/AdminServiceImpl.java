package com.lushnikova.homework_3.service.impl;

import com.lushnikova.homework_3.dto.resp.UserResponse;
import com.lushnikova.homework_3.mapper.AdminMapper;
import com.lushnikova.homework_3.mapper.UserMapper;
import com.lushnikova.homework_3.repository.UserRepository;
import com.lushnikova.homework_3.dto.req.AdminRequest;
import com.lushnikova.homework_3.dto.resp.AdminResponse;
import com.lushnikova.homework_3.model.Admin;
import com.lushnikova.homework_3.repository.AdminRepository;
import com.lushnikova.homework_3.service.AdminService;

import java.util.List;
import java.util.UUID;

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
     */
    public AdminServiceImpl() {
        this.userRepository = UserRepository.getInstance();
        this.adminRepository = AdminRepository.getInstance();
        this.userMapper = UserMapper.INSTANCE;
        this.adminMapper = AdminMapper.INSTANCE;
    }

    /**
     * Процедура сохранения администратора {@link AdminRepository#save(Admin)}
     * @param adminRequest - объект администратора
     */
    @Override
    public void save(AdminRequest adminRequest) {
        adminRepository.save(adminMapper.mapToEntity(adminRequest));
    }

    /**
     * Функция получения администратора {@link AdminRepository#findById(Long)}
     * @param id - id администратора
     * @return возвращает объект администратора
     */
    @Override
    public AdminResponse findById(Long id) {
        return adminMapper.mapToResponse(adminRepository.findById(id));
    }

    /**
     * Процедура обновления пароля администратора {@link AdminRepository#updatePassword(Long, String)}
     * @param id - объект id администратора
     * @param newPassword - пароль администратора
     */
    @Override
    public void updatePassword(Long id, String newPassword) {
        adminRepository.updatePassword(id, newPassword);
    }

    /**
     * Процедура удаления администратора
     *
     * @param id - объект id администратора
     */
    @Override
    public void delete(Long id) {
        adminRepository.delete(id);
    }

    /**
     * Функция получения списка администраторов {@link AdminRepository#findAll}
     * @return возвращает списка администраторов
     */
    @Override
    public List<AdminResponse> findAll() {
        return adminRepository.findAll().stream().map(adminMapper::mapToResponse).toList();
    }

    /**
     * Функция получения пользователя {@link com.lushnikova.homework_1.repository.UserRepository#findAll}
     * @return возвращает объект пользователя
     */
    @Override
    public List<UserResponse> findAllUsers() {
        return userRepository.findAll().stream().map(userMapper::mapToResponse).toList();
    }

    /**
     * Процедура удаления пользователя {@link com.lushnikova.homework_1.repository.UserRepository#delete(UUID)}
     * @param idUser - id пользователя
     */
    @Override
    public void deleteUser(Long idUser) {
        userRepository.delete(idUser);
    }

    /**
     * Процедура блокирования пользователя {@link com.lushnikova.homework_1.repository.UserRepository#setIsActiveByIdUser(UUID, boolean)}
     * @param idUser - id пользователя
     * @param isActive - блокировка пользователя
     */
    @Override
    public void blockByIdUser(Long idUser, boolean isActive){
        userRepository.setIsActiveByIdUser(idUser, isActive);
    }
}
