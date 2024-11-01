package com.lushnikova.controller;

import com.lushnikova.dto.request.UserRequest;
import com.lushnikova.dto.response.UserResponse;
import com.lushnikova.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lushnikova.consts.WebConsts.ADMIN_PATH;

@RestController
@RequestMapping(ADMIN_PATH)
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserResponse> getUsers() {
        return userService.findAll();
    }

    @PutMapping(value = "/{id}")
    public void blockUser(@PathVariable("id") Long id, @RequestBody UserRequest userRequest) {
        userService.blockByIdUser(id, userRequest.getIsActive());
    }

    @DeleteMapping(value = "/{id}")
    public void deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
    }

}
