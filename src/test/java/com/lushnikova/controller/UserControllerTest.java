package com.lushnikova.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lushnikova.dto.response.UserResponse;
import com.lushnikova.middleware.DateMiddleware;
import com.lushnikova.middleware.Middleware;
import com.lushnikova.model.enums.Role;
import com.lushnikova.service.impl.HabitServiceImpl;
import com.lushnikova.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static com.lushnikova.consts.WebConsts.USERS_PATH;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Класс тестирования контроллер пользователей")
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserServiceImpl userService;

    @MockBean
    HabitServiceImpl habitService;

    @MockBean
    DateMiddleware dateMiddleware;

    @MockBean
    Middleware middleware;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("Получение данных пользователя")
    @Test
    void shouldGetUser() throws Exception {
        long idUser = 1;

        UserResponse userResponse = UserResponse.builder()
                .id(idUser)
                .name("user")
                .email("user@email.com")
                .password("password")
                .isActive(true)
                .role(Role.USER)
                .build();

        Mockito.when(userService.findById(idUser)).thenReturn(Optional.of(userResponse));

        mockMvc.perform(get(USERS_PATH + "/{idUser}", idUser))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(objectMapper.writeValueAsString(Optional.of(userResponse)))));
    }


}
