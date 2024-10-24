package com.lushnikova.homework_1.dto.req;

import com.lushnikova.homework_1.model.enum_for_model.Repeat;

import java.util.Objects;

/**
 * Класс HabitRequest является объект request для класса {@see Habit}
 */
public class HabitRequest {
    /** Поле название */
    private String title;

    /** Поле описание */
    private String description;

    /** Поле частота повторения */
    private Repeat repeat;

    /**
     * Пустой конструктор для создания нового объекта
     */
    public HabitRequest() {
    }

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param title - почта
     * @param description - пароль
     * @param repeat - частота повторения
     */
    public HabitRequest(String title, String description, Repeat repeat) {
        this.title = title;
        this.description = description;
        this.repeat = repeat;
    }

    /**
     * Функция получения значения поля {@link HabitRequest#title}
     * @return возвращает название привычки
     */
    public String getTitle() {
        return title;
    }

    /**
     * Процедура определения значения поля {@link HabitRequest#title}
     * @param title - название
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     Функция получения значения поля {@link HabitRequest#description}
     * @return возвращает описание привычки
     */
    public String getDescription() {
        return description;
    }

    /**
     * Процедура определения значения поля {@link HabitRequest#description}
     * @param description - описание
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Функция получения значения поля {@link HabitRequest#repeat}
     * @return возвращает частоту повторения привычки
     */
    public Repeat getRepeat() {
        return repeat;
    }

    /**
     * Процедура определения значения поля {@link HabitRequest#repeat}
     * @param repeat - частота повторения
     */
    public void setRepeat(Repeat repeat) {
        this.repeat = repeat;
    }

    /**
     * Переопределенный метод {@link Object#equals(Object)}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HabitRequest that)) return false;
        return Objects.equals(title, that.title) && Objects.equals(description, that.description) && repeat == that.repeat;
    }

    /**
     * Переопределенный метод {@link Object#hashCode}
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, description, repeat);
    }

    /**
     * Переопределенный метод {@link Object#toString}
     */
    @Override
    public String toString() {
        return "HabitRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", repeat=" + repeat +
                '}';
    }
}
