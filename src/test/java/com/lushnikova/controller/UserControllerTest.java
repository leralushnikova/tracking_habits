package com.lushnikova.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.lushnikova.dto.request.HabitRequest;
import com.lushnikova.dto.request.UserRequest;
import com.lushnikova.dto.response.HabitResponse;
import com.lushnikova.dto.response.UserResponse;
import com.lushnikova.middleware.DateMiddleware;
import com.lushnikova.middleware.Middleware;
import com.lushnikova.model.Habit;
import com.lushnikova.model.enums.Repeat;
import com.lushnikova.model.enums.Role;
import com.lushnikova.model.enums.Status;
import com.lushnikova.service.impl.HabitServiceImpl;
import com.lushnikova.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.lushnikova.consts.StringConsts.*;
import static com.lushnikova.consts.WebConsts.ADMIN_PATH;
import static com.lushnikova.consts.WebConsts.USERS_PATH;
import static com.lushnikova.controller.UserController.getStatus;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Класс тестирования контроллер пользователей")
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
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

        Mockito.when(userService.findById(idUser)).thenReturn(Optional.of(getUserResponse(idUser)));

        mockMvc.perform(get(USERS_PATH + "/{idUser}", idUser))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(objectMapper.writeValueAsString(Optional.of(getUserResponse(idUser))))));
    }

    @DisplayName("Список привычек пользователя")
    @Test
    void shouldGetHabits() throws Exception {
        long idUser = 1;

        Mockito.when(habitService.findAll(idUser)).thenReturn(getHabits(idUser));

        mockMvc.perform(get(USERS_PATH + "/{idUser}/habits", idUser))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(objectMapper.writeValueAsString(getHabits(idUser)))));
    }

    @DisplayName("Список привычек пользователя по статусу")
    @Test
    void shouldGetHabitsByStatus() throws Exception {
        long idUser = 1;
        String status = CREATE;

        Mockito.when(habitService.getHabitsByStatus(idUser, getStatus(status))).thenReturn(getHabits(idUser).stream().filter(el->el.getStatus().equals(getStatus(status))).toList());

        mockMvc.perform(get(USERS_PATH + "/{idUser}/habits?status={status}", idUser, status))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(objectMapper.writeValueAsString(getHabits(idUser).stream().filter(el->el.getStatus().equals(getStatus(status))).toList()))));
    }


    @DisplayName("Получение процента успешного выполнения привычек за определенный период")
    @Test
    void shouldGetPercentSuccessHabits() throws Exception {
        long idUser = 1;
        String date_from = "2024-10-08";
        String date_to = "2024-10-15";

        mockMvc.perform(get(USERS_PATH + "/{idUser}/habits/percent?date_from={date_from}&date_to={date_to}", idUser, date_from, date_to))
                .andExpect(status().isNotFound());
    }

    @DisplayName("Получение данных привычки пользователя")
    @Test
    void shouldGetHabit() throws Exception {
        long idUser = 1;
        long idHabit = 1;

        Mockito.when(habitService.findById(idHabit,idUser)).thenReturn(Optional.of(getHabitResponse(idHabit, idUser)));

        mockMvc.perform(get(USERS_PATH + "/{idUser}/habits/{idHabit}", idUser, idHabit))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(objectMapper.writeValueAsString(Optional.of(getHabitResponse(idHabit, idUser))))));
    }

    @DisplayName("Отчет по привычке")
    @Test
    void shouldGetReportHabit() throws Exception {
        long idUser = 1;
        long idHabit = 1;

        Mockito.when(habitService.reportHabit(idHabit, idUser)).thenReturn(Optional.of(reportHabit(idHabit, idHabit)));

        mockMvc.perform(get(USERS_PATH + "/{idUser}/habits/{idHabit}/report", idUser, idHabit))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(reportHabit(idHabit, idHabit))));

    }

    @DisplayName("Создание пользователя")
    @Test
    void shouldSaveUser() throws Exception {
        long idUser = 1;

        UserResponse userResponse = getUserResponse(idUser);
        UserRequest userRequest = getUserRequest(userResponse);

        Mockito.when(userService.save(userRequest)).thenReturn(Optional.of(userResponse));

        String json = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(post(USERS_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());

        verify(userService, times(0)).save(getUserRequest(userResponse));
    }

    @DisplayName("Создание привычки")
    @Test
    void shouldSaveHabit() throws Exception {
        long idUser = 1;

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

    }

    @DisplayName("Обновления имени пользователя")
    @Test
    void shouldUpdateUserName() throws Exception {
        long idUser = 1;

        UserRequest userRequest = new UserRequest();
        userRequest.setName("Name");

        String json = objectMapper.writeValueAsString(userRequest);

        mockMvc.perform(put(USERS_PATH + "/{idUser}", idUser)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(userService, times(1)).updateName(idUser, userRequest.getName());
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

        verify(userService, times(1)).updateEmail(idUser, userRequest.getEmail());
    }

    @DisplayName("Обновление описания привычки пользователя")
    @Test
    void shouldUpdateHabitDescription() throws Exception {
        long idUser = 1;
        long idHabit = 1;

        HabitRequest habitRequest = new HabitRequest();
        habitRequest.setDescription("New Description");

        String json = objectMapper.writeValueAsString(habitRequest);

        mockMvc.perform(put(USERS_PATH + "/{idUser}/habits/{idHabit}", idUser, idHabit)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(habitService, times(1)).updateDescriptionByIdHabit(idUser, habitRequest.getDescription());
    }

    @DisplayName("Обновление названия привычки пользователя")
    @Test
    void shouldUpdateHabitTitle() throws Exception {
        long idUser = 1;
        long idHabit = 1;

        HabitRequest habitRequest = new HabitRequest();
        habitRequest.setTitle("New title");

        String json = objectMapper.writeValueAsString(habitRequest);

        mockMvc.perform(put(USERS_PATH + "/{idUser}/habits/{idHabit}", idUser, idHabit)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(habitService, times(1)).updateTitleByIdHabit(idUser, habitRequest.getTitle());
    }

    @DisplayName("Обновление частоты повторения привычки пользователя")
    @Test
    void shouldUpdateHabitRepeat() throws Exception {
        long idUser = 1;
        long idHabit = 1;

        HabitRequest habitRequest = new HabitRequest();
        habitRequest.setRepeat(Repeat.DAILY);

        String json = objectMapper.writeValueAsString(habitRequest);

        mockMvc.perform(put(USERS_PATH + "/{idUser}/habits/{idHabit}", idUser, idHabit)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(habitService, times(1)).updateRepeatByIdHabit(idUser, habitRequest.getRepeat());
    }

    @DisplayName("Обновление статуса привычки пользователя")
    @Test
    void shouldUpdateHabitStatus() throws Exception {
        long idUser = 1;
        long idHabit = 1;

        HabitRequest habitRequest = new HabitRequest();
        habitRequest.setStatus(Status.CREATED);

        String json = objectMapper.writeValueAsString(habitRequest);

        mockMvc.perform(put(USERS_PATH + "/{idUser}/habits/{idHabit}", idUser, idHabit)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(habitService, times(1)).updateStatusByIdHabit(idUser, habitRequest.getStatus());
    }


    @DisplayName("Выключение отправки уведомления привычки пользователя")
    @Test
    void shouldUpdateHabitNotifications() throws Exception {
        long idUser = 1;
        long idHabit = 1;

        HabitRequest habitRequest = new HabitRequest();
        habitRequest.setPush(NO);

        String json = objectMapper.writeValueAsString(habitRequest);

        mockMvc.perform(put(USERS_PATH + "/{idUser}/habits/{idHabit}", idUser, idHabit)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(habitService, times(1)).switchOnOrOffPushNotification(idHabit, null);
    }


    @DisplayName("Отметка, что за день привычка выполнена")
    @Test
    void shouldUpdateHabitDone() throws Exception {
        long idUser = 1;
        long idHabit = 1;

        HabitRequest habitRequest = new HabitRequest();
        habitRequest.setDone(YES);

        String json = objectMapper.writeValueAsString(habitRequest);

        mockMvc.perform(put(USERS_PATH + "/{idUser}/habits/{idHabit}", idUser, idHabit)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(habitService, times(1)).setDoneDates(idHabit, idUser);
    }

    @DisplayName("Удаление пользователя")
    @Test
    void shouldDeleteUser() throws Exception {
        long idUser = 2;

        mockMvc.perform(delete(USERS_PATH + "/{idUser}", idUser))
                .andExpect(status().isOk());

        verify(userService, times(1)).delete(idUser);
    }

    @DisplayName("Удаление привычки")
    @Test
    void shouldDeleteHabit() throws Exception {
        long idHabit = 2;

        mockMvc.perform(delete(USERS_PATH + "/habits/{idHabits}", idHabit))
                .andExpect(status().isOk());

        verify(habitService, times(1)).delete(idHabit);
    }

    private HabitResponse getHabitResponse(Long idHabit, Long idUser){
        return HabitResponse.builder()
                .id(idHabit)
                .userId(idUser)
                .title("habit1")
                .description("habit1")
                .repeat(Repeat.DAILY)
                .status(Status.CREATED)
                .createdAt(LocalDate.of(2024, 10, 11).toString())
                .build();
    }

    private UserResponse getUserResponse(Long idUser){
        return UserResponse.builder()
                .id(idUser)
                .name("user")
                .email("user@email.com")
                .password("password")
                .isActive(true)
                .role(Role.USER)
                .build();
    }

    private UserRequest getUserRequest(UserResponse userResponse){
        return UserRequest.builder()
                .name(userResponse.getName())
                .email(userResponse.getEmail())
                .password("password")
                .isActive(userResponse.getIsActive())
                .role(userResponse.getRole())
                .build();
    }

    private List<HabitResponse> getHabits(Long idUser){
        List<HabitResponse> habits = new ArrayList<>();

        HabitResponse habitResponse1 = HabitResponse.builder()
                .id(1L)
                .userId(idUser)
                .title("habit1")
                .description("habit1")
                .repeat(Repeat.DAILY)
                .status(Status.CREATED)
                .createdAt(LocalDate.of(2024, 10, 11).toString())
                .build();

        HabitResponse habitResponse2 = HabitResponse.builder()
                .id(2L)
                .userId(idUser)
                .title("habit2")
                .description("habit2")
                .repeat(Repeat.WEEKLY)
                .status(Status.IN_PROGRESS)
                .createdAt(LocalDate.of(2024, 6, 7).toString())
                .build();



        habits.add(habitResponse1);
        habits.add(habitResponse2);
        return habits;
    }

    public String reportHabit(Long idHabit, Long idUser) {
        StringBuilder builder = new StringBuilder();

        Habit habitResponse1 = Habit.builder()
                .id(idHabit)
                .userId(idUser)
                .title("habit1")
                .description("habit1")
                .repeat(Repeat.DAILY)
                .status(Status.CREATED)
                .createdAt(Date.valueOf(LocalDate.of(2024, 10, 11)))
                .build();

        builder.append("Id user: ").append(habitResponse1.getId()).append("\n");
        builder.append("Title: ").append(habitResponse1.getTitle()).append("\n");
        builder.append("Description: ").append(habitResponse1.getDescription()).append("\n");
        builder.append("Status: ").append(habitResponse1.getStatus()).append("\n");
        builder.append("Repeat: ").append(habitResponse1.getRepeat()).append("\n");

        StringBuilder stringBuilder = new StringBuilder("Notifications : ");
        if (habitResponse1.getPushTime() == null) stringBuilder.append("off");
        else stringBuilder.append("on").append(" at ").append(habitResponse1.getPushTime());

        builder.append(stringBuilder).append("\n");
        builder.append("Streak: ").append(habitResponse1.getStreak()).append("\n");
        builder.append("Done Date: ").append(habitResponse1.getDoneDates()).append("\n");

        return builder.toString();
    }


}
