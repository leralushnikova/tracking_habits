package com.lushnikova.mapper;

import com.lushnikova.dto.req.HabitRequest;
import com.lushnikova.dto.resp.HabitResponse;
import com.lushnikova.model.Habit;

public class HabitMapper {

    public Habit mapToEntity(HabitRequest habitRequest) {
        if (habitRequest == null) return null;

        Habit habit = new Habit();
        habit.setTitle(habitRequest.getTitle());
        habit.setDescription(habitRequest.getDescription());
        habit.setRepeat(habitRequest.getRepeat());
        return habit;
    }

    public HabitResponse mapToResponse(Habit habit) {
        if (habit == null) return null;

        HabitResponse habitResponse = new HabitResponse();
        habitResponse.setId(habit.getId());
        habitResponse.setTitle(habit.getTitle());
        habitResponse.setDescription(habit.getDescription());
        habitResponse.setRepeat(habit.getRepeat());
        habitResponse.setStatus(habit.getStatus());
        habitResponse.setCreatedAt(habit.getCreatedAt());
        habitResponse.setUpdatedAt(habit.getUpdatedAt());
        return habitResponse;
    }
}
