package com.lushnikova.homework_3.servlet;

import com.lushnikova.homework_3.annotations.Loggable;
import com.lushnikova.homework_3.middleware.AdminMiddleware;
import com.lushnikova.homework_3.middleware.Middleware;
import com.lushnikova.homework_3.dto.response.UserResponse;
import com.lushnikova.homework_3.exception.ModelNotFound;
import com.lushnikova.homework_3.dto.request.AdminRequest;
import com.lushnikova.homework_3.dto.response.ErrorResponse;
import com.lushnikova.homework_3.service.impl.AdminServiceImpl;
import com.lushnikova.homework_3.service.AdminService;
import com.lushnikova.homework_3.service.JsonParseService;
import com.lushnikova.homework_3.service.impl.JsonParseServiceImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;

import static com.lushnikova.homework_3.consts.ErrorConsts.*;
import static com.lushnikova.homework_3.consts.StringConsts.ID_ADMIN;
import static com.lushnikova.homework_3.consts.StringConsts.ID_USER;
import static com.lushnikova.homework_3.consts.WebConsts.ADMINS_PATH;
import static com.lushnikova.homework_3.consts.WebConsts.USERS_PATH;

/**
 * Класс Servlet для работы с администраторами
 */
@Loggable
@WebServlet(ADMINS_PATH)
public class AdminServlet extends HttpServlet {

    /** Поле сериализации/десериализации Java-объектов */
    private final JsonParseService jsonParseService;

    /** Поле сервис администраторов */
    private final AdminService adminService;

    /** Поле инструмент проверки*/
    private final Middleware middleware;

    /**
     * Конструктор - создание нового объекта
     */
    public AdminServlet() {
        jsonParseService = new JsonParseServiceImpl();
        adminService = new AdminServiceImpl();
        middleware = new AdminMiddleware(adminService);
    }

    /**
     * Операция получения администратора или администраторов
     * @param req - информация получения запроса
     * @param resp - ответ на основе объекта
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        String paramId = req.getPathInfo();

        byte[] response;

        try {

            if (paramId == null) {
                response = jsonParseService.writeValueAsBytes(adminService.findAll());
            }
            else if (paramId.contains(ID_ADMIN)) {
                Long id = Long.parseLong(paramId.substring(8));
                response = jsonParseService.writeValueAsBytes(adminService.findById(id));
            }
            else if (paramId.contains(USERS_PATH.substring(0, USERS_PATH.length() - 2))) {
                response = jsonParseService.writeValueAsBytes(adminService.findAllUsers());
            }
            else response = getError(WRONG_REQUEST, resp);

        } catch (Exception e) {
            response = getError(WRONG_REQUEST, resp);
        }

        sendOkAndObject(resp, response);
    }

    /**
     * Операция сохранения администратора
     * @param req - информация получения запроса
     * @param resp - ответ на основе объекта
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        AdminRequest adminRequest;

        try {
            adminRequest = (AdminRequest) jsonParseService.readObject(req.getInputStream(), AdminRequest.class);

            if (!middleware.checkEmail(adminRequest)) {

                if(middleware.checkPassword(adminRequest.getPassword())) {
                    adminService.save(adminRequest);
                    sendOk(resp);
                }
                else sendOkAndObject(resp, getError(WRONG_PASSWORD, resp));

            }
            else sendOkAndObject(resp, getError(ADMIN_EXISTS, resp));

        } catch (IOException e) {
            sendOkAndObject(resp, getError(WRONG_REQUEST, resp));
        }

    }


    /**
     * Операция обновления администратора
     * @param req - информация получения запроса
     * @param resp - ответ на основе объекта
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp){

        String paramId = req.getPathInfo();

        try {

            if (paramId.contains(ID_ADMIN)) {
                Long idAdmin = Long.parseLong(paramId.substring(8));

                AdminRequest adminRequest = (AdminRequest) jsonParseService.readObject(req.getInputStream(), AdminRequest.class);

                if(middleware.checkPassword(adminRequest.getPassword())) {
                    adminService.updatePassword(idAdmin, adminRequest.getPassword());
                    sendOk(resp);
                }
                else sendOkAndObject(resp, getError(WRONG_PASSWORD, resp));
            }
            else if (paramId.contains(ID_USER)) {

                Long idUser = Long.parseLong(paramId.substring(7));

                UserResponse userResponse = (UserResponse) jsonParseService.readObject(req.getInputStream(), UserResponse.class);

                if (userResponse.isActive() != null) {
                    adminService.blockByIdUser(idUser, userResponse.isActive());
                    sendOk(resp);
                }
            }
            else sendOkAndObject(resp, getError(WRONG_REQUEST, resp));

        } catch (ModelNotFound | IOException e) {
            sendOkAndObject(resp, getError(WRONG_REQUEST, resp));
        }

    }

    /**
     * Операция удаления администратора
     * @param req - информация получения запроса
     * @param resp - ответ на основе объекта
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        String paramId = req.getPathInfo();

        try {
            if (paramId.contains(ID_ADMIN)) {
                Long idAdmin = Long.parseLong(paramId.substring(8));
                adminService.delete(idAdmin);
                sendOk(resp);
            }
            else if (paramId.contains(ID_USER)) {
                Long idUser = Long.parseLong(paramId.substring(7));
                adminService.deleteUser(idUser);
                sendOk(resp);
            }
            else sendOkAndObject(resp, getError(WRONG_REQUEST, resp));

        } catch (ModelNotFound e) {
            sendOkAndObject(resp, getError(WRONG_REQUEST, resp));
        }
    }

    /**
     * Функция установки ответа на запрос
     * @param resp ответ на основе объекта
     */
    @SneakyThrows
    private void sendOk(HttpServletResponse resp){
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
    }

    /**
     * Функция вывода ответа в формате json
     * @param resp ответ на основе объекта
     */
    @SneakyThrows
    private void sendOkAndObject(HttpServletResponse resp, byte[] bytes){
        sendOk(resp);
        resp.getOutputStream().write(bytes);
    }

    /** Функция формирования ошибки*/
    @SneakyThrows
    private byte[] getError(String error, HttpServletResponse resp){
        return jsonParseService.writeValueAsBytes(new ErrorResponse(error, resp.getStatus()));
    }

}
