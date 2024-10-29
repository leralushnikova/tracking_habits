package com.lushnikova.homework_3.servlet;

import com.lushnikova.homework_3.mapper.UserMapper;
import com.lushnikova.homework_3.model.ENUM.Status;
import com.lushnikova.homework_3.repository.UserRepository;
import com.lushnikova.homework_3.service.JsonParseService;
import com.lushnikova.homework_3.service.UserService;
import com.lushnikova.homework_3.service.impl.JsonParseServiceImpl;
import com.lushnikova.homework_3.service.impl.UserServiceImpl;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalTime;

import static com.lushnikova.homework_3.consts.StringConsts.*;
import static com.lushnikova.homework_3.servlet.HabitServlet.getStatus;
import static com.lushnikova.homework_3.servlet.UserServletTest.getInputStream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Класс тестирования сервлета привычек")
class HabitServletTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    UserService userService = new UserServiceImpl(UserMapper.INSTANCE, UserRepository.getInstance());
    HabitServlet habitServlet = new HabitServlet();
    JsonParseService jsonParseService = new JsonParseServiceImpl();

    @DisplayName("Список привычек пользователя")
    @SneakyThrows
    @Test
    void doGetHabits(){
        long idUser = 1;
        String localhost = "/idUser" + idUser;
        when(request.getPathInfo()).thenReturn(localhost);
        when(response.getOutputStream()).thenReturn(new ServletOutputStream() {
            @Override
            public boolean isReady() {return false;}

            @Override
            public void setWriteListener(WriteListener writeListener) {}

            @Override
            public void write(int b){
                outputStream.write(b);
            }
        });

        habitServlet.doGet(request, response);
        assertEquals(outputStream.toString(), jsonParseService.writeToJson(userService.getHabitsByIdUser(idUser)));
    }


    @DisplayName("Список привычек пользователя по статусу")
    @SneakyThrows
    @Test
    void doGetHabitsByStatus(){
        long idUser = 1;
        String localhost = "/idUser" + idUser;
        String QueryString = STATUS + "=" + CREATE;

        when(request.getPathInfo()).thenReturn(localhost);
        when(request.getQueryString()).thenReturn(QueryString);
        when(request.getParameter(STATUS)).thenReturn(CREATE);
        when(response.getOutputStream()).thenReturn(new ServletOutputStream() {
            @Override
            public boolean isReady() {return false;}

            @Override
            public void setWriteListener(WriteListener writeListener) {}

            @Override
            public void write(int b){
                outputStream.write(b);
            }
        });

        habitServlet.doGet(request, response);
        Status status = getStatus(CREATE);
        assertEquals(outputStream.toString(), jsonParseService.writeToJson(userService.getHabitsByStatusByIdUser(idUser, status)));
    }


    @DisplayName("Список привычек пользователя по дате создания")
    @SneakyThrows
    @Test
    void doGetHabitsByLocalDate(){
        long idUser = 1;
        String localhost = "/idUser" + idUser;
        String create_at = "2024-09-30";
        String QueryString = DATE + "=" + create_at;

        when(request.getPathInfo()).thenReturn(localhost);
        when(request.getQueryString()).thenReturn(QueryString);
        when(request.getParameter(DATE)).thenReturn(create_at);
        when(response.getOutputStream()).thenReturn(new ServletOutputStream() {
            @Override
            public boolean isReady() {return false;}

            @Override
            public void setWriteListener(WriteListener writeListener) {}

            @Override
            public void write(int b){
                outputStream.write(b);
            }
        });

        habitServlet.doGet(request, response);
        assertEquals(outputStream.toString(), jsonParseService.writeToJson(userService.getHabitsByLocalDateByIdUser(idUser, LocalDate.parse(create_at))));
    }

    @DisplayName("Отчет по привычке")
    @SneakyThrows
    @Test
    void doGetHabit(){
        long idUser = 1;
        long idHabit = 1;
        String localhost = "/idUser" + idUser + "/idHabit" + idHabit;

        when(request.getPathInfo()).thenReturn(localhost);
        when(response.getOutputStream()).thenReturn(new ServletOutputStream() {
            @Override
            public boolean isReady() {return false;}

            @Override
            public void setWriteListener(WriteListener writeListener) {}

            @Override
            public void write(int b){
                outputStream.write(b);
            }
        });

        habitServlet.doGet(request, response);
        assertEquals(outputStream.toString(), jsonParseService.writeToJson(userService.reportHabitByIdUser(idUser, idHabit)));
    }

    @DisplayName("Сохранение привычки")
    @SneakyThrows
    @Test
    void doPostHabit(){
        long idUser = 1;
        String localhost = "/idUser" + idUser;

        String habitCreate = """
                {
                    "title": "new habit",
                    "description": "description for new habit",
                    "repeat": "DAILY"
                }
                """;

        when(request.getPathInfo()).thenReturn(localhost);
        when(request.getInputStream()).thenReturn(getInputStream(habitCreate));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        habitServlet.doPost(request, response);
        assertNotNull(userService.getHabitsByIdUser(idUser).stream().filter(el -> el.getId().equals(11L)).findFirst());
    }

    @DisplayName("Обновление названия привычки")
    @SneakyThrows
    @Test
    void doPutHabitTitle(){
        long idUser = 1;
        long idHabit = 1;
        String localhost = "/idUser" + idUser + "/idHabit" + idHabit;
        String title = "new title";
        String updateHabit = "{\"title\":\"" + title + "\"}";

        when(request.getPathInfo()).thenReturn(localhost);
        when(request.getInputStream()).thenReturn(getInputStream(updateHabit));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        habitServlet.doPut(request, response);
        assertNotNull(userService.getHabitsByIdUser(idUser).stream().filter(el -> el.getId().equals(idHabit)).findFirst());

    }

    @DisplayName("Обновление описания привычки")
    @SneakyThrows
    @Test
    void doPutHabitDescription(){
        long idUser = 1;
        long idHabit = 1;
        String localhost = "/idUser" + idUser + "/idHabit" + idHabit;
        String description = "new description";
        String updateHabit = "{\"description\":\"" + description + "\"}";

        when(request.getPathInfo()).thenReturn(localhost);
        when(request.getInputStream()).thenReturn(getInputStream(updateHabit));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        habitServlet.doPut(request, response);
        assertNotNull(userService.getHabitsByIdUser(idUser).stream().filter(el -> el.getId().equals(idHabit)).findFirst());

    }

    @DisplayName("Обновление статуса привычки")
    @SneakyThrows
    @Test
    void doPutHabitStatus(){
        long idUser = 1;
        long idHabit = 1;
        String localhost = "/idUser" + idUser + "/idHabit" + idHabit;
        String status = IN_PROGRESS;
        String updateHabit = "{\"title\":\"" + status + "\"}";

        when(request.getPathInfo()).thenReturn(localhost);
        when(request.getInputStream()).thenReturn(getInputStream(updateHabit));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        habitServlet.doPut(request, response);
        assertNotNull(userService.getHabitsByIdUser(idUser).stream().filter(el -> el.getId().equals(idHabit)).findFirst());

    }

    @DisplayName("Обновление частоты повторения привычки")
    @SneakyThrows
    @Test
    void doPutHabitRepeat(){
        long idUser = 1;
        long idHabit = 1;
        String localhost = "/idUser" + idUser + "/idHabit" + idHabit;
        String repeat = WEEKLY;
        String updateHabit = "{\"title\":\"" + repeat + "\"}";

        when(request.getPathInfo()).thenReturn(localhost);
        when(request.getInputStream()).thenReturn(getInputStream(updateHabit));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        habitServlet.doPut(request, response);
        assertNotNull(userService.getHabitsByIdUser(idUser).stream().filter(el -> el.getId().equals(idHabit)).findFirst());

    }

    @DisplayName("Включение отправки уведомления привычки в определенное время")
    @SneakyThrows
    @Test
    void doPutHabitPushTime(){
        long idUser = 1;
        long idHabit = 1;
        String localhost = "/idUser" + idUser + "/idHabit" + idHabit;
        String time = "11:15";
        String pushTime = "{\"pushTime\":\"" + time + "\"}";

        when(request.getPathInfo()).thenReturn(localhost);
        when(request.getInputStream()).thenReturn(getInputStream(pushTime));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        habitServlet.doPut(request, response);
        assertEquals(LocalTime.parse(time), userService.getHabitsByIdUser(idUser).stream().filter(el -> el.getId().equals(idHabit)).findFirst().get().getPushTime());
    }

    @DisplayName("Удаление привычки")
    @SneakyThrows
    @Test
    void doDelete(){
        long idUser = 1;
        long idHabit = 1;
        String localhost = "/idUser" + idUser + "/idHabit" + idHabit;

        when(request.getPathInfo()).thenReturn(localhost);

        habitServlet.doDelete(request, response);

        assertNull(userService.getHabitsByIdUser(idUser).stream().filter(el -> el.getId().equals(idHabit)).findFirst().orElse(null));
    }
}