package com.lushnikova.homework_1.dto.req;

import java.util.Objects;

/**
 * Класс UserRequest является объект request для класса {@see User}
 */
public class UserRequest {
    /** Поле имя */
    private String name;

    /** Поле почта */
    private String email;

    /** Поле пароль */
    private String password;

    /**
     * Пустой конструктор для создания нового объекта
     */
    public UserRequest() {
    }

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param name - имя
     * @param email - почта
     * @param password - пароль
     */
    public UserRequest(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * Функция получения значения поля {@link UserRequest#name}
     * @return возвращает имя пользователя
     */
    public String getName() {
        return name;
    }

    /**
     * Процедура определения значения поля {@link UserRequest#name}
     * @param name - имя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Функция получения значения поля {@link UserRequest#email}
     * @return возвращает почту пользователя
     */
    public String getEmail() {
        return email;
    }

    /**
     * Процедура определения значения поля {@link UserRequest#email}
     * @param email - почта
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Функция получения значения поля {@link UserRequest#password}
     * @return возвращает пароль пользователя
     */
    public String getPassword() {
        return password;
    }

    /**
     * Процедура определения значения поля {@link UserRequest#password}
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
        if (!(o instanceof UserRequest that)) return false;
        return Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    /**
     * Переопределенный метод {@link Object#hashCode}
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, email, password);
    }

    /**
     * Переопределенный метод {@link Object#toString}
     */
    @Override
    public String toString() {
        return "PersonRequest{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
