package com.lushnikova.model;

import com.lushnikova.model.enums.Repeat;
import com.lushnikova.model.enums.Status;

import java.time.LocalDateTime;
import java.util.Objects;

public class Habit {
    private Long id;
    private String title;
    private String description;
    private Repeat repeat;
    private Status status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Habit() {
    }

    public Habit(Long id, String title, String description, Repeat repeat, Status status, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.repeat = repeat;
        this.status = status;
        this.createdAt = createdAt;
    }

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Habit habit)) return false;
        return Objects.equals(id, habit.id) && Objects.equals(title, habit.title) && Objects.equals(description, habit.description) && repeat == habit.repeat && status == habit.status && Objects.equals(createdAt, habit.createdAt) && Objects.equals(updatedAt, habit.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, repeat, status, createdAt, updatedAt);
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
                ", updatedAt=" + updatedAt +
                '}';
    }
}
