package com.lushnikova.homework_1.mapper_mapstruct;

import com.lushnikova.homework_1.dto.req.HabitRequest;
import com.lushnikova.homework_1.dto.resp.HabitResponse;
import com.lushnikova.homework_1.model.Habit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HabitMapper {

    HabitMapper INSTANCE = Mappers.getMapper(HabitMapper.class);

    Habit mapToEntity(HabitRequest habitRequest);

    HabitResponse mapToResponse(Habit habit);
}
