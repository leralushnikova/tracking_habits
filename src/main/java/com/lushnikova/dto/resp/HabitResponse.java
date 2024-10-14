package com.lushnikova.dto.resp;

import com.lushnikova.model.enums.Repeat;
import com.lushnikova.model.enums.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class HabitResponse {
    private Long id;
    private String title;
    private String description;
    private Repeat repeat;
    private Status status;
    private LocalDate createdAt;
    private int streak;
    private LocalTime pushTime;
    private final Set<LocalDate> doneDates = new TreeSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public LocalTime getPushTime() {
        return pushTime;
    }

    public void setPushTime(LocalTime pushTime) {
        this.pushTime = pushTime;
    }

    public Set<LocalDate> getDoneDates() {
        return doneDates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HabitResponse response)) return false;
        return streak == response.streak && Objects.equals(id, response.id) && Objects.equals(title, response.title) && Objects.equals(description, response.description) && repeat == response.repeat && status == response.status && Objects.equals(createdAt, response.createdAt) && Objects.equals(pushTime, response.pushTime) && Objects.equals(doneDates, response.doneDates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, repeat, status, createdAt, streak, pushTime, doneDates);
    }

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
