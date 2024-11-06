package com.lushnikova.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lushnikova.dto.request.UserRequest;
import com.lushnikova.dto.response.UserResponse;
import com.lushnikova.model.enums.Role;
import com.lushnikova.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static com.lushnikova.consts.WebConsts.ADMIN_PATH;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Класс тестирования контроллера администратора")
@WebMvcTest(AdminController.class)
class AdminControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserServiceImpl userService;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("Список пользователей")
    @Test
    void shouldGetAllUsers() throws Exception {
        Mockito.when(userService.findAll()).thenReturn(getUsers());

        mockMvc.perform(get(ADMIN_PATH))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(objectMapper.writeValueAsString(getUsers()))));
    }

    @DisplayName("Блокировка пользователя")
    @Test
    void shouldBlockUser() throws Exception {

        UserRequest userRequest = new UserRequest();
        userRequest.setIsActive(false);

        String json = objectMapper.writeValueAsString(userRequest);

        long idUser = 2;

        mockMvc.perform(put(ADMIN_PATH + "/{id}", idUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(userService, times(1)).blockByIdUser(idUser, userRequest.getIsActive());
    }

    @DisplayName("Удаление пользователя")
    @Test
    void shouldDeleteUser() throws Exception {
        long idUser = 2;

        mockMvc.perform(delete(ADMIN_PATH + "/{id}", idUser))
                .andExpect(status().isOk());

        verify(userService, times(1)).delete(idUser);
    }



    private List<UserResponse> getUsers(){
        List<UserResponse> users = new ArrayList<>();

        UserResponse userResponse = UserResponse.builder()
                .id(1L)
                .name("user")
                .email("user@email.com")
                .password("password")
                .isActive(true)
                .role(Role.USER)
                .build();

        UserResponse admin = UserResponse.builder()
                .id(2L)
                .name("admin")
                .email("admin@email.com")
                .password("admin")
                .isActive(true)
                .role(Role.ADMIN)
                .build();

        users.add(userResponse);
        users.add(admin);
        return users;
    }
}