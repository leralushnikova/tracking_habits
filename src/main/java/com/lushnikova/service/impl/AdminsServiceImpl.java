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

public class AdminsServiceImpl implements AdminService {
    private final UserService userService;
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    public AdminsServiceImpl(UserService userService, AdminRepository adminRepository, AdminMapper adminMapper) {
        this.userService = userService;
        this.adminRepository = adminRepository;
        this.adminMapper = adminMapper;
    }


    @Override
    public void saveAdmin(AdminRequest adminRequest) {
        adminRepository.save(adminMapper.mapToEntity(adminRequest));
    }

    @Override
    public List<AdminResponse> getAllAdmins() {
        return adminRepository.findAll().stream().map(adminMapper::mapToResponse).toList();
    }

    @Override
    public List<UserResponse> findAllPerson() {
        return userService.findAll();
    }

    @Override
    public void deletePerson(UUID idPerson) {
        userService.delete(idPerson);
    }

    @Override
    public void blockByIpPerson(UUID idPerson, boolean isActive) throws ModelNotFound {
        userService.setIsActiveByIdPerson(idPerson, isActive);
    }
}
