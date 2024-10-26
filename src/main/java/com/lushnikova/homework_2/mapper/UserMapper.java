package com.lushnikova.homework_2.mapper;

import com.lushnikova.homework_2.dto.req.UserRequest;
import com.lushnikova.homework_2.dto.resp.UserResponse;
import com.lushnikova.homework_2.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Операция преобразования классов {@see UserRequest}, {@see User} и {@see UserResponse}
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

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
