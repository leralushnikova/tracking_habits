package com.lushnikova.service;

import com.lushnikova.dto.req.HabitRequest;
import com.lushnikova.dto.resp.HabitResponse;
import com.lushnikova.exception.ModelNotFound;
import com.lushnikova.model.Habit;
import com.lushnikova.model.enums.Status;

import java.time.LocalDate;
import java.util.List;

public interface HabitService {
    HabitResponse save(HabitRequest habitRequest);
    void findById(Long id) throws ModelNotFound;
    void update(Long id, Habit habit) throws ModelNotFound;
    void delete(Long id) throws ModelNotFound;
    List<HabitResponse> getListHabitByStatus(Long idPerson, Status status);
    List<HabitResponse> getListHabitByDateCreate(Long idPerson, LocalDate time);
}
