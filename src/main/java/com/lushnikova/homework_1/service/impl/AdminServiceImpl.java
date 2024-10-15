package com.lushnikova.homework_1.service.impl;

import com.lushnikova.homework_1.dto.req.AdminRequest;
import com.lushnikova.homework_1.dto.resp.AdminResponse;
import com.lushnikova.homework_1.dto.resp.UserResponse;
import com.lushnikova.homework_1.exception.ModelNotFound;
import com.lushnikova.homework_1.mapper_mapstruct.AdminMapper;
import com.lushnikova.homework_1.repository.AdminRepository;
import com.lushnikova.homework_1.service.UserService;
import com.lushnikova.homework_1.service.AdminService;

import java.util.List;
import java.util.UUID;

public class AdminServiceImpl implements AdminService {
    private final UserService userService;
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    public AdminServiceImpl(UserService userService, AdminRepository adminRepository) {
        this.userService = userService;
        this.adminRepository = adminRepository;
        this.adminMapper = AdminMapper.INSTANCE;
    }


    @Override
    public void saveAdmin(AdminRequest adminRequest) {
        adminRepository.save(adminMapper.mapToEntity(adminRequest));
    }

    @Override
    public AdminResponse findById(UUID id) {
        return adminMapper.mapToResponse(adminRepository.findById(id));
    }

    @Override
    public void updatePassword(UUID id, String password) {
        adminRepository.updatePassword(id, password);
    }

    @Override
    public List<AdminResponse> findAllAdmins() {
        return adminRepository.findAll().stream().map(adminMapper::mapToResponse).toList();
    }

    @Override
    public UserResponse findByIdUser(UUID idUser) {
        return userService.findById(idUser);
    }

    @Override
    public List<UserResponse> findAllUsers() {
        return userService.findAll();
    }

    @Override
    public void deletePerson(UUID idUser) {
        userService.delete(idUser);
    }

    @Override
    public void blockByIpPerson(UUID idUser, boolean isActive) throws ModelNotFound {
        userService.setIsActiveByIdPerson(idUser, isActive);
    }


}
