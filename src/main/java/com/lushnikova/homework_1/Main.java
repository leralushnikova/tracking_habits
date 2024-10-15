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


public class Main {


    private static final UserMapper USER_MAPPER = UserMapper.INSTANCE;
    private static final AdminRepository adminRepository = AdminRepository.getInstance();
    private static final DateMiddleware dateMiddleware = new DateMiddleware();
    private static final Middleware userMiddleware = new UserMiddleware();
    private static final Middleware adminMiddleware = new AdminMiddleware();
    private static final UserRepository userRepository = UserRepository.getInstance();
    private static final UserService userService = new UserServiceImpl(USER_MAPPER, userRepository);
    private static final UserController userController = new UserController(userService, userMiddleware);
    private static final HabitController habitController = new HabitController(userService, dateMiddleware);
    private static final AdminService adminService = new AdminServiceImpl(userService, adminRepository);
    private static final AdminController adminController = new AdminController(adminService, adminMiddleware);
    static final ReminderService reminderService = new ReminderService(userRepository);

    public static void main(String[] args) throws ModelNotFound {
        adminOrUser();
    }

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

    //вход для пользователей
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
                    userResponse = userController.createPerson();
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

    //вход для администраторов
    public static void enterAdmin() throws ModelNotFound {
        adminController.getAdminAfterAuthentication();
        adminController.modesForUsers();
    }

    public static void modesForUsers(UUID idPerson) throws ModelNotFound {
        while (true) {
            UserResponse userResponse = userController.getPerson(idPerson);

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
                    case "1" -> userController.editPerson(idPerson);

                    case "2" -> userController.readPerson(idPerson);

                    case "3" -> habitController.crudHabits(idPerson);

                    case "4" -> habitController.readHabits(idPerson);

                    case "5" -> habitController.getHabitFulfillmentStatisticsByIdPerson(idPerson);

                    case "6" -> habitController.getPercentSuccessHabitsByIdPerson(idPerson);

                    case "7" -> habitController.reportHabitByIdPerson(idPerson);

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
