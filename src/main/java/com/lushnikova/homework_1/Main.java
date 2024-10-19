package com.lushnikova.homework_1;

import com.lushnikova.homework_1.controller.AdminController;
import com.lushnikova.homework_1.controller.HabitController;
import com.lushnikova.homework_1.controller.UserController;
import com.lushnikova.homework_1.dto.resp.UserResponse;
import com.lushnikova.homework_1.exception.ModelNotFound;
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

import static com.lushnikova.homework_1.controller.UserController.scannerString;
import static com.lushnikova.homework_1.controller.UserController.wrongInput;

/**
 * Класс запуска приложений
 */
public class Main {
    /** Поле преобразования пользователей*/
    private static final UserMapper userMapper = UserMapper.INSTANCE;

    /** Поле сервис администраторов*/
    private static final AdminRepository adminRepository = AdminRepository.getInstance();

    /** Поле инструмент проверки дат*/
    private static final DateMiddleware dateMiddleware = new DateMiddleware();

    /** Поле инструмент проверки для пользователя*/
    private static final Middleware userMiddleware = new UserMiddleware();

    /** Поле инструмент проверки для администратора*/
    private static final Middleware adminMiddleware = new AdminMiddleware();

    /** Поле репозиторий пользователей*/
    private static final UserRepository userRepository = UserRepository.getInstance();

    /** Поле сервис пользователей*/
    private static final UserService userService = new UserServiceImpl(userMapper, userRepository);

    /** Поле контроллер пользователей*/
    private static final UserController userController = new UserController(userService, userMiddleware);

    /** Поле контроллер привычек*/
    private static final HabitController habitController = new HabitController(userService, dateMiddleware);

    /** Поле сервис администраторов*/
    private static final AdminService adminService = new AdminServiceImpl(userService, adminRepository);

    /** Поле контроллер администраторов*/
    private static final AdminController adminController = new AdminController(adminService, adminMiddleware);

    /** Поле сервис по проверки уведомлений*/
    static final ReminderService reminderService = new ReminderService(userRepository);

    /**
     * Здесь точка старта приложения
     * @throws ModelNotFound
     */
    public static void main(String[] args) throws ModelNotFound {
        adminOrUser();
    }

    /**
     * Вход в приложение либо как администратор, либо как пользователь
     * @throws ModelNotFound
     */
    public static void adminOrUser() throws ModelNotFound {
        while (true) {
            System.out.println("Вы хотите войти как:");
            System.out.println("1 - администратор");
            System.out.println("2 - пользователь");
            System.out.println("exit - выход");

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
     * @throws ModelNotFound
     */
    public static void enterUser() throws ModelNotFound {
        UserResponse userResponse;
        while (true) {
            System.out.println("Вы хотите войти(in), зарегистрироваться(up) или выйти(exit)?[in/up/exit]");
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
     * @throws ModelNotFound
     */
    public static void enterAdmin() throws ModelNotFound {
        adminController.getAdminAfterAuthentication();
        adminController.modesForUsers();
    }

    /**
     * Режим управления либо данными пользователя или его привычками
     * @param idUser - id пользователя
     * @throws ModelNotFound
     */
    public static void modesForUsers(UUID idUser) throws ModelNotFound {
        while (true) {
            UserResponse userResponse = userController.getUser(idUser);

            if (userResponse != null) {
                System.out.println("Выберите режим: ");
                System.out.println("1 - Редактировать данные пользователя");
                System.out.println("2 - Просмотр данных пользователя");
                System.out.println("3 - Управления режимами привычек");
                System.out.println("4 - Просмотр привычек");
                System.out.println("5 - Статистика привычек");
                System.out.println("6 - Процент успешного выполнения привычек");
                System.out.println("7 - Отчет по привычке");
                System.out.println("exit - Выход");

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
