package com.lushnikova.homework_1.dto.resp;


import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Класс UserResponse является объект response для класса {@see User}
 */
public class UserResponse {
    /** Поле уникальный идентификатор */
    private UUID id;

    /** Поле имя */
    private String name;

    /** Поле почта */
    private String email;

    /** Поле пароль */
    private String password;

    /**  Поле блокировка пользователя */
    private boolean isActive;

    /** Список привычек пользователя*/
    private List<HabitResponse> habits;

    /**
     * Пустой конструктор для создания нового объекта
     */
    public UserResponse() {
    }

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param id - идентификатор
     * @param name - имя
     * @param email - почта
     * @param password - пароль
     * @param isActive - блокировка пользователя
     * @param habits - cписок привычек пользователя
     */
    public UserResponse(UUID id, String name, String email, String password, boolean isActive, List<HabitResponse> habits) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.habits = habits;
    }

    /**
     * Функция получения значения поля {@link UserResponse#id}
     * @return возвращает идентификатор пользователя
     */
    public UUID getId() {
        return id;
    }

    /**
     * Процедура определения значения поля {@link UserResponse#id}
     * @param id - идентификатор
     */
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * Функция получения значения поля {@link UserResponse#name}
     * @return возвращает имя пользователя
     */
    public String getName() {
        return name;
    }

    /**
     * Процедура определения значения поля {@link UserResponse#name}
     * @param name - имя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Функция получения значения поля {@link UserResponse#email}
     * @return возвращает почту пользователя
     */
    public String getEmail() {
        return email;
    }

    /**
     * Процедура определения значения поля {@link UserResponse#email}
     * @param email - почта
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Функция получения значения поля {@link UserResponse#password}
     * @return возвращает пароль пользователя
     */
    public String getPassword() {
        return password;
    }

    /**
     * Процедура определения значения поля {@link UserResponse#password}
     * @param password - пароль
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Функция получения значения поля {@link UserResponse#isActive}
     * @return возвращает заблокирован или активен пользователь
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Процедура определения значения поля {@link UserResponse#isActive}
     * @param active - блокировка
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Функция получения списка привычек {@link UserResponse#habits}
     * @return возвращает список привычек пользователя
     */
    public List<HabitResponse> getHabits() {
        return habits;
    }

    /**
     * Процедура определения значения поля {@link UserResponse#habits}
     * @param habits - Список привычек пользователя
     */
    public void setHabits(List<HabitResponse> habits) {
        this.habits = habits;
    }

    /**
     * Переопределенный метод {@link Object#hashCode}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserResponse that)) return false;
        return isActive == that.isActive && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(habits, that.habits);
    }

    /**
     * Переопределенный метод {@link Object#hashCode}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, isActive, habits);
    }

    /**
     * Переопределенный метод {@link Object#toString}
     */
    @Override
    public String toString() {
        return "PersonResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", habits=" + habits +
                '}';
    }
}
