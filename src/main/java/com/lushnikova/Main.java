package com.lushnikova;

import com.lushnikova.controller.HabitController;
import com.lushnikova.controller.UserController;
import com.lushnikova.dto.resp.UserResponse;
import com.lushnikova.exception.ModelNotFound;
import com.lushnikova.mapper_mapstruct.UserMapper;
import com.lushnikova.middleware.DateMiddleware;
import com.lushnikova.middleware.UserMiddleware;
import com.lushnikova.repository.UserRepository;
import com.lushnikova.service.UserService;
import com.lushnikova.service.impl.UserServiceImpl;

import java.util.UUID;

import static com.lushnikova.controller.UserController.scannerString;
import static com.lushnikova.controller.UserController.wrongInput;


public class Main {


    private static final UserMapper USER_MAPPER = UserMapper.INSTANCE;
    private static final DateMiddleware dateMiddleware = new DateMiddleware();
    private static final UserMiddleware USER_MIDDLEWARE = new UserMiddleware();
    private static final UserRepository USER_REPOSITORY = UserRepository.getInstance();
    private static final UserService USER_SERVICE = new UserServiceImpl(USER_MAPPER, USER_REPOSITORY);
    private static final UserController USER_CONTROLLER = new UserController(USER_SERVICE, USER_MIDDLEWARE);
    private static final HabitController habitController = new HabitController(USER_SERVICE, dateMiddleware);

    public static void main(String[] args) throws ModelNotFound {

        UserResponse userResponse;
        while (true) {
            System.out.println("Вы хотите войти(in), зарегистрироваться(up) или выйти(exit)?[in/up/exit]");
            String input = scannerString();

            switch (input) {
                case ("in") -> {
                    userResponse = USER_CONTROLLER.getPersonAfterAuthentication();
                    modesForUsers(userResponse.getId());
                }
                case ("up") -> {
                    userResponse = USER_CONTROLLER.createPerson();
                    modesForUsers(userResponse.getId());
                }
                case ("exit") -> {
                    return;
                }

                default -> wrongInput();
            }
        }
    }

    public static void modesForUsers(UUID idPerson) throws ModelNotFound {
        while (true) {
            UserResponse userResponse = USER_CONTROLLER.getPerson(idPerson);
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
                    case "1" -> USER_CONTROLLER.editPerson(idPerson);

                    case "2" -> USER_CONTROLLER.readPerson(idPerson);

                    case "3" -> habitController.crudHabits(idPerson);

                    case "4" -> habitController.readHabits(idPerson);

                    case "5" -> habitController.getHabitFulfillmentStatisticsByIdPerson(idPerson);

                    case "6" -> habitController.getPercentSuccessHabitsByIdPerson(idPerson);

                    case "7" -> habitController.reportHabitByIdPerson(idPerson);

                    case "exit" -> {
                        return;
                    }
                    default -> wrongInput();
                }
            } else return;
        }
    }


}
