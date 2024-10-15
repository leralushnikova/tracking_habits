package com.lushnikova.homework_1.service;

import com.lushnikova.homework_1.dto.req.AdminRequest;
import com.lushnikova.homework_1.dto.resp.AdminResponse;
import com.lushnikova.homework_1.dto.resp.UserResponse;
import com.lushnikova.homework_1.exception.ModelNotFound;

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
