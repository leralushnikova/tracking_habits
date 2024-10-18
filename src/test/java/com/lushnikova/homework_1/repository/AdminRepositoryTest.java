package com.lushnikova.homework_1.repository;

import com.lushnikova.homework_1.model.Admin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminRepositoryTest {

    private final AdminRepository adminRepository = AdminRepository.getInstance();

    @Test
    void shouldSaveAdmin(){
        Admin admin = createAdmin();
        adminRepository.save(admin);

        Admin adminFromRepository = adminRepository
                .findAll()
                .stream()
                .filter(admin::equals)
                .findFirst()
                .orElseThrow();

        assertEquals(admin, adminFromRepository);
    }

    @Test
    void shouldUpdatePasswordAdmin(){
        Admin admin = createAdmin();
        adminRepository.save(admin);
        String newPassword = "new_password";
        admin.setPassword(newPassword);

        adminRepository.updatePassword(admin.getId(), newPassword);
        Admin adminFromRepository = adminRepository
                .findAll()
                .stream()
                .filter(admin::equals)
                .findFirst()
                .orElseThrow();

        assertEquals(newPassword, adminFromRepository.getPassword());
    }

    private Admin createAdmin(){
        return new Admin("admin", "admin");
    }
  
}