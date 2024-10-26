package com.lushnikova.homework_3.servlet;

import com.lushnikova.homework_3.middleware.DateMiddleware;
import com.lushnikova.homework_3.reminder.ReminderService;
import com.lushnikova.homework_3.dto.req.UserRequest;
import com.lushnikova.homework_3.dto.resp.ErrorResponse;
import com.lushnikova.homework_3.exception.JsonParseException;
import com.lushnikova.homework_3.exception.ModelNotFound;
import com.lushnikova.homework_3.mapper.UserMapper;
import com.lushnikova.homework_3.middleware.Middleware;
import com.lushnikova.homework_3.middleware.UserMiddleware;
import com.lushnikova.homework_3.repository.UserRepository;
import com.lushnikova.homework_3.service.JsonParseService;
import com.lushnikova.homework_3.service.UserService;
import com.lushnikova.homework_3.service.impl.JsonParseServiceImpl;
import com.lushnikova.homework_3.service.impl.UserServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.time.LocalDate;

import static com.lushnikova.homework_3.consts.ErrorConsts.*;
import static com.lushnikova.homework_3.consts.StringConsts.*;
import static com.lushnikova.homework_3.consts.WebConsts.USERS_PATH;

/**
 * Класс Servlet для работы с пользователями
 */
@WebServlet(USERS_PATH)
public class UserServlet extends HttpServlet {

    /** Поле сериализации/десериализации Java-объектов */
    private final JsonParseService jsonParseService;

    /** Поле сервис пользователей */
    private final UserService userService;

    /** Поле инструмент проверки*/
    private final Middleware middleware;

    /** Поле инструмент проверки дат*/
    private final DateMiddleware dateMiddleware;

    /**
     * Конструктор - создание нового объекта
     */
    public UserServlet() {
        jsonParseService = new JsonParseServiceImpl();
        UserMapper userMapper = UserMapper.INSTANCE;
        UserRepository userRepository = UserRepository.getInstance();
        userService = new UserServiceImpl(userMapper, userRepository);
        middleware = new UserMiddleware(userService);
        this.dateMiddleware =  new DateMiddleware();
        new ReminderService(userRepository);
    }


    /**
     * Операция получения пользователя или пользователей
     * @param req - информация получения запроса
     * @param resp - ответ на основе объекта
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        String paramId = req.getPathInfo();

        byte[] response;

        try {
            if (paramId != null) {

                Long idUser = Long.parseLong(paramId.substring(7));
                if (req.getQueryString() == null) {
                    response = jsonParseService.writeValueAsBytes(userService.findById(idUser));
                }
                else {
                    String stringDateFrom = req.getParameter(DATE_FROM);
                    String stringDateTo = req.getParameter(DATE_TO);

                    if (dateMiddleware.checkDate(stringDateFrom) && dateMiddleware.checkDate(stringDateTo)) {
                        LocalDate dateFrom = LocalDate.parse(stringDateFrom);
                        LocalDate dateTo = LocalDate.parse(stringDateTo);

                        response = jsonParseService.writeValueAsBytes(userService.percentSuccessHabitsByIdUser(idUser, dateFrom, dateTo));
                    }
                    else response = getError(WRONG_DATE, resp);

                }

            }
            else response = jsonParseService.writeValueAsBytes(userService.findAll());
        } catch (JsonParseException | ModelNotFound e) {
            response = getError(WRONG_REQUEST, resp);
        }

        sendOkAndObject(resp, response);
    }

    /**
     * Операция сохранения пользователя
     * @param req - информация получения запроса
     * @param resp - ответ на основе объекта
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        UserRequest userRequest;
        try {
            userRequest = (UserRequest) jsonParseService.readObject(req.getInputStream(), UserRequest.class);

            if (!middleware.checkEmail(userRequest)) {

                if(middleware.checkPassword(userRequest.getPassword())) {

                    userService.save(userRequest);

                    System.out.println("Saved user");//сделать логгом
                    sendOk(resp);
                }
                else sendOkAndObject(resp, getError(WRONG_PASSWORD, resp));

            }
            else sendOkAndObject(resp, getError(USER_EXISTS, resp));
        } catch (IOException e) {
            sendOkAndObject(resp, getError(WRONG_REQUEST, resp));
        }
    }


    /**
     * Операция обновления пользователя
     * @param req - информация получения запроса
     * @param resp - ответ на основе объекта
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {

        String paramId = req.getPathInfo();

        if(paramId != null) {
            try {
                Long id = Long.parseLong(paramId.substring(7));

                UserRequest userRequest = (UserRequest) jsonParseService.readObject(req.getInputStream(), UserRequest.class);

                if(userRequest.getPassword() != null) {

                    if(middleware.checkPassword(userRequest.getPassword())) {
                        userService.updatePassword(id, userRequest.getPassword());

                        System.out.println("Updated user's password");//сделать логгом
                        sendOk(resp);
                    }
                    else sendOkAndObject(resp, getError(WRONG_PASSWORD, resp));

                } else if(userRequest.getEmail() != null) {

                    if (!middleware.checkEmail(userRequest)) {
                        userService.updateEmail(id, userRequest.getEmail());

                        System.out.println("Updated user's email");
                        sendOk(resp);
                    }
                    else sendOkAndObject(resp, getError(USER_EXISTS, resp));

                } else if(userRequest.getName() != null) {
                    userService.updateName(id, userRequest.getName());

                    System.out.println("Updated user's name");
                    sendOk(resp);
                }

            } catch (IOException | ModelNotFound e) {
                sendOkAndObject(resp, getError(WRONG_REQUEST, resp));
            }
        } else sendOkAndObject(resp, getError(WRONG_REQUEST, resp));

    }


    /**
     * Операция удаления пользователя
     * @param req - информация получения запроса
     * @param resp - ответ на основе объекта
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp){
        String paramId = req.getPathInfo();

        if (paramId != null) {
            Long id = Long.parseLong(paramId.substring(7));
            try {
                userService.delete(id);

                System.out.println("Deleted user");//сделать логгом
                sendOk(resp);
            } catch (ModelNotFound e) {
                sendOkAndObject(resp, getError(WRONG_REQUEST, resp));
            }
        } else sendOkAndObject(resp, getError(WRONG_REQUEST, resp));
    }

    @SneakyThrows
    private void sendOk(HttpServletResponse resp){
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    @SneakyThrows
    private void sendOkAndObject(HttpServletResponse resp, byte[] bytes){
        sendOk(resp);
        resp.getOutputStream().write(bytes);
    }


    /**
     * Функция получения ошибки
     * @param resp - ответ на основе объекта
     * @return возвращает массив байтов json
     */
    @SneakyThrows
    private byte[] getError(String error, HttpServletResponse resp){
        return jsonParseService.writeValueAsBytes(new ErrorResponse(error, resp.getStatus()));
    }
}
