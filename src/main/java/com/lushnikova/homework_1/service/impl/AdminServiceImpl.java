package com.lushnikova.homework_1.service.impl;

import com.lushnikova.homework_1.dto.req.AdminRequest;
import com.lushnikova.homework_1.dto.resp.AdminResponse;
import com.lushnikova.homework_1.dto.resp.UserResponse;
import com.lushnikova.homework_1.exception.ModelNotFound;
import com.lushnikova.homework_1.mapper_mapstruct.AdminMapper;
import com.lushnikova.homework_1.model.Admin;
import com.lushnikova.homework_1.repository.AdminRepository;
import com.lushnikova.homework_1.repository.UserRepository;
import com.lushnikova.homework_1.service.UserService;
import com.lushnikova.homework_1.service.AdminService;

import java.util.List;
import java.util.UUID;

/**
 * Класс Service по управлению администраторами и пользователями
 */
public class AdminServiceImpl implements AdminService {
    /** Поле сервис пользователей*/
    private final UserService userService;

    /** Поле репозиторий администраторов*/
    private final AdminRepository adminRepository;

    /** Поле преобразования администраторов*/
    private final AdminMapper adminMapper;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param userService - сервис пользователей
     * @param adminRepository - репозиторий администраторов
     * инициализация поля преобразования администраторов
     */
    public AdminServiceImpl(UserService userService, AdminRepository adminRepository) {
        this.userService = userService;
        this.adminRepository = adminRepository;
        this.adminMapper = AdminMapper.INSTANCE;
    }

    /**
     * Процедура сохранения администратора {@link AdminRepository#save(Admin)}
     * @param adminRequest - объект администратора
     */
    @Override
    public void saveAdmin(AdminRequest adminRequest) {
        adminRepository.save(adminMapper.mapToEntity(adminRequest));
    }

    /**
     * Функция получения администратора {@link AdminRepository#findById(UUID)}
     * @param id - id администратора
     * @return возвращает объект администратора
     */
    @Override
    public AdminResponse findById(UUID id) {
        return adminMapper.mapToResponse(adminRepository.findById(id));
    }

    /**
     * Процедура обновления пароля администратора {@link AdminRepository#updatePassword(UUID, String)}
     * @param id - объект id администратора
     * @param newPassword - пароль администратора
     */
    @Override
    public void updatePassword(UUID id, String newPassword) {
        adminRepository.updatePassword(id, newPassword);
    }

    /**
     * Функция получения списка администраторов {@link AdminRepository#findAll}
     * @return возвращает списка администраторов
     */
    @Override
    public List<AdminResponse> findAllAdmins() {
        return adminRepository.findAll().stream().map(adminMapper::mapToResponse).toList();
    }

    @Override
    public UserResponse findByIdUser(UUID idUser) {
        return userService.findById(idUser);
    }

    /**
     * Функция получения пользователя {@link UserRepository#findAll}
     * @return возвращает объект пользователя
     */
    @Override
    public List<UserResponse> findAllUsers() {
        return userService.findAll();
    }

    /**
     * Процедура удаления пользователя {@link UserRepository#delete(UUID)}
     * @param idUser - id пользователя
     */
    @Override
    public void deleteUser(UUID idUser) {
        userService.delete(idUser);
    }

    /**
     * Процедура блокирования пользователя {@link UserRepository#setIsActiveByIdUser(UUID, boolean)}
     * @param idUser - id пользователя
     * @param isActive - блокировка пользователя
     */
    @Override
    public void blockByIdUser(UUID idUser, boolean isActive) throws ModelNotFound {
        userService.setIsActiveByIdUser(idUser, isActive);
    }

}
