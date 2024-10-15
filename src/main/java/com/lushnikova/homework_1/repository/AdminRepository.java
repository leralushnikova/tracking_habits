package com.lushnikova.homework_1.repository;

import com.lushnikova.homework_1.model.Admin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class AdminRepository {
    private static final AdminRepository INSTANCE;
    private final CopyOnWriteArrayList<Admin> admins;

    static {
        INSTANCE = new AdminRepository();
    }

    private AdminRepository() {
        admins = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 3; i++) {
            Admin admin = new Admin();
            admin.setEmail("admin" + i);
            admin.setPassword("admin" + i);
            admins.add(admin);
        }
    }

    public static AdminRepository getInstance() {
        return INSTANCE;
    }

    public synchronized void save(Admin admin) {
        admins.add(admin);
    }

    public Admin findById(UUID id){
        return admins.stream().filter(admin -> admin.getId().equals(id)).findFirst().orElse(null);
    }

    public synchronized void updatePassword(UUID id, String newPassword) {
        findById(id).setPassword(newPassword);
    }

    public synchronized List<Admin> findAll(){
        return new ArrayList<>(admins);
    }


}
