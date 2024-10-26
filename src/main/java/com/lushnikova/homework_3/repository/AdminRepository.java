package com.lushnikova.homework_3.repository;

import com.lushnikova.homework_3.model.Admin;

import java.util.HashSet;
import java.util.Set;

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
            admin.setId((long)(i + 1));
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
        admin.setId((long)(admins.size() + 1));
        admins.add(admin);
    }

    /**
     * Функция получения администратора из списка {@link AdminRepository#admins}
     * @param id - id администратора
     * @return возвращает объект администратора
     */
    public Admin findById(Long id){
        return admins.stream().filter(admin -> admin.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Процедура обновления пароля администратора, которого получаем из списка {@link AdminRepository#findById(Long)}
     * @param id - объект id администратора
     * @param newPassword - пароль администратора
     */
    public synchronized void updatePassword(Long id, String newPassword) {
        findById(id).setPassword(newPassword);
    }

    /**
     * Процедура удаления администратора
     * @param id - объект id администратора
     */
    public synchronized void delete(Long id) {
        admins.remove(findById(id));
    }

    /**
     * Функция получения списка администраторов {@link AdminRepository#admins}
     * @return возвращает копию списка администраторов
     */
    public synchronized Set<Admin> findAll(){
        return admins;
    }


}
