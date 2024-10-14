package com.lushnikova.service.impl;

import com.lushnikova.dto.resp.PersonResponse;
import com.lushnikova.exception.ModelNotFound;
import com.lushnikova.service.AdminService;
import com.lushnikova.service.PersonService;

import java.util.List;
import java.util.UUID;

public class AdminsServiceImpl implements AdminService {
    private final PersonService personService;

    public AdminsServiceImpl(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public List<PersonResponse> findAllPerson() {
        return personService.findAll();
    }

    @Override
    public void delete(UUID idPerson) {
        personService.delete(idPerson);
    }

    @Override
    public void blockByIpPerson(UUID idPerson, boolean isActive) throws ModelNotFound {
        personService.setIsActiveByIdPerson(idPerson, isActive);
    }
}
