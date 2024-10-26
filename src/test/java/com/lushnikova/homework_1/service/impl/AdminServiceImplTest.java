package com.lushnikova.homework_1.service.impl;

import com.lushnikova.homework_1.mapper_mapstruct.UserMapper;
import com.lushnikova.homework_1.model.User;
import com.lushnikova.homework_1.repository.AdminRepository;
import com.lushnikova.homework_1.repository.UserRepository;
import com.lushnikova.homework_1.service.AdminService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Сервис по управлению пользователя администраторами")
class AdminServiceImplTest {

    /** Поле преобразования пользователей*/
    private final UserMapper userMapper = UserMapper.INSTANCE;

    /** Поле репозиторий администраторов*/
    private final AdminRepository adminRepository = AdminRepository.getInstance();

    /** Поле репозиторий пользователей*/
    private final UserRepository userRepository = UserRepository.getInstance();

    /** Поле сервис администраторов*/
    private final AdminService adminService = new AdminServiceImpl(userRepository, adminRepository, userMapper);



    @Test
    @DisplayName("Блокировка пользователя")
    void shouldBlockUser(){
        User user = getUserById();
        boolean isActive = false;

        adminService.blockByIdUser(user.getId(), isActive);

        assertFalse(userRepository.findById(user.getId()).isActive());

    }

    @Test
    @DisplayName("Удаление пользователя")
    void shouldDeleteUser(){

        User user = getUserById();
        adminService.deleteUser(user.getId());

        assertNull(adminService.findByIdUser(user.getId()));
    }

    /**
     * Функция получения пользователя
     * @return возвращает объект пользователя
     */
    private User getUserById(){
        return userRepository.findAll().stream().filter(el -> el.getName().equals("Jame")).findFirst().orElse(null);
    }


}