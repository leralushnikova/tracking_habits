package com.lushnikova.homework_2.mapper;

import com.lushnikova.homework_2.dto.request.HabitRequest;
import com.lushnikova.homework_2.dto.response.HabitResponse;
import com.lushnikova.homework_2.model.Habit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Операция преобразования классов {@see HabitRequest}, {@see Habit} и {@see HabitResponse}
 */
@Mapper
public interface HabitMapper {

    HabitMapper INSTANCE = Mappers.getMapper(HabitMapper.class);

    /**
     * Операция преобразования класса HabitRequest в класс Habit
     * @param habitRequest - объект класса HabitRequest
     * @return возвращает объект класса Habit
     */
    Habit mapToEntity(HabitRequest habitRequest);

    /**
     * Операция преобразования класса Habit в класс HabitResponse
     * @param habit - объект класса Habit
     * @return возвращает объект класса HabitResponse
     */
    HabitResponse mapToResponse(Habit habit);
}
