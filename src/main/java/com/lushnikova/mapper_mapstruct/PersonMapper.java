package com.lushnikova.mapper_mapstruct;

import com.lushnikova.dto.req.PersonRequest;
import com.lushnikova.dto.resp.PersonResponse;
import com.lushnikova.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {

    PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    User mapToEntity(PersonRequest personRequest);

    PersonResponse mapToResponse(User user);

}
