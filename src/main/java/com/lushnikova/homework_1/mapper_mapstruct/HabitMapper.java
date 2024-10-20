package com.lushnikova.homework_1.mapper_mapstruct;

import com.lushnikova.homework_1.dto.req.HabitRequest;
import com.lushnikova.homework_1.dto.resp.HabitResponse;
import com.lushnikova.homework_1.model.Habit;
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
