package com.lushnikova.repository;

import com.lushnikova.model.Habit;
import com.lushnikova.model.enums.Status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HabitRepository {
    private final List<Habit> habits;

    public HabitRepository() {
        this.habits = new ArrayList<>();
    }

    public Habit save(Habit habit) {
        habit.setId((long) (habits.size() + 1));
        habit.setStatus(Status.TO_DO);
        habit.setCreatedAt(LocalDateTime.now());
        habits.add(habit);
        return habit;
    }

    public Habit findById(Long id) {
        return habits.stream().filter(habit -> habit.getId().equals(id)).findFirst().orElse(null);
    }

    public Habit update(Long id, Habit habit) {
        for (int i = 0; i < habits.size(); i++) {
            if(id.equals(habits.get(i).getId())) {
                habit.setId(id);
                habit.setUpdatedAt(LocalDateTime.now());
                habits.set(i, habit);
            }
        }
        return habit;
    }

    public void delete(Long id) {
        habits.remove(findById(id));
        for (int i = 0; i < habits.size(); i++) {
            habits.get(i).setId((long) (i + 1));
        }
    }
}
