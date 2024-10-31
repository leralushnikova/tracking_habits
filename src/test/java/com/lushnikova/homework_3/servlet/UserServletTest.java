package com.lushnikova.homework_3.servlet;

import com.lushnikova.homework_3.repository.UserRepository;
import com.lushnikova.homework_3.service.JsonParseService;
import com.lushnikova.homework_3.service.UserService;
import com.lushnikova.homework_3.service.impl.JsonParseServiceImpl;
import com.lushnikova.homework_3.service.impl.UserServiceImpl;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static com.lushnikova.homework_3.consts.StringConsts.DATE_FROM;
import static com.lushnikova.homework_3.consts.StringConsts.DATE_TO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("Класс тестирования сервлета пользователей")
class UserServletTest {

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    UserServlet userServlet = new UserServlet();
    UserService userService = new UserServiceImpl(UserRepository.getInstance());
    JsonParseService jsonParseService = new JsonParseServiceImpl();

    @DisplayName("Получение данных пользователя")
    @SneakyThrows
    @Test
    void doGetUser() {
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

        userServlet.doGet(request, response);
        assertEquals(outputStream.toString(), jsonParseService.writeToJson(userService.findById(idUser)));
    }

    @DisplayName("Получение процента успешного выполнения привычек за определенный период")
    @SneakyThrows
    @Test
    void doGetPercentSuccessHabits() {
        long idUser = 1;
        String localhost = "/idUser" + idUser;
        String date_from = "2024-05-10";
        String date_to = "2024-06-11";
        String QueryString = DATE_FROM + "=" + date_from + "&" + DATE_TO + "=" + date_to;

        when(request.getPathInfo()).thenReturn(localhost);
        when(request.getQueryString()).thenReturn(QueryString);
        when(request.getParameter(DATE_FROM)).thenReturn(date_from);
        when(request.getParameter(DATE_TO)).thenReturn(date_to);
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

        userServlet.doGet(request, response);

        assertEquals(outputStream.toString(), jsonParseService.writeToJson(userService.percentSuccessHabitsByIdUser(idUser, LocalDate.parse(date_from), LocalDate.parse(date_to))));
    }

    @DisplayName("Сохранение пользователя")
    @SneakyThrows
    @Test
    void doPost() {
        String userCreate = """
                {
                    "name": "lera",
                    "email": "lera@gmail.com",
                    "password": "asdfsdsA654"
                }
                """;

        when(request.getInputStream()).thenReturn(getInputStream(userCreate));
        when(request.getContentType()).thenReturn("application/json");
        when(request.getCharacterEncoding()).thenReturn("UTF-8");

        userServlet.doPost(request, response);

        assertNotNull(userService.findById(3L));
    }

    @DisplayName("Обновление пользователя")
    @SneakyThrows
    @Test
    void doPut() {
        long idUser = 2;
        String localhost = "/idUser" + idUser;
        String name = "James";
        String updateUserName = "{\"name\":\"" + name + "\"}";

        when(request.getPathInfo()).thenReturn(localhost);
        when(request.getInputStream()).thenReturn(getInputStream(updateUserName));

        userServlet.doPut(request, response);

        assertEquals(name, userService.findById(idUser).getName());

    }

    @DisplayName("Удаление пользователя")
    @SneakyThrows
    @Test
    void doDelete() {
        long idUser = 1;
        String localhost = "/idUser" + idUser;

        when(request.getPathInfo()).thenReturn(localhost);

        userServlet.doDelete(request, response);

        assertNull(userService.findById(idUser));
    }

    public static ServletInputStream getInputStream(String string){
        byte[] myBytes = string.getBytes(StandardCharsets.UTF_8);
        return new ServletInputStream() {
            private int lastIndexRetrieved = -1;
            private ReadListener readListener = null;

            @Override
            public boolean isFinished() {
                return (lastIndexRetrieved == myBytes.length - 1);
            }

            @Override
            public boolean isReady() {
                // This implementation will never block
                // We also never need to call the readListener from this method, as this method will never return false
                return isFinished();
            }

            @Override
            public void setReadListener(ReadListener readListener) {
                this.readListener = readListener;
                if (!isFinished()) {
                    try {
                        readListener.onDataAvailable();
                    } catch (IOException e) {
                        readListener.onError(e);
                    }
                } else {
                    try {
                        readListener.onAllDataRead();
                    } catch (IOException e) {
                        readListener.onError(e);
                    }
                }
            }

            @Override
            public int read() throws IOException {
                int i;
                if (!isFinished()) {
                    i = myBytes[lastIndexRetrieved + 1];
                    lastIndexRetrieved++;
                    if (isFinished() && (readListener != null)) {
                        try {
                            readListener.onAllDataRead();
                        } catch (IOException ex) {
                            readListener.onError(ex);
                            throw ex;
                        }
                    }
                    return i;
                } else {
                    return -1;
                }
            }
        };
    }

}