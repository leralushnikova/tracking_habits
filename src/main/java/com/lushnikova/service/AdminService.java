package com.lushnikova.service;

import com.lushnikova.dto.resp.PersonResponse;
import com.lushnikova.exception.ModelNotFound;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    List<PersonResponse> findAllPerson();
    void delete(UUID idPerson);

    void blockByIpPerson(UUID idPerson, boolean isActive) throws ModelNotFound;
}
