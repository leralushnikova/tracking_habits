package com.lushnikova.homework_3.servlet;

import com.lushnikova.homework_3.annotations.Loggable;
import com.lushnikova.homework_3.middleware.DateMiddleware;
import com.lushnikova.homework_3.model.ENUM.Repeat;
import com.lushnikova.homework_3.model.ENUM.Statistics;
import com.lushnikova.homework_3.dto.request.HabitRequest;
import com.lushnikova.homework_3.dto.response.ErrorResponse;
import com.lushnikova.homework_3.exception.JsonParseException;
import com.lushnikova.homework_3.exception.ModelNotFound;
import com.lushnikova.homework_3.mapper.UserMapper;
import com.lushnikova.homework_3.model.ENUM.Status;
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
import java.time.LocalTime;

import static com.lushnikova.homework_3.consts.ErrorConsts.*;
import static com.lushnikova.homework_3.consts.StringConsts.*;
import static com.lushnikova.homework_3.consts.WebConsts.HABITS_PATH;


/**
 * Класс Servlet для работы с привычками
 */
@Loggable
@WebServlet(HABITS_PATH)
public class HabitServlet extends HttpServlet {

    /** Поле сериализации/десериализации Java-объектов*/
    private final JsonParseService jsonParseService;

    /** Поле инструмент проверки дат*/
    private final DateMiddleware dateMiddleware;

    /**
     * Поле сервис пользователей
     */
    private final UserService userService;

    public HabitServlet() {
        jsonParseService = new JsonParseServiceImpl();
        UserMapper userMapper = UserMapper.INSTANCE;
        UserRepository userRepository = UserRepository.getInstance();
        userService = new UserServiceImpl(userMapper, userRepository);
        this.dateMiddleware =  new DateMiddleware();
    }

