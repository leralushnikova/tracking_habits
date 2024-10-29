package com.lushnikova.homework_3.servlet;

import com.lushnikova.homework_3.repository.UserRepository;
import com.lushnikova.homework_3.service.AdminService;
import com.lushnikova.homework_3.service.JsonParseService;
import com.lushnikova.homework_3.service.impl.AdminServiceImpl;
import com.lushnikova.homework_3.service.impl.JsonParseServiceImpl;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;

import static com.lushnikova.homework_3.servlet.UserServletTest.getInputStream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("Класс тестирования сервлета администратора")
class AdminServletTest {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    AdminServlet adminServlet = new AdminServlet();
    AdminService adminService = new AdminServiceImpl();
    JsonParseService jsonParseService = new JsonParseServiceImpl();
    UserRepository userRepository = UserRepository.getInstance();


    @DisplayName("Список пользователей")
    @SneakyThrows
    @Test
    void doGetUsers() {
        String localhost = "/users";

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

        adminServlet.doGet(request, response);

        assertEquals(outputStream.toString(), jsonParseService.writeToJson(adminService.findAllUsers()));
    }

    @DisplayName("Блокировка пользователя")
    @SneakyThrows
    @Test
    void doPut() {
        long idUser = 1;
        String localhost = "/idUser" + idUser;
        boolean isActive = false;
        String updateUserName = "{\"active\":\"" + isActive + "\"}";

        when(request.getPathInfo()).thenReturn(localhost);
        when(request.getInputStream()).thenReturn(getInputStream(updateUserName));

        adminServlet.doPut(request, response);

        assertEquals(isActive, userRepository.findById(idUser).isActive());
    }

    @DisplayName("Удаление пользователя")
    @SneakyThrows
    @Test
    void doDelete() {
        long idUser = 1;
        String localhost = "/idUser" + idUser;

        when(request.getPathInfo()).thenReturn(localhost);

        adminServlet.doDelete(request, response);

        assertNull(userRepository.findById(idUser));
    }
}