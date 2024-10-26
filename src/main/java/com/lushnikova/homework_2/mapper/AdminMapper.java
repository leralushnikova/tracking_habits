package com.lushnikova.homework_2.mapper;

import com.lushnikova.homework_2.dto.req.AdminRequest;
import com.lushnikova.homework_2.dto.resp.AdminResponse;
import com.lushnikova.homework_2.model.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Операция преобразования классов {@see AdminRequest}, {@see Admin} и {@see AdminResponse}
 */
@Mapper
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    /**
     * Операция преобразования класса AdminRequest в класс Admin
     * @param adminRequest - объект класса AdminRequest
     * @return возвращает объект класса Admin
     */
    Admin mapToEntity(AdminRequest adminRequest);

    /**
     * Операция преобразования класса Admin в класс AdminResponse
     * @param admin - объект класса Admin
     * @return возвращает объект класса AdminResponse
     */
    AdminResponse mapToResponse(Admin admin);
}
