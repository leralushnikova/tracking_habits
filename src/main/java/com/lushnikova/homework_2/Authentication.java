package com.lushnikova.homework_2;

import com.lushnikova.homework_2.middleware.Middleware;
import com.lushnikova.homework_2.middleware.UserMiddleware;
import com.lushnikova.homework_2.controller.AdminController;
import com.lushnikova.homework_2.controller.Controller;
import com.lushnikova.homework_2.controller.UserController;
import com.lushnikova.homework_2.mapper.UserMapper;
import com.lushnikova.homework_2.repository.HabitRepository;
import com.lushnikova.homework_2.repository.UserRepository;
import com.lushnikova.homework_2.service.UserService;
import com.lushnikova.homework_2.service.impl.UserServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.lushnikova.homework_2.config.Environment.*;
import static com.lushnikova.homework_2.consts.ModesConsts.ADMIN_OR_USER;
import static com.lushnikova.homework_2.controller.UserController.scannerString;
import static com.lushnikova.homework_2.controller.UserController.wrongInput;

/**
 * Класс для аутентификации администратора или пользователя
 */
public class Authentication {
    /**
     * Поле преобразования пользователей
     */
    private final UserMapper userMapper = UserMapper.INSTANCE;

    private final Middleware middleware = new UserMiddleware();

    /**
     * Поле контроллера
     */
    Controller controller;

    private UserRepository userRepository;
    private UserService userService;
    private HabitRepository habitRepository;

    /**
     * Процедура инициализация контроллера
     */
    private void initializer() {

        System.out.println(ADMIN_OR_USER);

        String answer = scannerString();

        switch (answer) {
            case "1" -> controller = new AdminController(userRepository, userService, middleware);
            case "2" -> controller = new UserController(userRepository, habitRepository, userService, middleware);
            case "exit" -> {}
            default -> {
                wrongInput();
                initializer();
            }
        }
    }

    /**
     * Процедура запуска метода инициализации
     */
    public void start() {
        try (Connection connection = DriverManager.getConnection(getURL(), getUSER(), getPASSWORD())) {
            userRepository = new UserRepository(connection);
            habitRepository = new HabitRepository(connection);

            userService = new UserServiceImpl(userMapper, userRepository);

            this.initializer();
            while (controller != null) {
                controller.render();
                this.initializer();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