    /**
     * Операция получения привычек
     *
     * @param req  - информация получения запроса
     * @param resp - ответ на основе объекта
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.getPathInfo();

        byte[] response;
        try {

            if (pathInfo.contains(ID_HABIT)) {

                String[] s = pathInfo.substring(1).split("/");

                Long idUser = Long.parseLong(s[0].substring(6));
                Long idHabit = Long.parseLong(s[1].substring(7));

                if (req.getQueryString() == null) {
                    response = jsonParseService.writeValueAsBytes(userService.reportHabitByIdUser(idUser, idHabit));
                }
                else {
                    Statistics statistics = getStatistics(req.getParameter(STATISTICS));

                    if(statistics == null) response = getError(WRONG_STATISTICS, resp);
                    else {
                        String stringDate = req.getParameter(DATE);

                        if (dateMiddleware.checkDate(stringDate)) {
                            LocalDate date = LocalDate.parse(stringDate);

                            response = jsonParseService.writeValueAsBytes(userService.getHabitFulfillmentStatisticsByIdUser(idUser, statistics, idHabit, date));
                        }
                        else response = getError(WRONG_DATE, resp);
                    }
                }

            } else {

                Long idUser = Long.parseLong(pathInfo.substring(7));

                if (req.getQueryString() == null) {
                    response = jsonParseService.writeValueAsBytes(userService.getHabitsByIdUser(idUser));
                }
                else if (req.getQueryString().contains(STATUS)) {
                    Status status = getStatus(req.getParameter(STATUS));

                    if (status == null) response = getError(WRONG_STATUS, resp);
                    else response = jsonParseService.writeValueAsBytes(userService.getHabitsByStatusByIdUser(idUser, status));
                }
                else if (req.getQueryString().contains(DATE)) {
                    String stringDate = req.getParameter(DATE);

                    if (dateMiddleware.checkDate(stringDate)) {
                        LocalDate date = LocalDate.parse(stringDate);
                        response = jsonParseService.writeValueAsBytes(userService.getHabitsByLocalDateByIdUser(idUser, date));
                    }
                    else response = getError(WRONG_DATE, resp);

                }
                else response = getError(WRONG_REQUEST, resp);

            }

        } catch (JsonParseException | ModelNotFound e) {
            response = getError(WRONG_REQUEST, resp);
        }

        sendOkAndObject(resp, response);
    }

    /**
     * Операция сохранения привычки
     *
     * @param req  - информация получения запроса
     * @param resp - ответ на основе объекта
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String paramIdUser = req.getPathInfo();

        if (paramIdUser != null) {
            try {
                Long idUser = Long.parseLong(paramIdUser.substring(7));
                HabitRequest habitRequest = (HabitRequest) jsonParseService.readObject(req.getInputStream(), HabitRequest.class);

                Repeat repeat = getRepeat(habitRequest.getRepeat());

                if (repeat == null) sendOkAndObject(resp, getError(WRONG_REPEAT, resp));
                else {
                    userService.addHabitByIdUser(idUser, habitRequest);
                    sendOk(resp);
                }

            } catch (IOException | ModelNotFound e) {
                sendOkAndObject(resp, getError(WRONG_REQUEST, resp));
            }
        } else sendOkAndObject(resp, getError(WRONG_REQUEST, resp));


    }

    /**
     * Операция обновления привычки
     *
     * @param req  - информация получения запроса
     * @param resp - ответ на основе объекта
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {

        String pathInfo = req.getPathInfo();

        if (pathInfo != null) {
            try {
                String[] s = pathInfo.substring(1).split("/");

                Long idUser = Long.parseLong(s[0].substring(6));
                Long idHabit = Long.parseLong(s[1].substring(7));

                HabitRequest habitRequest = (HabitRequest) jsonParseService.readObject(req.getInputStream(), HabitRequest.class);

                if (habitRequest.getTitle() != null) {

                    userService.updateTitleByIdHabitByIdUser(idUser, idHabit, habitRequest.getTitle());
                    sendOk(resp);

                } else if (habitRequest.getDescription() != null) {

                    userService.updateDescriptionByIdHabitByIdUser(idUser, idHabit, habitRequest.getDescription());
                    sendOk(resp);

                } else if (habitRequest.getRepeat() != null) {

                    Repeat repeat = getRepeat(habitRequest.getRepeat());

                    if(repeat == null ) sendOkAndObject(resp, getError(WRONG_REPEAT, resp));
                    else {
                        userService.updateRepeatByIdHabitByIdUser(idUser, idHabit, repeat);
                        sendOk(resp);
                    }

                } else if (habitRequest.getStatus() != null) {
                    Status status = getStatus(habitRequest.getStatus());

                    if (status == null) sendOkAndObject(resp, getError(WRONG_STATUS, resp));
                    else {
                        userService.updateStatusByIdHabitByIdUser(idUser, idHabit, status);
                        sendOk(resp);
                    }

                } else if (habitRequest.getDoneDate() != null) {

                    if (habitRequest.getDoneDate().equals(YES)) {
                        userService.setDoneDatesHabitByIdUser(idUser, idHabit);
                        sendOk(resp);
                    }

                } else if (habitRequest.getPushTime() != null) {

                    if (habitRequest.getPushTime().equals(NO)) {
                        userService.switchOffPushNotificationByIdUser(idUser, idHabit);
                        sendOk(resp);
                    } else {

                        if (dateMiddleware.checkTime(habitRequest.getPushTime())) {

                            LocalTime time = LocalTime.parse(habitRequest.getPushTime());
                            userService.switchOnPushNotificationByIdUser(idUser, idHabit, time);
                            sendOk(resp);
                        } else sendOkAndObject(resp, getError(WRONG_TIME, resp));

                    }
                }

            } catch (IOException | ModelNotFound e) {
                sendOkAndObject(resp, getError(WRONG_REQUEST, resp));
            }
        } else sendOkAndObject(resp, getError(WRONG_REQUEST, resp));

    }

    /**
     * Операция удаления привычки
     *
     * @param req  - информация получения запроса
     * @param resp - ответ на основе объекта
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String pathInfo = req.getPathInfo();

        if (pathInfo != null) {
            String[] s = pathInfo.substring(1).split("/");

            Long idUser = Long.parseLong(s[0].substring(6));
            Long idHabit = Long.parseLong(s[1].substring(7));

            try {
                userService.deleteHabitByIdUser(idUser, idHabit);
                sendOk(resp);
            } catch (ModelNotFound e) {
                sendOkAndObject(resp, getError(WRONG_REQUEST, resp));
            }

        } else sendOkAndObject(resp, getError(WRONG_REQUEST, resp));
    }

    @SneakyThrows
    private void sendOk(HttpServletResponse resp) {
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    @SneakyThrows
    private void sendOkAndObject(HttpServletResponse resp, byte[] bytes) {
        sendOk(resp);
        resp.getOutputStream().write(bytes);
    }


    /**
     * Функция получения ошибки
     *
     * @param resp - ответ на основе объекта
     * @return возвращает массив байтов json
     */
    @SneakyThrows
    private byte[] getError(String error, HttpServletResponse resp) {
        return jsonParseService.writeValueAsBytes(new ErrorResponse(error, resp.getStatus()));
    }

    /**
     * Функция получения статуса привычки
     *
     * @param s - ввод
     * @return возвращает статус привычки
     */
    public static Status getStatus(String s) {
        switch (s) {
            case CREATE -> {
                return Status.CREATED;
            }
            case IN_PROGRESS -> {
                return Status.IN_PROGRESS;
            }
            case DONE -> {
                return Status.DONE;
            }
        }
        return null;
    }

    /**
     * Функция получения частоты повторения привычки
     *
     * @param s - ввод
     * @return возвращает частоты повторения привычки
     */
    public static Repeat getRepeat(String s) {
        switch (s) {
            case DAILY -> {
                return Repeat.DAILY;
            }
            case WEEKLY -> {
                return Repeat.WEEKLY;
            }
        }
        return null;
    }

    /**
     * Функция получения периода
     *
     * @param s - ввод
     * @return возвращает период
     */
    private Statistics getStatistics(String s) {
        switch (s) {
            case DAY -> {
                return Statistics.DAY;
            }
            case WEEK -> {
                return Statistics.WEEK;
            }
            case MONTH -> {
                return Statistics.MONTH;
            }
        }
        return null;
    }
}
