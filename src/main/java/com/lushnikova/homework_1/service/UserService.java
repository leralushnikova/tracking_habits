package com.lushnikova.homework_1.service;

import com.lushnikova.homework_1.dto.req.HabitRequest;
import com.lushnikova.homework_1.dto.req.UserRequest;
import com.lushnikova.homework_1.dto.resp.HabitResponse;
import com.lushnikova.homework_1.dto.resp.UserResponse;
import com.lushnikova.homework_1.exception.ModelNotFound;
import com.lushnikova.homework_1.model.enums.Repeat;
import com.lushnikova.homework_1.model.enums.Statistics;
import com.lushnikova.homework_1.model.enums.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface UserService {
    UserResponse save(UserRequest userRequest);
    UserResponse findById(UUID id);
    void updateName(UUID id, String name);
    void updateEmail(UUID id, String email);
    void updatePassword(UUID id, String password);
    void delete(UUID id);
    void addHabitByIdUser(UUID idUser, HabitRequest habitRequest) throws ModelNotFound;
    List<HabitResponse> getHabitsByIdUser(UUID idUser) throws ModelNotFound;
    List<HabitResponse> getHabitsByStatusByIdUser(UUID idUser, Status status) throws ModelNotFound;
    List<HabitResponse> getHabitsByLocalDateByIdUser(UUID idUser, LocalDate localDate) throws ModelNotFound;
    void updateTitleByIdHabitByIdUser(UUID idUser, Long idHabit, String newTitle);
    void updateDescriptionByIdHabitByIdUser(UUID idUser, Long idHabit, String newDescription);
    void updateRepeatByIdHabitByIdUser(UUID idUser, Long idHabit, Repeat newRepeat);
    void updateStatusByIdHabitByIdUser(UUID idUser, Long idHabit, Status newStatus);
    void deleteHabitByIdUser(UUID idUser, Long idHabit);
    void setDoneDatesHabitByIdUser(UUID idUser, Long idHabit);
    List<String> getHabitFulfillmentStatisticsByIdUser(UUID idUser, Statistics statistics, Long idHabit, LocalDate dateFrom);
    int percentSuccessHabitsByIdUser(UUID idUser, LocalDate dateFrom, LocalDate dateTo);
    void reportHabitByIdUser(UUID idUser, Long idHabit);
    List<UserResponse> findAll();
    void setIsActiveByIdUser(UUID idUser, boolean isActive) throws ModelNotFound;
    void switchOnPushNotificationByIdUser(UUID idUser, Long idHabit, LocalTime pushTime) throws ModelNotFound;
    void switchOffPushNotificationByIdUser(UUID idUser, Long idHabit) throws ModelNotFound;
}
