package com.lushnikova.homework_1.controller;

import com.lushnikova.homework_1.dto.resp.UserResponse;
import com.lushnikova.homework_1.mapper_mapstruct.UserMapper;
import com.lushnikova.homework_1.middleware.AdminMiddleware;
import com.lushnikova.homework_1.middleware.DateMiddleware;
import com.lushnikova.homework_1.middleware.Middleware;
import com.lushnikova.homework_1.middleware.UserMiddleware;
import com.lushnikova.homework_1.reminder.ReminderService;
import com.lushnikova.homework_1.repository.AdminRepository;
import com.lushnikova.homework_1.repository.UserRepository;
import com.lushnikova.homework_1.service.AdminService;
import com.lushnikova.homework_1.service.UserService;
import com.lushnikova.homework_1.service.impl.AdminServiceImpl;
import com.lushnikova.homework_1.service.impl.UserServiceImpl;

import java.util.UUID;

import static com.lushnikova.homework_1.consts.ModesConsts.*;
import static com.lushnikova.homework_1.controller.UserController.scannerString;
import static com.lushnikova.homework_1.controller.UserController.wrongInput;

/**
 * Класс для аутентификации админа или пользователя
 */
public class AuthenticationController {

    /** Поле преобразования пользователей*/
    private final UserMapper userMapper = UserMapper.INSTANCE;

    /** Поле репозиторий администраторов*/
    private final AdminRepository adminRepository = AdminRepository.getInstance();

    /** Поле инструмент проверки дат*/
    private final DateMiddleware dateMiddleware = new DateMiddleware();

    /** Поле инструмент проверки для пользователя*/
    private final Middleware userMiddleware = new UserMiddleware();

    /** Поле инструмент проверки для администратора*/
    private final Middleware adminMiddleware = new AdminMiddleware();

    /** Поле репозиторий пользователей*/
    private final UserRepository userRepository = UserRepository.getInstance();

    /** Поле сервис пользователей*/
    private final UserService userService = new UserServiceImpl(userMapper, userRepository);

    /** Поле контроллер пользователей*/
    private final UserController userController = new UserController(userService, userRepository, userMiddleware);

    /** Поле контроллер привычек*/
    private final HabitController habitController = new HabitController(userService, dateMiddleware);

    /** Поле сервис администраторов*/
    private final AdminService adminService = new AdminServiceImpl(userService, adminRepository);

    /** Поле контроллер администраторов*/
    private final AdminController adminController = new AdminController(adminService, adminRepository, adminMiddleware);

    /** Поле сервис по проверки уведомлений*/
    final ReminderService reminderService = new ReminderService(userRepository);


    /**
     * Пустой конструктор для создания нового объекта
     */
    public AuthenticationController() {
    }

    /**
     * Вход в приложение либо как администратор, либо как пользователь
     */
    public void adminOrUser(){
        while (true) {
            System.out.println(ADMIN_OR_USER);

            String answer = scannerString();

            switch (answer) {
                case "1" -> enterAdmin();
                case "2" -> enterUser();
                case "exit" -> {return;}
                default -> wrongInput();
            }
        }

    }

    /**
     * Регистрация или авторизация пользователей
     */
    public void enterUser(){
        UserResponse userResponse;
        while (true) {
            System.out.println(ENTER_USER);
            String input = scannerString();

            switch (input) {
                case ("in") -> {
                    userResponse = userController.getUserAfterAuthentication();
                    reminderService.start(userResponse.getId());
                    modesForUsers(userResponse.getId());
                }
                case ("up") -> {
                    userResponse = userController.createUser();
                    reminderService.start(userResponse.getId());
                    modesForUsers(userResponse.getId());
                }
                case ("exit") -> {
                    return;
                }

                default -> wrongInput();
            }
        }
    }

    /**
     * Авторизация администратора
     */
    public void enterAdmin(){
        adminController.getAdminAfterAuthentication();
        adminController.modesForUsers();
    }

    /**
     * Режим управления либо данными пользователя или его привычками
     * @param idUser - id пользователя
     */
    public void modesForUsers(UUID idUser){
        while (true) {
            UserResponse userResponse = userController.getUser(idUser);

            if (userResponse != null) {
                System.out.println(MODES_FOR_USER);

                String answer = scannerString();

                switch (answer) {
                    case "1" -> userController.editUser(idUser);

                    case "2" -> userController.readUser(idUser);

                    case "3" -> habitController.crudHabits(idUser);

                    case "4" -> habitController.readHabits(idUser);

                    case "5" -> habitController.getHabitFulfillmentStatisticsByIdUser(idUser);

                    case "6" -> habitController.getPercentSuccessHabitsByIdUser(idUser);

                    case "7" -> habitController.reportHabitByIdUser(idUser);

                    case "exit" -> {
                        reminderService.stop();
                        return;
                    }
                    default -> wrongInput();
                }
            } else return;
        }
    }
}
