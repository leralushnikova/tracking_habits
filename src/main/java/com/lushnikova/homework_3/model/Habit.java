package com.lushnikova.homework_3.model;

import com.lushnikova.homework_3.model.ENUM.Repeat;
import com.lushnikova.homework_3.model.ENUM.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

/**
 * Класс Привычки
 */
public class Habit {
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

    /** Поле текущая серии выполнений(сколько дней подряд выполнялась привычки)*/
    private int streak;

    /** Поле временя отправки уведомления */
    private LocalTime pushTime;

    /** Поле история выполнения каждой привычки */
    private final Set<LocalDate> doneDates = new TreeSet<>();

    /**
     * Нестатический блок инициализации
     * каждый новая привычка создается со статусом created
     */
    {
        this.status = Status.CREATED;
    }

    /**
     * Пустой конструктор для создания нового объекта
     */
    public Habit() {}

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param title - название
     * @param description - описание
     * @param repeat - частота повторения
     */
    public Habit(String title, String description, Repeat repeat) {
        this.title = title;
        this.description = description;
        this.repeat = repeat;
    }

    /**
     * Процедура определения значения поля {@link Habit#id}
     * @param id - идентификатор
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Функция получения значения поля {@link Habit#id}
     * @return возвращает идентификатор привычки
     */
    public Long getId() {
        return id;
    }

    /**
     * Функция получения значения поля {@link Habit#title}
     * @return возвращает название привычки
     */
    public String getTitle() {
        return title;
    }

    /**
     * Процедура определения значения поля {@link Habit#title}
     * @param title - название
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     Функция получения значения поля {@link Habit#description}
     * @return возвращает описание привычки
     */
    public String getDescription() {
        return description;
    }

    /**
     * Процедура определения значения поля {@link Habit#description}
     * @param description - описание
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Функция получения значения поля {@link Habit#repeat}
     * @return возвращает частоту повторения привычки
     */
    public Repeat getRepeat() {
        return repeat;
    }

    /**
     * Процедура определения значения поля {@link Habit#repeat}
     * @param repeat - частота повторения
     */
    public void setRepeat(Repeat repeat) {
        this.repeat = repeat;
    }

    /**
     * Функция получения значения поля {@link Habit#status}
     * @return возвращает статус привычки
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Процедура определения значения поля {@link Habit#status}
     * @param status - статус
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Процедура определения значения поля {@link Habit#createdAt}
     * @param createdAt - дата создания
     */
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Функция получения значения поля {@link Habit#createdAt}
     * @return возвращает дату создания привычки
     */
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    /**
     * Функция получения значения поля {@link Habit#pushTime}
     * @return возвращает время уведомления привычки
     */
    public LocalTime getPushTime() {
        return pushTime;
    }

    /**
     * Процедура определения значения поля {@link Habit#pushTime}
     * @param pushTime - время уведомления
     */
    public void setPushTime(LocalTime pushTime) {
        this.pushTime = pushTime;
    }

    /**
     * Процедура отметки даты, когда выполнялась привычка {@link Habit#doneDates}
     * и подсчета текущей серии выполнения {@link Habit#streak}
     */
    public synchronized void setDoneDates() {
        LocalDate now = LocalDate.now();
        doneDates.add(now);

        if(doneDates.contains(now.minusDays(1))){
            streak ++;
        } else streak = 1;
    }

    /**
     * Процедура отметки даты, когда выполнялась привычка {@link Habit#doneDates}
     * @param date - дата выполнения
     * и подсчета текущей серии выполнения {@link Habit#streak}
     */
    public synchronized void setDoneDates(LocalDate date) {
        doneDates.add(date);

        if(doneDates.contains(date.minusDays(1))){
            streak ++;
        } else streak = 1;
    }

    /**
     * Функция получения значения поля {@link Habit#streak}
     * @return возвращает текущую серию выполнения привычки
     */
    public int getStreak() {
        return streak;
    }

    /**
     * Функция получения значения поля {@link Habit#doneDates}
     * @return возвращает даты выполнения привычки
     */
    public Set<LocalDate> getDoneDates() {
        return doneDates;
    }

    /**
     * Переопределенный метод {@link Object#equals(Object)}
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Habit habit)) return false;
        return streak == habit.streak && Objects.equals(id, habit.id) && Objects.equals(title, habit.title) && Objects.equals(description, habit.description) && repeat == habit.repeat && status == habit.status && Objects.equals(createdAt, habit.createdAt) && Objects.equals(pushTime, habit.pushTime) && Objects.equals(doneDates, habit.doneDates);
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
        return "Habit{" +
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
