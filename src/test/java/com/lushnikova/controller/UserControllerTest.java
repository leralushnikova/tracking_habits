package com.lushnikova.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lushnikova.dto.request.HabitRequest;
import com.lushnikova.dto.request.UserRequest;
import com.lushnikova.model.enums.Repeat;
import com.lushnikova.model.enums.Role;
import com.lushnikova.model.enums.Status;
import com.lushnikova.service.impl.HabitServiceImpl;
import com.lushnikova.service.impl.UserServiceImpl;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static com.lushnikova.consts.StringConsts.*;
import static com.lushnikova.consts.WebConsts.USERS_PATH;
import static com.lushnikova.controller.UserController.getStatus;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Класс тестирования контроллер пользователей")
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest extends AbstractIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserController userController;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    HabitServiceImpl habitService;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("Получение данных пользователя")
    @Test
    void shouldGetUser() throws Exception {
        long idUser = 1;

        mockMvc.perform(get(USERS_PATH + "/{idUser}", idUser))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(objectMapper.writeValueAsString(userService.findById(idUser)))));
    }

    @DisplayName("Список привычек пользователя")
    @Test
    void shouldGetHabits() throws Exception {
        long idUser = 1;

        mockMvc.perform(get(USERS_PATH + "/{idUser}/habits", idUser))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(objectMapper.writeValueAsString(habitService.findAll(idUser)))));
    }

    @DisplayName("Список привычек пользователя по статусу")
    @Test
    void shouldGetHabitsByStatus() throws Exception {
        long idUser = 1;
        String status = CREATE;

        mockMvc.perform(get(USERS_PATH + "/{idUser}/habits?status={status}", idUser, status))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(objectMapper.writeValueAsString(habitService.getHabitsByStatus(idUser, getStatus(status))))));
    }


    @DisplayName("Получение процента успешного выполнения привычек за определенный период")
    @Test
    void shouldGetPercentSuccessHabits() throws Exception {
        long idUser = 1;
        String date_from = "2024-10-08";
        String date_to = "2024-10-15";

        mockMvc.perform(get(USERS_PATH + "/{idUser}/habits/percent?date_from={date_from}&date_to={date_to}", idUser, date_from, date_to))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(objectMapper.writeValueAsString(habitService.percentSuccessHabits(idUser, LocalDate.parse(date_from), LocalDate.parse(date_to))))));
    }

    @DisplayName("Получение данных привычки пользователя")
    @Test
    void shouldGetHabit() throws Exception {
        long idUser = 2;
        long idHabit = 1;

        mockMvc.perform(get(USERS_PATH + "/{idUser}/habits/{idHabit}", idUser, idHabit))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(objectMapper.writeValueAsString(habitService.findById(idHabit,idUser)))));
    }

    @DisplayName("Отчет по привычке")
    @Test
    void shouldGetReportHabit() throws Exception {
        long idUser = 2;
        long idHabit = 1;

        mockMvc.perform(get(USERS_PATH + "/{idUser}/habits/{idHabit}/report", idUser, idHabit))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(habitService.reportHabit(idHabit, idUser).get())));
    }

    @DisplayName("Создание пользователя")
    @Test
    void shouldSaveUser() throws Exception {
        String name = "name";
        UserRequest userRequest = UserRequest.builder()
                .name(name)
                .email("email@email.com")
                .password("passwordLKJ654")
                .isActive(true)
                .role(Role.USER)
                .build();

        String json = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post(USERS_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());

        assertNotNull(userService.findAll().stream().filter(el -> el.getName().equals(name)));
    }

    @DisplayName("Создание привычки")
    @Test
    void shouldSaveHabit() throws Exception {
        long idUser = 2;

        HabitRequest habitRequest = HabitRequest.builder()
                .title("title")
                .description("description")
                .repeat(Repeat.DAILY)
                .build();

        String json = objectMapper.writeValueAsString(habitRequest);

        mockMvc.perform(post(USERS_PATH + "/{idUser}", idUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        assertNotNull(habitService.findAll(idUser).stream().filter(el -> el.getTitle().equals(habitRequest.getTitle())));
    }

    @DisplayName("Обновления имени пользователя")
    @Test
    void shouldUpdateUserName() throws Exception {
        long idUser = 2;

        UserRequest userRequest = new UserRequest();
        userRequest.setName("Name");

        String json = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(put(USERS_PATH + "/{idUser}", idUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        assertEquals(userService.findById(idUser).get().getName(), userRequest.getName());
    }

    @SneakyThrows
    @Test
    @DisplayName("Изменение пароля пользователя")
    void shouldUpdatePasswordUser() {
        long idUser = 2;

        UserRequest userRequest = new UserRequest();
        userRequest.setPassword("Password123");

        String json = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(put(USERS_PATH + "/{idUser}", idUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }


    @DisplayName("Обновления почты пользователя")
    @Test
    void shouldUpdateUserEmail() throws Exception {
        long idUser = 1;

        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("user@email.com");

        String json = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(put(USERS_PATH + "/{idUser}", idUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        assertEquals(userService.findById(idUser).get().getEmail(), userRequest.getEmail());
    }

    @DisplayName("Обновление названия привычки пользователя")
    @Test
    void shouldUpdateHabitTitle() throws Exception {
        long idUser = 2;
        long idHabit = 1;

        HabitRequest habitRequest = new HabitRequest();
        habitRequest.setTitle("New title");

        String json = objectMapper.writeValueAsString(habitRequest);

        mockMvc.perform(put(USERS_PATH + "/{idUser}/habits/{idHabit}", idUser, idHabit)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        assertEquals(habitService.findById(idHabit, idUser).get().getTitle(), habitRequest.getTitle());
    }

    @DisplayName("Обновление описания привычки пользователя")
    @Test
    void shouldUpdateHabitDescription() throws Exception {
        long idUser = 2;
        long idHabit = 1;

        HabitRequest habitRequest = new HabitRequest();
        habitRequest.setDescription("New Description");

        String json = objectMapper.writeValueAsString(habitRequest);

        mockMvc.perform(put(USERS_PATH + "/{idUser}/habits/{idHabit}", idUser, idHabit)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        assertEquals(habitService.findById(idHabit, idUser).get().getDescription(), habitRequest.getDescription());
    }

    @DisplayName("Обновление частоты повторения привычки пользователя")
    @Test
    void shouldUpdateHabitRepeat() throws Exception {
        long idUser = 2;
        long idHabit = 1;

        HabitRequest habitRequest = new HabitRequest();
        habitRequest.setRepeat(Repeat.DAILY);

        String json = objectMapper.writeValueAsString(habitRequest);

        mockMvc.perform(put(USERS_PATH + "/{idUser}/habits/{idHabit}", idUser, idHabit)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        assertEquals(habitService.findById(idHabit, idUser).get().getRepeat(), habitRequest.getRepeat());
    }

    @DisplayName("Обновление статуса привычки пользователя")
    @Test
    void shouldUpdateHabitStatus() throws Exception {
        long idUser = 2;
        long idHabit = 1;

        HabitRequest habitRequest = new HabitRequest();
        habitRequest.setStatus(Status.CREATED);

        String json = objectMapper.writeValueAsString(habitRequest);

        mockMvc.perform(put(USERS_PATH + "/{idUser}/habits/{idHabit}", idUser, idHabit)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        assertEquals(habitService.findById(idHabit, idUser).get().getStatus(), habitRequest.getStatus());
    }


    @DisplayName("Включение уведомления привычки")
    @Test
    void shouldSwitchOnOrOffPushNotification() throws Exception {
        long idUser = 2;
        long idHabit = 1;

        String time = "11:10";

        HabitRequest habitRequest = new HabitRequest();
        habitRequest.setPush(time);

        String json = objectMapper.writeValueAsString(habitRequest);

        mockMvc.perform(put(USERS_PATH + "/{idUser}/habits/{idHabit}", idUser, idHabit)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        assertEquals(habitService.findById(idHabit, idUser).get().getPushTime(), Time.valueOf(LocalTime.parse(time)));
    }


    @DisplayName("Отметка, что за день привычка выполнена")
    @Test
    void shouldSetDoneDates() throws Exception {
        long idUser = 2;
        long idHabit = 1;

        HabitRequest habitRequest = new HabitRequest();
        habitRequest.setDone(YES);

        String json = objectMapper.writeValueAsString(habitRequest);

        mockMvc.perform(put(USERS_PATH + "/{idUser}/habits/{idHabit}", idUser, idHabit)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @DisplayName("Удаление пользователя")
    @Test
    void shouldDeleteUser() throws Exception {
        long idUser = 2;

        mockMvc.perform(delete(USERS_PATH + "/{idUser}", idUser))
                .andExpect(status().isOk());

        assertEquals(userService.findById(idUser), Optional.empty());
    }

    @DisplayName("Удаление привычки")
    @Test
    void shouldDeleteHabit() throws Exception {
        long idUser = 2;
        long idHabit = 2;

        mockMvc.perform(delete(USERS_PATH + "/habits/{idHabits}", idHabit))
                .andExpect(status().isOk());

        assertEquals(habitService.findById(idHabit, idUser), Optional.empty());
    }

}
