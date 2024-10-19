package com.lushnikova.homework_1.repository;

import com.lushnikova.homework_1.model.Admin;

import java.util.*;

/**
 * Класс Repository для администраторов.
 * Используется паттерн Singleton
 */
public class AdminRepository {
    private static final AdminRepository INSTANCE;

    /** Поле списка администраторов */
    private final Set<Admin> admins;

    /** Статический блок инициализации по созданию репозитория единожды */
    static {
        INSTANCE = new AdminRepository();
    }

    /**
     * Приватный пустой конструктор для создания нового объекта
     * и созданий списка администраторов
     */
    private AdminRepository() {
        admins = new HashSet<>();

        for (int i = 0; i < 3; i++) {
            Admin admin = new Admin();
            admin.setEmail("admin" + i);
            admin.setPassword("admin" + i);
            admins.add(admin);
        }
    }

    /** Возвращение экземпляра репозитория*/
    public static AdminRepository getInstance() {
        return INSTANCE;
    }

    /**
     * Процедура сохранения администратора в список {@link AdminRepository#admins}
     * @param admin - объект администратора
     */
    public synchronized void save(Admin admin) {
        admins.add(admin);
    }

    /**
     * Функция получения администратора из списка {@link AdminRepository#admins}
     * @param id - id администратора
     * @return возвращает объект администратора
     */
    public Admin findById(UUID id){
        return admins.stream().filter(admin -> admin.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Процедура обновления пароля администратора, которого получаем из списка {@link AdminRepository#findById(UUID)}
     * @param id - объект id администратора
     * @param newPassword - пароль администратора
     */
    public synchronized void updatePassword(UUID id, String newPassword) {
        findById(id).setPassword(newPassword);
    }

    /**
     * Функция получения списка администраторов {@link AdminRepository#admins}
     * @return возвращает копию списка администраторов
     */
    public synchronized Set<Admin> findAll(){
        return admins;
    }


}
