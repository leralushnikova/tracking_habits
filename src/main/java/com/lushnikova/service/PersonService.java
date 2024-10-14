package com.lushnikova.service;

import com.lushnikova.dto.req.HabitRequest;
import com.lushnikova.dto.req.PersonRequest;
import com.lushnikova.dto.resp.HabitResponse;
import com.lushnikova.dto.resp.PersonResponse;
import com.lushnikova.exception.ModelNotFound;
import com.lushnikova.model.enums.Repeat;
import com.lushnikova.model.enums.Statistics;
import com.lushnikova.model.enums.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PersonService {
    PersonResponse save(PersonRequest personRequest);
    PersonResponse findById(UUID id);
    void updateName(UUID id, String name);
    void updateEmail(UUID id, String email);
    void updatePassword(UUID id, String password);
    void delete(UUID id);
    void addHabitByIdPerson(UUID idPerson, HabitRequest habitRequest) throws ModelNotFound;
    List<HabitResponse> getHabitsByIdPerson(UUID idPerson) throws ModelNotFound;
    List<HabitResponse> getHabitsByStatusByIdPerson(UUID idPerson, Status status) throws ModelNotFound;
    List<HabitResponse> getHabitsByLocalDateByIdPerson(UUID idPerson, LocalDate localDate) throws ModelNotFound;
    void updateTitleByIdHabitByIdPerson(UUID idPerson, Long idHabit, String newTitle);
    void updateDescriptionByIdHabitByIdPerson(UUID idPerson, Long idHabit, String newDescription);
    void updateRepeatByIdHabitByIdPerson(UUID idPerson, Long idHabit, Repeat newRepeat);
    void updateStatusByIdHabitByIdPerson(UUID idPerson, Long idHabit, Status newStatus);
    void deleteHabitByIdPerson(UUID idPerson, Long idHabit);
    void setDoneDatesHabitByIdPerson(UUID idPerson, Long idHabit);
    List<String> getHabitFulfillmentStatisticsByIdPerson(UUID idPerson, Statistics statistics, Long idHabit, LocalDate dateFrom);
    int percentSuccessHabitsByIdPerson(UUID idPerson, LocalDate dateFrom, LocalDate dateTo);
    void reportHabitByIdPerson(UUID idPerson, Long idHabit);
    List<PersonResponse> findAll();
    void setIsActiveByIdPerson(UUID idPerson, boolean isActive) throws ModelNotFound;
}
