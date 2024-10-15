package com.lushnikova.homework_1.service.impl;

import com.lushnikova.homework_1.dto.req.HabitRequest;
import com.lushnikova.homework_1.dto.req.UserRequest;
import com.lushnikova.homework_1.dto.resp.HabitResponse;
import com.lushnikova.homework_1.dto.resp.UserResponse;
import com.lushnikova.homework_1.mapper_mapstruct.HabitMapper;
import com.lushnikova.homework_1.mapper_mapstruct.UserMapper;
import com.lushnikova.homework_1.model.Habit;
import com.lushnikova.homework_1.model.User;
import com.lushnikova.homework_1.model.enums.Repeat;
import com.lushnikova.homework_1.model.enums.Statistics;
import com.lushnikova.homework_1.model.enums.Status;
import com.lushnikova.homework_1.service.UserService;
import com.lushnikova.homework_1.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalTime;
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
    public void addHabitByIdUser(UUID idUser, HabitRequest habitRequest) {
        Habit habit = habitMapper.mapToEntity(habitRequest);
        userRepository.addHabitByIdUser(idUser, habit);
    }

    @Override
    public List<HabitResponse> getHabitsByIdUser(UUID idUser) {
        return userRepository.getHabitsByIdUser(idUser).stream().map(habitMapper::mapToResponse).toList();
    }

    @Override
    public List<HabitResponse> getHabitsByStatusByIdUser(UUID idUser, Status status) {
        return userRepository.getHabitsByStatusByIdUser(idUser, status).stream().map(habitMapper::mapToResponse).toList();
    }

    @Override
    public List<HabitResponse> getHabitsByLocalDateByIdUser(UUID idUser, LocalDate localDate) {
        return userRepository.getHabitsByLocalDateByIdUser(idUser, localDate).stream().map(habitMapper::mapToResponse).toList();
    }

    @Override
    public void updateTitleByIdHabitByIdUser(UUID idUser, Long idHabit, String newTitle) {
        userRepository.updateTitleByIdHabitByIdUser(idUser, idHabit, newTitle);
    }

    @Override
    public void updateDescriptionByIdHabitByIdUser(UUID idUser, Long idHabit, String newDescription) {
        userRepository.updateDescriptionByIdHabitByIdUser(idUser, idHabit, newDescription);
    }

    @Override
    public void updateRepeatByIdHabitByIdUser(UUID idUser, Long idHabit, Repeat newRepeat) {
        userRepository.updateRepeatByIdHabitByIdUser(idUser, idHabit, newRepeat);
    }

    @Override
    public void updateStatusByIdHabitByIdUser(UUID idUser, Long idHabit, Status newStatus) {
        userRepository.updateStatusByIdHabitByIdUser(idUser, idHabit, newStatus);
    }

    @Override
    public void deleteHabitByIdUser(UUID idUser, Long idHabit) {
        userRepository.deleteHabitByIdUser(idUser, idHabit);
    }

    @Override
    public List<String> getHabitFulfillmentStatisticsByIdUser(UUID idUser, Statistics statistics, Long idHabit, LocalDate dateFrom) {
        return userRepository.getHabitFulfillmentStatisticsByIdUser(idUser, statistics, idHabit, dateFrom);
    }

    @Override
    public int percentSuccessHabitsByIdUser(UUID idUser, LocalDate dateFrom, LocalDate dateTo) {
        return userRepository.percentSuccessHabitsByIdUser(idUser, dateFrom, dateTo);
    }

    @Override
    public void reportHabitByIdUser(UUID idUser, Long idHabit) {
        userRepository.reportHabitByIdUser(idUser, idHabit);
    }

    @Override
    public void setDoneDatesHabitByIdUser(UUID idUser, Long idHabit) {
        userRepository.setDoneDatesHabitByIdUser(idUser, idHabit);
    }

    public void setIsActiveByIdUser(UUID idUser, boolean isActive) {
        userRepository.setIsActiveByIdUser(idUser, isActive);
    }

    @Override
    public void switchOnPushNotificationByIdUser(UUID idUser, Long idHabit, LocalTime pushTime) {
        userRepository.switchOnPushNotificationByIdUser(idUser, idHabit, pushTime);
    }

    @Override
    public void switchOffPushNotificationByIdUser(UUID idUser, Long idHabit) {
        userRepository.switchOffPushNotificationByIdUser(idUser, idHabit);
    }


    @Override
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream().map(userMapper::mapToResponse).toList();
    }
}
