package com.lushnikova.model;

import java.util.List;
import java.util.Objects;

public class Person {
    private Long id;
    private String name;
    private String email;
    private String password;
    private List<Habit> habits;

    public Person() {
    }

    public Person(Long id, String name, String email, String password, List<Habit> habits) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.habits = habits;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<Habit> getHabits() {
        return habits;
    }

    public void setHabits(List<Habit> habits) {
        this.habits = habits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person person)) return false;
        return Objects.equals(id, person.id) && Objects.equals(name, person.name) && Objects.equals(email, person.email) && Objects.equals(password, person.password) && Objects.equals(habits, person.habits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, habits);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", habits=" + habits +
                '}';
    }
}
