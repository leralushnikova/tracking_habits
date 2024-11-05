package com.lushnikova.controller;

import com.lushnikova.annotations.Loggable;
import com.lushnikova.dto.request.UserRequest;
import com.lushnikova.dto.response.UserResponse;
import com.lushnikova.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lushnikova.consts.WebConsts.ADMIN_PATH;

/**
 * Класс Controller для администратора
 */
@Loggable
@RestController
@RequestMapping(ADMIN_PATH)
public class AdminController {

    /** Поле сервис пользователей*/
    private final UserService userService;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param userService - сервис пользователей
     */
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Операция получения списка пользователей
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserResponse> getUsers() {
        return userService.findAll();
    }

    /**
     * Операция блокировки/разблокировки пользователя
     */
    @PutMapping(value = "/{id}")
    public void blockUser(@PathVariable("id") Long id, @RequestBody UserRequest userRequest) {
        userService.blockByIdUser(id, userRequest.getIsActive());
    }

    /**
     * Операция удаления пользователя
     */
    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
    }

}
