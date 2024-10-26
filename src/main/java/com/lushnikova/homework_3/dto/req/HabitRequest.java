package com.lushnikova.homework_3.dto.req;


import java.time.LocalDate;
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
    private String repeat;

    /** Поле статус */
    private String status;

    /** Поле дата создания */
    private LocalDate createdAt;

    /** Поле отметка даты выполнения привычки */
    private String doneDate;

    /** Поле временя отправки уведомления */
    private String pushTime;

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
    public HabitRequest(String title, String description, String repeat) {
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
    public String getRepeat() {
        return repeat;
    }

    /**
     * Процедура определения значения поля {@link HabitRequest#repeat}
     * @param repeat - частота повторения
     */
    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }

    /**
     * Функция получения значения поля {@link HabitRequest#status}
     * @return возвращает статус привычки
     */
    public String getStatus() {
        return status;
    }

    /**
     * Процедура определения значения поля {@link HabitRequest#status}
     * @param status - статус
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Процедура определения значения поля {@link HabitRequest#createdAt}
     * @param createdAt - дата создания
     */
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Функция получения значения поля {@link HabitRequest#createdAt}
     * @return возвращает дату создания привычки
     */
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    /**
     * Функция получения отметки даты выполнения привычки {@link HabitRequest#doneDate}
     * @return возвращает ответ
     */
    public String getDoneDate() {
        return doneDate;
    }

    /**
     * Процедура определения значения поля {@link HabitRequest#doneDate}
     * @param doneDate - ответ
     */
    public void setDoneDate(String doneDate) {
        this.doneDate = doneDate;
    }

    /**
     * Функция получения значения поля {@link HabitRequest#pushTime}
     * @return возвращает время уведомления привычки
     */
    public String getPushTime() {
        return pushTime;
    }

    /**
     * Процедура определения значения поля {@link HabitRequest#pushTime}
     * @param pushTime - время уведомления
     */
    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
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
