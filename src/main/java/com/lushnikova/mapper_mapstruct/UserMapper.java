package com.lushnikova.mapper_mapstruct;

import com.lushnikova.dto.req.UserRequest;
import com.lushnikova.dto.resp.UserResponse;
import com.lushnikova.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User mapToEntity(UserRequest userRequest);

    UserResponse mapToResponse(User user);

}
