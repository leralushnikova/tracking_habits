package com.lushnikova.homework_1.service.impl;

import com.lushnikova.homework_1.dto.req.AdminRequest;
import com.lushnikova.homework_1.exception.ModelNotFound;
import com.lushnikova.homework_1.mapper_mapstruct.UserMapper;
import com.lushnikova.homework_1.model.Admin;
import com.lushnikova.homework_1.model.User;
import com.lushnikova.homework_1.repository.AdminRepository;
import com.lushnikova.homework_1.repository.UserRepository;
import com.lushnikova.homework_1.service.AdminService;
import com.lushnikova.homework_1.service.UserService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminServiceImplTest {
    private final UserMapper userMapper = UserMapper.INSTANCE;
    private final AdminRepository adminRepository = AdminRepository.getInstance();
    private final UserRepository userRepository = UserRepository.getInstance();
    private final UserService userService = new UserServiceImpl(userMapper, userRepository);
    private final AdminService adminService = new AdminServiceImpl(userService, adminRepository);


    @Test
    void shouldDeleteUser(){

        User user = userRepository.findAll().get(0);
        adminService.deleteUser(user.getId());

        assertNull(adminService.findByIdUser(user.getId()));
    }

    @Test
    void shouldBlockUser() throws ModelNotFound {
        User user = userRepository.findAll().get(0);
        boolean isActive = false;

        adminService.blockByIdUser(user.getId(), isActive);

        assertFalse(userRepository.findById(user.getId()).isActive());

    }


}