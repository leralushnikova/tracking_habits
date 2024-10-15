package com.lushnikova.homework_1.dto.resp;


import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class UserResponse {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private boolean isActive;
    private List<HabitResponse> habits;

    public UserResponse() {
    }

    public UserResponse(UUID id, String name, String email, String password, boolean isActive, List<HabitResponse> habits) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
        this.habits = habits;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<HabitResponse> getHabits() {
        return habits;
    }

    public void setHabits(List<HabitResponse> habits) {
        this.habits = habits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserResponse that)) return false;
        return isActive == that.isActive && Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(password, that.password) && Objects.equals(habits, that.habits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, isActive, habits);
    }

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
