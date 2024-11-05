package com.lushnikova.mapper;

import com.lushnikova.dto.request.UserRequest;
import com.lushnikova.dto.response.UserResponse;
import com.lushnikova.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Операция преобразования классов {@see UserRequest}, {@see User} и {@see UserResponse}
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    /**
     * Операция преобразования класса UserRequest в класс User
     * @param userRequest - объект класса UserRequest
     * @return возвращает объект класса User
     */
    User mapToEntity(UserRequest userRequest);

    /**
     * Операция преобразования класса User в класс UserResponse
     * @param user - объект класса User
     * @return возвращает объект класса UserResponse
     */
    UserResponse mapToResponse(User user);

}

