package com.lushnikova.mapper;

import com.lushnikova.dto.request.HabitRequest;
import com.lushnikova.dto.response.HabitResponse;
import com.lushnikova.model.Habit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
 * Операция преобразования классов {@see HabitRequest}, {@see Habit} и {@see HabitResponse}
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HabitMapper {

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
    @Mappings({
            @Mapping(target = "createdAt", dateFormat = "yyyy-MM-dd"),
            @Mapping(target = "pushTime", dateFormat = "HH:mm"),
            @Mapping(target = "doneDates", dateFormat = "yyyy-MM-dd")
    }
    )
    HabitResponse mapToResponse(Habit habit);
}
