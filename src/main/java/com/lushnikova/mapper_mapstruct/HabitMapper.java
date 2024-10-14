package com.lushnikova.mapper_mapstruct;

import com.lushnikova.dto.req.HabitRequest;
import com.lushnikova.dto.resp.HabitResponse;
import com.lushnikova.model.Habit;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface HabitMapper {

    HabitMapper INSTANCE = Mappers.getMapper(HabitMapper.class);

    Habit mapToEntity(HabitRequest habitRequest);

    HabitResponse mapToResponse(Habit habit);
}
