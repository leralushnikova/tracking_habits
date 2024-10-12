package com.lushnikova.service.impl;

import com.lushnikova.dto.req.HabitRequest;
import com.lushnikova.dto.resp.HabitResponse;
import com.lushnikova.mapper.HabitMapper;
import com.lushnikova.model.Habit;
import com.lushnikova.model.enums.Status;
import com.lushnikova.repository.HabitRepository;
import com.lushnikova.repository.PersonRepository;
import com.lushnikova.service.HabitService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HabitServiceImpl implements HabitService {
    private final HabitRepository habitRepository;
    private final HabitMapper habitMapper;
    private final PersonRepository personRepository;

    public HabitServiceImpl(HabitRepository habitRepository, HabitMapper habitMapper, PersonRepository personRepository) {
        this.habitRepository = habitRepository;
        this.habitMapper = habitMapper;
        this.personRepository = personRepository;
    }

    @Override
    public HabitResponse save(HabitRequest habitRequest) {
        return habitMapper.mapToResponse(habitRepository.save(habitMapper.mapToEntity(habitRequest)));
    }

    @Override
    public void findById(Long id) {
        habitMapper.mapToResponse(habitRepository.findById(id));
    }

    @Override
    public void update(Long id, Habit habit) {
        habitMapper.mapToResponse(habitRepository.update(id, habit));
    }

    @Override
    public void delete(Long id){
        habitRepository.delete(id);
    }

    @Override
    public List<HabitResponse> getListHabitByStatus(Long idPerson, Status status) {
        List<Habit> listHabit = personRepository.findById(idPerson).getHabits();
        List<Habit> listStatus = new ArrayList<>();
        for (Habit habit : listHabit) {
            if (habit.getStatus().equals(status)) listStatus.add(habit);
        }
        return listStatus.stream().map(habitMapper::mapToResponse).toList();
    }

    @Override
    public List<HabitResponse> getListHabitByDateCreate(Long idPerson, LocalDate time) {
        List<Habit> listHabit = personRepository.findById(idPerson).getHabits();
        List<Habit> listStatus = new ArrayList<>();
        for (Habit habit : listHabit) {
            String createDate = habit.getCreatedAt().toLocalDate().toString();
            if(createDate.equals(time.toString())) listStatus.add(habit);
        }
        return listStatus.stream().map(habitMapper::mapToResponse).toList();
    }
}
