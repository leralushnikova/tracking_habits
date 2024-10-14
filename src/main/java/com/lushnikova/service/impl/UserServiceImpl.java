package com.lushnikova.service.impl;

import com.lushnikova.dto.req.HabitRequest;
import com.lushnikova.dto.req.UserRequest;
import com.lushnikova.dto.resp.HabitResponse;
import com.lushnikova.dto.resp.UserResponse;
import com.lushnikova.mapper_mapstruct.HabitMapper;
import com.lushnikova.mapper_mapstruct.UserMapper;
import com.lushnikova.model.Habit;
import com.lushnikova.model.User;
import com.lushnikova.model.enums.Repeat;
import com.lushnikova.model.enums.Statistics;
import com.lushnikova.model.enums.Status;
import com.lushnikova.service.UserService;
import com.lushnikova.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public class UserServiceImpl implements UserService {
    private final HabitMapper habitMapper;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(UserMapper userMapper, UserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.habitMapper = HabitMapper.INSTANCE;
    }

    @Override
    public UserResponse save(UserRequest userRequest) {
        User user = userMapper.mapToEntity(userRequest);
        return userMapper.mapToResponse(userRepository.save(user));
    }

    @Override
    public UserResponse findById(UUID id) {
        return userMapper.mapToResponse(userRepository.findById(id));
    }

    @Override
    public void updateName(UUID id, String name) {
        userRepository.updateName(id, name);
    }

    @Override
    public void updateEmail(UUID id, String email) {
        userRepository.updateEmail(id, email);
    }

    @Override
    public void updatePassword(UUID id, String password) {
        userRepository.updatePassword(id, password);
    }

    @Override
    public void delete(UUID id){
        userRepository.delete(id);
    }

    @Override
    public void addHabitByIdPerson(UUID idPerson, HabitRequest habitRequest) {
        Habit habit = habitMapper.mapToEntity(habitRequest);
        long idHabit = getHabitsByIdPerson(idPerson).size() + 1;
        habit.setId(idHabit);
        userRepository.addHabitByIdPerson(idPerson, habit);
    }

    @Override
    public List<HabitResponse> getHabitsByIdPerson(UUID idPerson) {
        return userRepository.getHabitsByIdPerson(idPerson).stream().map(habitMapper::mapToResponse).toList();
    }

    @Override
    public List<HabitResponse> getHabitsByStatusByIdPerson(UUID idPerson, Status status) {
        return userRepository.getHabitsByStatusByIdPerson(idPerson, status).stream().map(habitMapper::mapToResponse).toList();
    }

    @Override
    public List<HabitResponse> getHabitsByLocalDateByIdPerson(UUID idPerson, LocalDate localDate) {
        return userRepository.getHabitsByLocalDateByIdPerson(idPerson, localDate).stream().map(habitMapper::mapToResponse).toList();
    }

    @Override
    public void updateTitleByIdHabitByIdPerson(UUID idPerson, Long idHabit, String newTitle) {
        userRepository.updateTitleByIdHabitByIdPerson(idPerson, idHabit, newTitle);
    }

    @Override
    public void updateDescriptionByIdHabitByIdPerson(UUID idPerson, Long idHabit, String newDescription) {
        userRepository.updateDescriptionByIdHabitByIdPerson(idPerson, idHabit, newDescription);
    }

    @Override
    public void updateRepeatByIdHabitByIdPerson(UUID idPerson, Long idHabit, Repeat newRepeat) {
        userRepository.updateRepeatByIdHabitByIdPerson(idPerson, idHabit, newRepeat);
    }

    @Override
    public void updateStatusByIdHabitByIdPerson(UUID idPerson, Long idHabit, Status newStatus) {
        userRepository.updateStatusByIdHabitByIdPerson(idPerson, idHabit, newStatus);
    }

    @Override
    public void deleteHabitByIdPerson(UUID idPerson, Long idHabit) {
        userRepository.deleteHabitByIdPerson(idPerson, idHabit);
    }

    @Override
    public List<String> getHabitFulfillmentStatisticsByIdPerson(UUID idPerson, Statistics statistics, Long idHabit, LocalDate dateFrom) {
        return userRepository.getHabitFulfillmentStatisticsByIdPerson(idPerson, statistics, idHabit, dateFrom);
    }

    @Override
    public int percentSuccessHabitsByIdPerson(UUID idPerson, LocalDate dateFrom, LocalDate dateTo) {
        return userRepository.percentSuccessHabitsByIdPerson(idPerson, dateFrom, dateTo);
    }

    @Override
    public void reportHabitByIdPerson(UUID idPerson, Long idHabit) {
        userRepository.reportHabitByIdPerson(idPerson, idHabit);
    }

    @Override
    public void setDoneDatesHabitByIdPerson(UUID idPerson, Long idHabit) {
        userRepository.setDoneDatesHabitByIdPerson(idPerson, idHabit);
    }

    public void setIsActiveByIdPerson(UUID idPerson, boolean isActive) {
        userRepository.setIsActiveByIdPerson(idPerson, isActive);
    }

    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(userMapper::mapToResponse).toList();
    }
}
