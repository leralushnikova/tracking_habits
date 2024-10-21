package com.lushnikova.homework_1;


import com.lushnikova.homework_1.controller.AdminController;
import com.lushnikova.homework_1.controller.Controller;
import com.lushnikova.homework_1.controller.UserController;
import com.lushnikova.homework_1.mapper_mapstruct.UserMapper;
import com.lushnikova.homework_1.repository.UserRepository;

import static com.lushnikova.homework_1.consts.ModesConsts.ADMIN_OR_USER;
import static com.lushnikova.homework_1.controller.UserController.scannerString;
import static com.lushnikova.homework_1.controller.UserController.wrongInput;

/**
 * Класс для аутентификации администратора или пользователя
 */
public class Authentication {
    /**
     * Поле преобразования пользователей
     */
    private final UserMapper userMapper = UserMapper.INSTANCE;

    /**
     * Поле репозиторий пользователей
     */
    private final UserRepository userRepository = UserRepository.getInstance();

    /**
     * Поле контроллера
     */
    Controller controller;

    /**
     * Процедура инициализация контроллера
     */
    private void initializer() {
        while (true) {
            System.out.println(ADMIN_OR_USER);

            String answer = scannerString();

            switch (answer) {
                case "1" -> {
                    controller = new AdminController(userRepository, userMapper);
                    return;
                }
                case "2" -> {
                    controller = new UserController(userRepository, userMapper);
                    return;
                }
                case "exit" -> {
                    return;
                }
                default -> {
                    wrongInput();
                    initializer();
                }
            }
        }
    }

    /**
     * Процедура запуска метода инициализации
     */
    public void main() {
        this.initializer();
        if (controller != null) {
            controller.render();
            this.initializer();
        }
    }
}
