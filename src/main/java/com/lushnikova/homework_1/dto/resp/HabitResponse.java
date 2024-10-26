package com.lushnikova.homework_1.dto.resp;

import com.lushnikova.homework_1.model.enum_for_model.Repeat;
import com.lushnikova.homework_1.model.enum_for_model.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * Класс HabitResponse является объект response для класса {@see Habit}
 */
public class HabitResponse {
    /** Поле идентификатора */
    private Long id;

    /** Поле название */
    private String title;

    /** Поле описание */
    private String description;

    /** Поле частота повторения */
    private Repeat repeat;

    /** Поле статус */
    private Status status;

    /** Поле дата создания */
    private LocalDate createdAt;

    /** Поле текущая серии выполнений*/
    private int streak;

    /** Поле временя отправки уведомления */
    private LocalTime pushTime;

    /** Поле история выполнения каждой привычки */
    private final Set<LocalDate> doneDates = new TreeSet<>();

    /**
     * Пустой конструктор для создания нового объекта
     */
    public HabitResponse() {
    }

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param id - идентификатора
     * @param title - название
     * @param description - описание
     * @param repeat - частота повторения
     * @param status - статус
     * @param createdAt - дата создания
     * @param streak - текущая серии выполнений
     * @param pushTime - временя отправки уведомления
     */
    public HabitResponse(Long id, String title, String description, Repeat repeat, Status status, LocalDate createdAt, int streak, LocalTime pushTime) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.repeat = repeat;
        this.status = status;
        this.createdAt = createdAt;
        this.streak = streak;
        this.pushTime = pushTime;
    }

    /**
     * Функция получения значения поля {@link HabitResponse#id}
     * @return возвращает идентификатор привычки
     */
    public Long getId() {
        return id;
    }

    /**
     * Процедура определения значения поля {@link HabitResponse#id}
     * @param id - идентификатор
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Функция получения значения поля {@link HabitResponse#title}
     * @return возвращает название привычки
     */
    public String getTitle() {
        return title;
    }

    /**
     * Процедура определения значения поля {@link HabitResponse#title}
     * @param title - название
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     Функция получения значения поля {@link HabitResponse#description}
     * @return возвращает описание привычки
     */
    public String getDescription() {
        return description;
    }

    /**
     * Процедура определения значения поля {@link HabitResponse#description}
     * @param description - описание
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Функция получения значения поля {@link HabitResponse#repeat}
     * @return возвращает частоту повторения привычки
     */
    public Repeat getRepeat() {
        return repeat;
    }

    /**
     * Процедура определения значения поля {@link HabitResponse#repeat}
     * @param repeat - частота повторения привычки
     */
    public void setRepeat(Repeat repeat) {
        this.repeat = repeat;
    }

    /**
     * Функция получения значения поля {@link HabitResponse#status}
     * @return возвращает статус привычки
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Процедура определения значения поля {@link HabitResponse#status}
     * @param status - статус
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Функция получения значения поля {@link HabitResponse#createdAt}
     * @return возвращает дату создания привычки
     */
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    /**
     * Процедура определения значения поля {@link HabitResponse#createdAt}
     * @param createdAt - дата создания
     */
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Функция получения значения поля {@link HabitResponse#streak}
     * @return возвращает текущую серию выполнения привычки
     */
    public int getStreak() {
        return streak;
    }

    /**
     * Процедура определения значения поля {@link HabitResponse#streak}
     * @param streak - текущая серии выполнений
     */
    public void setStreak(int streak) {
        this.streak = streak;
    }

    /**
     * Функция получения значения поля {@link HabitResponse#pushTime}
     * @return возвращает время уведомления привычки
     */
    public LocalTime getPushTime() {
        return pushTime;
    }

    /**
     * Процедура определения значения поля {@link HabitResponse#pushTime}
     * @param pushTime - время уведомления
     */
    public void setPushTime(LocalTime pushTime) {
        this.pushTime = pushTime;
    }

    /**
     * Функция получения значения поля {@link HabitResponse#doneDates}
     * @return возвращает даты выполнения привычки
     */
    public Set<LocalDate> getDoneDates() {
        return doneDates;
    }

    /**
     * Переопределенный метод {@link Object#equals(Object)}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HabitResponse response)) return false;
        return streak == response.streak && Objects.equals(id, response.id) && Objects.equals(title, response.title) && Objects.equals(description, response.description) && repeat == response.repeat && status == response.status && Objects.equals(createdAt, response.createdAt) && Objects.equals(pushTime, response.pushTime) && Objects.equals(doneDates, response.doneDates);
    }

    /**
     * Переопределенный метод {@link Object#hashCode}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, repeat, status, createdAt, streak, pushTime, doneDates);
    }

    /**
     * Переопределенный метод {@link Object#toString}
     */
    @Override
    public String toString() {
        return "HabitResponse{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", repeat=" + repeat +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", streak=" + streak +
                ", pushTime=" + pushTime +
                ", doneDates=" + doneDates +
                '}';
    }
}
