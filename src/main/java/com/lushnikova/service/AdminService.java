package com.lushnikova.service;

import com.lushnikova.dto.req.AdminRequest;
import com.lushnikova.dto.resp.AdminResponse;
import com.lushnikova.dto.resp.PersonResponse;
import com.lushnikova.exception.ModelNotFound;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    void saveAdmin(AdminRequest adminRequest);
    List<AdminResponse> getAllAdmins();

    List<PersonResponse> findAllPerson();
    void deletePerson(UUID idPerson);
    void blockByIpPerson(UUID idPerson, boolean isActive) throws ModelNotFound;

}
