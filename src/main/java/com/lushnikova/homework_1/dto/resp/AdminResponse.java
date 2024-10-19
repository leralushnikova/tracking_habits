package com.lushnikova.homework_1.dto.resp;

import java.util.Objects;
import java.util.UUID;

/**
 * Класс AdminResponse является объект response для класса {@see Admin}
 */
public class AdminResponse {
    /** Поле уникальный идентификатор */
    private UUID id;

    /** Поле почта */
    private String email;

    /** Поле пароль */
    private String password;

    /**
     * Пустой конструктор для создания нового объекта
     */
    public AdminResponse() {
    }

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param id - идентификатор
     * @param email - почта
     * @param password - пароль
     */
    public AdminResponse(UUID id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    /**
     * Процедура определения значения поля {@link AdminResponse#id}
     * @param id - идентификатор
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Функция получения значения поля {@link AdminResponse#id}
     * @return возвращает идентификатор администратора
     */
    public UUID getId() {
        return id;
    }

    /**
     * Функция получения значения поля {@link AdminResponse#email}
     * @return возвращает почту администратора
     */
    public String getEmail() {
        return email;
    }

    /**
     * Процедура определения значения поля {@link AdminResponse#email}
     * @param email - почта
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Функция получения значения поля {@link AdminResponse#password}
     * @return возвращает пароль администратора
     */
    public String getPassword() {
        return password;
    }

    /**
     * Процедура определения значения поля {@link AdminResponse#password}
     * @param password - пароль
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Переопределенный метод {@link Object#equals(Object)}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdminResponse admin)) return false;
        return Objects.equals(id, admin.id) && Objects.equals(email, admin.email) && Objects.equals(password, admin.password);
    }

    /**
     * Переопределенный метод {@link Object#hashCode}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, email, password);
    }

    /**
     * Переопределенный метод {@link Object#toString}
     */
    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
