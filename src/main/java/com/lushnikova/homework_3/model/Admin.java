package com.lushnikova.homework_3.model;

import java.util.Objects;

/**
 * Класс Администратора
 */
public class Admin {
    /** Поле уникальный идентификатор */
    private Long id;

    /** Поле почта */
    private String email;

    /** Поле пароль */
    private String password;


    /**
     * Пустой конструктор для создания нового объекта
     */
    public Admin() {
    }

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param email - почта
     * @param password - пароль
     */
    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Функция получения значения поля {@link Admin#id}
     * @return возвращает идентификатор администратора
     */
    public Long getId() {
        return id;
    }

    /**
     * Процедура определения значения поля {@Admin User#id}
     * @param id - идентификатор
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Функция получения значения поля {@link Admin#email}
     * @return возвращает почту администратора
     */
    public String getEmail() {
        return email;
    }

    /**
     * Процедура определения значения поля {@link Admin#email}
     * @param email - почта
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Функция получения значения поля {@link Admin#password}
     * @return возвращает пароль администратора
     */
    public String getPassword() {
        return password;
    }

    /**
     * Процедура определения значения поля {@link Admin#password}
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
        if (!(o instanceof Admin admin)) return false;
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
