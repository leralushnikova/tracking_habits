package com.lushnikova.homework_1.model;

import com.lushnikova.homework_1.model.enums.Repeat;
import com.lushnikova.homework_1.model.enums.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

//удалить set для createdAt
public class Habit {
    private Long id;
    private String title;
    private String description;
    private Repeat repeat;
    private Status status;
    private LocalDate createdAt;
    private int streak;
    private LocalTime pushTime;
    private final Set<LocalDate> doneDates = new TreeSet<>();

    public Habit() {
        this.status = Status.CREATED;
    }

    public Habit(String title, String description, Repeat repeat) {
        this.title = title;
        this.description = description;
        this.repeat = repeat;
        this.status = Status.CREATED;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Repeat getRepeat() {
        return repeat;
    }

    public void setRepeat(Repeat repeat) {
        this.repeat = repeat;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public LocalTime getPushTime() {
        return pushTime;
    }

    public void setPushTime(LocalTime pushTime) {
        this.pushTime = pushTime;
    }

    public void setDoneDates() {
        LocalDate now = LocalDate.now();
        doneDates.add(now);

        if(doneDates.contains(now.minusDays(1))){
            streak ++;
        } else streak = 1;
    }

    //нужно будет удалить
    public void setDoneDates(LocalDate date) {
        doneDates.add(date);

        if(doneDates.contains(date.minusDays(1))){
            streak ++;
        } else streak = 1;
    }

    public int getStreak() {
        return streak;
    }

    public Set<LocalDate> getDoneDates() {
        return doneDates;
    }


    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof Habit habit)) return false;
        return streak == habit.streak && Objects.equals(id, habit.id) && Objects.equals(title, habit.title) && Objects.equals(description, habit.description) && repeat == habit.repeat && status == habit.status && Objects.equals(createdAt, habit.createdAt) && Objects.equals(pushTime, habit.pushTime) && Objects.equals(doneDates, habit.doneDates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, repeat, status, createdAt, streak, pushTime, doneDates);
    }

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
