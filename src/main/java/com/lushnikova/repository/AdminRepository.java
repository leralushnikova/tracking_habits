package com.lushnikova.repository;

import com.lushnikova.model.Admin;

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
    }

    public synchronized Admin save(Admin admin) {
        admins.add(admin);
        return admin;
    }

    public Admin findById(UUID id){
        return admins.stream().filter(admin -> admin.getId().equals(id)).findFirst().orElse(null);
    }

    public synchronized List<Admin> findAll(){
        return new ArrayList<>(admins);
    }


}
