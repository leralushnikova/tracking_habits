package com.lushnikova.service.impl;

import com.lushnikova.dto.req.AdminRequest;
import com.lushnikova.dto.resp.AdminResponse;
import com.lushnikova.dto.resp.PersonResponse;
import com.lushnikova.exception.ModelNotFound;
import com.lushnikova.mapper_mapstruct.AdminMapper;
import com.lushnikova.model.Admin;
import com.lushnikova.repository.AdminRepository;
import com.lushnikova.service.AdminService;
import com.lushnikova.service.PersonService;

import java.util.List;
import java.util.UUID;

public class AdminsServiceImpl implements AdminService {
    private final PersonService personService;
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;

    public AdminsServiceImpl(PersonService personService, AdminRepository adminRepository, AdminMapper adminMapper) {
        this.personService = personService;
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
    public List<PersonResponse> findAllPerson() {
        return personService.findAll();
    }

    @Override
    public void deletePerson(UUID idPerson) {
        personService.delete(idPerson);
    }

    @Override
    public void blockByIpPerson(UUID idPerson, boolean isActive) throws ModelNotFound {
        personService.setIsActiveByIdPerson(idPerson, isActive);
    }
}
