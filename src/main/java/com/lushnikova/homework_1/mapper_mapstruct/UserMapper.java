package com.lushnikova.homework_1.mapper_mapstruct;

import com.lushnikova.homework_1.dto.req.UserRequest;
import com.lushnikova.homework_1.dto.resp.UserResponse;
import com.lushnikova.homework_1.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User mapToEntity(UserRequest userRequest);

    UserResponse mapToResponse(User user);

}
