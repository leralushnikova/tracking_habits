package com.lushnikova.service.impl;

import com.lushnikova.dto.request.HabitRequest;
import com.lushnikova.dto.response.HabitResponse;
import com.lushnikova.mapper.HabitMapper;
import com.lushnikova.model.enums.Repeat;
import com.lushnikova.model.enums.Statistics;
import com.lushnikova.model.enums.Status;
import com.lushnikova.model.Habit;
import com.lushnikova.repository.HabitRepository;
import com.lushnikova.service.HabitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

@Service
public class HabitServiceImpl implements HabitService {

    private final HabitMapper habitMapper;

    private final HabitRepository habitRepository;


    @Autowired
    public HabitServiceImpl(HabitRepository habitRepository, HabitMapper habitMapper) {
        this.habitRepository = habitRepository;
        this.habitMapper = habitMapper;
    }

    @Override
    public void save(HabitRequest habitRequest, Long idUser) throws SQLException {
        Habit habit = habitMapper.mapToEntity(habitRequest);
        habitRepository.save(habit, idUser);
    }

    @Override
    public HabitResponse findById(Long id) throws SQLException {
        Habit habit = habitRepository.findById(id);
        return habitMapper.mapToResponse(habit);
    }
    
    @Override
    public List<HabitResponse> findAll(Long idUser) throws SQLException {
        return habitRepository.findAll(idUser).stream().map(habitMapper::mapToResponse).toList();
    }

    @Override
    public List<HabitResponse> getHabitsByStatus(Long idUser, Status status) throws SQLException {
        return habitRepository.getHabitsByStatus(idUser, status).stream().map(habitMapper::mapToResponse).toList();
    }

    @Override
    public List<HabitResponse> getHabitsByDate(Long idUser, Date date) throws SQLException {
        return habitRepository.getHabitsByDate(idUser, date).stream().map(habitMapper::mapToResponse).toList();
    }

    @Override
    public void updateTitleByIdHabit(Long id, String newTitle) throws SQLException {
        habitRepository.updateTitleByIdHabit(id, newTitle);
    }

    @Override
    public void updateDescriptionByIdHabit(Long id, String newDescription) throws SQLException {
        habitRepository.updateDescriptionByIdHabit(id, newDescription);
    }
    
    @Override
    public void updateRepeatByIdHabit(Long id, Repeat newRepeat) throws SQLException {
        habitRepository.updateRepeatByIdHabit(id, newRepeat);
    }
    
    @Override
    public void updateStatusByIdHabit(Long id, Status newStatus) throws SQLException {
        habitRepository.updateStatusByIdHabit(id, newStatus);
    }
    
    @Override
    public void delete(Long id) throws SQLException {
        habitRepository.delete(id);
    }
    
    @Override
    public List<String> getHabitFulfillmentStatistics(Long id, LocalDate dateFrom, Statistics statistics) throws SQLException {
        return habitRepository.getHabitFulfillmentStatistics(id, dateFrom, statistics);
    }

    @Override
    public void setDoneDates(Long id) throws SQLException {
        habitRepository.setDoneDates(id);
    }
    
    @Override
    public int percentSuccessHabits(Long idUser, LocalDate dateFrom, LocalDate dateTo) throws SQLException {
        return habitRepository.percentSuccessHabits(idUser, dateFrom, dateTo);
    }


    @Override
    public void reportHabit(Long id) throws SQLException {
        habitRepository.reportHabit(id);
    }

    @Override
    public void switchOnOrOffPushNotification(Long id, Time pushTime) throws SQLException {
        habitRepository.switchOnOrOffPushNotification(id, pushTime);
    }
}