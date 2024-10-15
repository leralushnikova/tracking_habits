package com.lushnikova.homework_1.dto.req;

import com.lushnikova.homework_1.model.enums.Repeat;

import java.util.Objects;


public class HabitRequest {
    private String title;
    private String description;
    private Repeat repeat;

    public HabitRequest() {
    }

    public HabitRequest(String title, String description, Repeat repeat) {
        this.title = title;
        this.description = description;
        this.repeat = repeat;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HabitRequest that)) return false;
        return Objects.equals(title, that.title) && Objects.equals(description, that.description) && repeat == that.repeat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, repeat);
    }

    @Override
    public String toString() {
        return "HabitRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", repeat=" + repeat +
                '}';
    }
}
