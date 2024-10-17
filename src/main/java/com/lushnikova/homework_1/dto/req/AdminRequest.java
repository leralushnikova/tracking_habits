package com.lushnikova.homework_1.dto.req;

import java.util.Objects;

/**
 * Класс AdminRequest является объект request для класса {@see Admin}
 */
public class AdminRequest {
    /** Поле почта */
    private String email;

    /** Поле пароль */
    private String password;


    /**
     * Пустой конструктор для создания нового объекта
     */
    public AdminRequest() {
    }

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param email - почта
     * @param password - пароль
     */
    public AdminRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * Функция получения значения поля {@link AdminRequest#email}
     * @return возвращает почту администратора
     */
    public String getEmail() {
        return email;
    }

    /**
     * Процедура определения значения поля {@link AdminRequest#email}
     * @param email - почта
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Функция получения значения поля {@link AdminRequest#password}
     * @return возвращает пароль администратора
     */
    public String getPassword() {
        return password;
    }

    /**
     * Процедура определения значения поля {@link AdminRequest#password}
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
        if (!(o instanceof AdminRequest that)) return false;
        return Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    /**
     * Переопределенный метод {@link Object#hashCode}
     */
    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    /**
     * Переопределенный метод {@link Object#toString}
     */
    @Override
    public String toString() {
        return "AdminRequest{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
