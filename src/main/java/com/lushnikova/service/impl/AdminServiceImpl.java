package com.lushnikova.service.impl;

import com.lushnikova.dto.req.AdminRequest;
import com.lushnikova.dto.resp.AdminResponse;
import com.lushnikova.dto.resp.UserResponse;
import com.lushnikova.exception.ModelNotFound;
import com.lushnikova.mapper_mapstruct.AdminMapper;
import com.lushnikova.repository.AdminRepository;
import com.lushnikova.service.AdminService;
import com.lushnikova.service.UserService;

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
