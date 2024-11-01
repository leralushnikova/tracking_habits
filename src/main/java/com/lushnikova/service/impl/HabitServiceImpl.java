package com.lushnikova.service.impl;

import com.lushnikova.annotations.Loggable;
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
import java.util.Optional;

/**
 * Класс по управлению привычками
 */
@Loggable
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
    public void save(HabitRequest habitRequest, Long idUser){
        try {
            Habit habit = habitMapper.mapToEntity(habitRequest);
            habitRepository.save(habit, idUser);
        } catch (SQLException e) {
            System.err.println("Error with creating habit for user: " + idUser);
        }
    }

    @Override
    public Optional<HabitResponse> findById(Long idHabit, Long idUser){
        try {
            Habit habit = habitRepository.findById(idHabit, idUser);
            return Optional.of(habitMapper.mapToResponse(habit));
        } catch (SQLException e) {
            System.err.println("Error with finding habit for id: " + idHabit + " for user: " + idUser);
            return Optional.empty();
        }
    }
    
    @Override
    public List<HabitResponse> findAll(Long idUser){
        try {
            return habitRepository.findAll(idUser).stream().map(habitMapper::mapToResponse).toList();
        } catch (SQLException e) {
            System.err.println("Error with getting list habits for user: " + idUser);
            return null;
        }
    }

    @Override
    public List<HabitResponse> getHabitsByStatus(Long idUser, Status status){
        try {
            return habitRepository.getHabitsByStatus(idUser, status).stream().map(habitMapper::mapToResponse).toList();
        } catch (SQLException e) {
            System.err.println("Error with getting list habits with status " + status.name() + " for user: " + idUser);
            return null;
        }
    }

    @Override
    public List<HabitResponse> getHabitsByDate(Long idUser, Date date){
        try {
            return habitRepository.getHabitsByDate(idUser, date).stream().map(habitMapper::mapToResponse).toList();
        } catch (SQLException e) {
            System.err.println("Error with getting list habits with create date " + date + " for user: " + idUser);
            return null;
        }
    }

    @Override
    public void updateTitleByIdHabit(Long id, String newTitle){
        try {
            habitRepository.updateTitleByIdHabit(id, newTitle);
        } catch (SQLException e) {
            System.err.println("Error with updating title habit: " + id);
        }
    }

    @Override
    public void updateDescriptionByIdHabit(Long id, String newDescription){
        try {
            habitRepository.updateDescriptionByIdHabit(id, newDescription);
        } catch (SQLException e) {
            System.err.println("Error with description title habit: " + id);
        }
    }
    
    @Override
    public void updateRepeatByIdHabit(Long id, Repeat newRepeat){
        try {
            habitRepository.updateRepeatByIdHabit(id, newRepeat);
        } catch (SQLException e) {
            System.err.println("Error with repeat title habit: " + id);
        }
    }
    
    @Override
    public void updateStatusByIdHabit(Long id, Status newStatus){
        try {
            habitRepository.updateStatusByIdHabit(id, newStatus);
        } catch (SQLException e) {
            System.err.println("Error with status title habit: " + id);
        }
    }
    
    @Override
    public void delete(Long id){
        try {
            habitRepository.delete(id);
        } catch (SQLException e) {
            System.err.println("Error with deleting habit: " + id);
        }
    }
    
    @Override
    public List<String> getHabitFulfillmentStatistics(Long id, LocalDate dateFrom, Statistics statistics) {
        try {
            return habitRepository.getHabitFulfillmentStatistics(id, dateFrom, statistics);
        } catch (SQLException e) {
            System.err.println("Error with getting habit fulfillment statistics: " + id);
            return null;
        }
    }

    @Override
    public void setDoneDates(Long idHabit, Long idUser){
        try {
            habitRepository.setDoneDates(idHabit, idUser);
        } catch (SQLException e) {
            System.err.println("Error with updating title habit: " + idHabit + " for user: " + idUser);
        }
    }
    
    @Override
    public Optional<Integer> percentSuccessHabits(Long idUser, LocalDate dateFrom, LocalDate dateTo){
        try {
            return Optional.of(habitRepository.percentSuccessHabits(idUser, dateFrom, dateTo));
        } catch (SQLException e) {
            System.err.println("Error with getting habit percents success habits: " + idUser);
            return Optional.empty();
        }
    }


    @Override
    public Optional<String> reportHabit(Long idHabit, Long idUser){
        try {
            return Optional.of(habitRepository.reportHabit(idHabit, idUser));
        } catch (SQLException e) {
            System.err.println("Error with reporting habit: " + idHabit + " for user: " + idUser);
            return Optional.empty();
        }
    }

    @Override
    public void switchOnOrOffPushNotification(Long id, Time pushTime){
        try {
            habitRepository.switchOnOrOffPushNotification(id, pushTime);
        } catch (SQLException e) {
            System.err.println("Error with switching on/off habit: " + id);
        }
    }
}