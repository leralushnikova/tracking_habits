package com.lushnikova.service;

import com.lushnikova.dto.req.AdminRequest;
import com.lushnikova.dto.resp.AdminResponse;
import com.lushnikova.dto.resp.UserResponse;
import com.lushnikova.exception.ModelNotFound;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    void saveAdmin(AdminRequest adminRequest);
    AdminResponse findById(UUID id);
    void updatePassword(UUID id, String password);
    List<AdminResponse> findAllAdmins();

    List<UserResponse> findAllUsers();
    UserResponse findByIdUser(UUID idUser);
    void deletePerson(UUID idUser);
    void blockByIpPerson(UUID idUser, boolean isActive) throws ModelNotFound;

}
