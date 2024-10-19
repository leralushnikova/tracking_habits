package com.lushnikova.homework_1.controller;

import com.lushnikova.homework_1.dto.req.UserRequest;
import com.lushnikova.homework_1.dto.resp.UserResponse;
import com.lushnikova.homework_1.mapper_mapstruct.UserMapper;
import com.lushnikova.homework_1.middleware.Middleware;
import com.lushnikova.homework_1.middleware.UserMiddleware;
import com.lushnikova.homework_1.model.User;
import com.lushnikova.homework_1.reminder.ReminderService;
import com.lushnikova.homework_1.repository.UserRepository;
import com.lushnikova.homework_1.service.UserService;
import com.lushnikova.homework_1.service.impl.UserServiceImpl;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static com.lushnikova.homework_1.consts.ModesConsts.*;

/**
 * Класс Controller для пользователей
 */
public class UserController extends Controller{

    /** Поле сервис пользователей*/
    private UserService userService;

    /** Поле репозиторий пользователей*/
    private final UserRepository userRepository;

    /** Поле инструмент проверки*/
    private final Middleware userMiddleware;

    /** Поле преобразования пользователей*/
    private final UserMapper userMapper;

    /** Поле контроллер привычек*/
    private HabitController habitController;

    /** Поле сервис по проверки уведомлений*/
    final ReminderService reminderService;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param userRepository - репозиторий пользователей
     */
    public UserController(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMiddleware = new UserMiddleware();
        this.userMapper = userMapper;
        reminderService = new ReminderService(userRepository);
    }

    /**
     * Процедура создания сервиса пользователя
     */
    @Override
    public void createService() {
        userService = new UserServiceImpl(userMapper, userRepository);
        habitController = new HabitController(userService);
    }

    @Override
    void enter() {
        UserResponse userResponse;
        while (true) {
            System.out.println(ENTER_USER);
            String input = scannerString();

            switch (input) {
                case ("in") -> {
                    userResponse = getUserAfterAuthentication();
                    reminderService.start(userResponse.getId());
                    modesForUsers(userResponse.getId());
                }
                case ("up") -> {
                    userResponse = createUser();
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
     * Режим управления либо данными пользователя или его привычками
     * @param idUser - id пользователя
     */
    public void modesForUsers(UUID idUser){
        while (true) {
            UserResponse userResponse = getUser(idUser);

            if (userResponse != null) {
                System.out.println(MODES_FOR_USER);

                String answer = scannerString();

                switch (answer) {
                    case "1" -> editUser(idUser);

                    case "2" -> readUser(idUser);

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

    /**
     * Функция получения нового пользователя
     * @return возвращение объекта пользователя
     */
    public UserResponse createUser() {
        UserRequest userRequest = new UserRequest();
        System.out.println("Введите свое имя: ");
        userRequest.setName(scannerString());

        while (true) {
            String email = email();

            if (checkEmail(email) != null) System.out.println("Данная почта уже существует");
            else {
                userRequest.setEmail(email);

                while (true) {
                    System.out.println(MESSAGE_PASSWORD);

                    String password = password();

                    if (userMiddleware.checkPassword(password)) {
                        userRequest.setPassword(password);

                        return userService.save(userRequest);
                    } else wrongInput();
                }
            }
        }
    }

    /**
     * Функция получения пользователя после авторизации
     * @return возвращение объекта пользователя
     */
    public UserResponse getUserAfterAuthentication() {
        while (true){
            String email = email();
            UUID idUserFromCheckEmail = checkEmail(email);

            UserResponse userResponse = userService.findById(idUserFromCheckEmail);
            if(userResponse == null) System.out.println("Данный пользователь не зарегистрирован");
            else {
                if (userResponse.isActive()) {
                    if (idUserFromCheckEmail != null) {
                        return recursionForPassword(idUserFromCheckEmail);
                    } else System.out.println("Данный пользователь не зарегистрирован");
                } else System.out.println("Пользователь заблокирован!");
            }
        }
    }

    /**
     * Функция получения пользователя {@link UserService#findById(UUID)}
     * @param id - id пользователя
     * @return возвращение объекта пользователя
     */
    public UserResponse getUser(UUID id) {
        return userService.findById(id);
    }

    /**
     * Получение информации о пользователе
     * @param id - id пользователя
     */
    public void readUser(UUID id) {
        UserResponse userResponse = userService.findById(id);
        System.out.println("Ваши данные: ");
        System.out.println("Имя =  " + userResponse.getName());
        System.out.println("Почта =  " + userResponse.getEmail());
        System.out.println("Статус =  " + userResponse.isActive());
        System.out.println("----------------------------------------------");
    }


    /**
     * Процедура редактирования пользователя
     * @param idUser - id пользователя
     */
    public void editUser(UUID idUser) {
        System.out.println(EDIT_USER);

        String answer = scannerString();
        switch (answer) {
            case "1" -> {
                System.out.println("Введите новое имя: ");
                String name = scannerString();
                if (!name.isBlank() && name.isEmpty()) {
                    userService.updateName(idUser, name);
                    editUser(idUser);
                } else wrongInput();
            }
            case "2" -> {
                String email = email();
                if (!email.isBlank() && email.isEmpty()) {
                    userService.updateEmail(idUser, email);
                    editUser(idUser);
                } else wrongInput();
            }
            case "3" -> {
                String password = password();
                if(userMiddleware.checkPassword(password) && !password.isEmpty() && !password.isBlank()){
                    userService.updatePassword(idUser, password);
                    editUser(idUser);
                } else wrongInput();

            }
            case "4" -> {
                userService.delete(idUser);
                System.out.println("Ваш профиль удален");
            }

            case "exit" -> {
            }
            default -> wrongInput();
        }
    }

    /**
     * Функция получения пользователя по id при проверке пароля,
     * если пароль не совпадает, то предлагается переустановить пароль
     * @param idUserFromCheckEmail - id пользователя
     * @return возвращает объект пользователя
     */
    private UserResponse recursionForPassword(UUID idUserFromCheckEmail){

        String password = password();

        UserResponse UserFromService = userService.findById(idUserFromCheckEmail);

        User userFromRepository = userRepository.findById(idUserFromCheckEmail);
        if (userMiddleware.checkPassword(password, userFromRepository)) {
            return UserFromService;
        } else {
            System.out.println(RECOVER_PASSWORD);
            String answer = scannerString();

            switch (answer) {
                case "y" -> {
                    System.out.println("Введите новый пароль: ");
                    String newPassword = scannerString();
                    userService.updatePassword(idUserFromCheckEmail, newPassword);
                    return userService.findById(idUserFromCheckEmail);
                }
                case "n" -> recursionForPassword(idUserFromCheckEmail);
                default -> wrongInput();
            }
        }
        return UserFromService;
    }

    /**
     * Введение почты в консоль
     * @return возвращение почты
     */
    public static String email() {
        System.out.println("Введите свою почту: ");
        return scannerString();
    }

    /**
     * Введение пароля в консоль
     * @return возвращение пароля
     */
    public static String password() {
        System.out.println("Введите свой пароль: ");
        return scannerString();
    }

    /**
     * Ввод в консоль
     * @return возвращение строки
     */
    public static String scannerString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Ответ при неправильном вводе в консоль
     */
    public static void wrongInput() {
        System.out.println(WRONG_INPUT);

    }

    /**
     * Функция получения списка пользователей {@link UserService#findAll()}
     * @return возвращение пользователей
     */
    private List<UserResponse> listUsers() {
        return userService.findAll();
    }

    /**
     * Функция получения id пользователя при совпадении почты из списка администраторов{@link UserController#listUsers}
     * @param email - почта при вводе
     * @return возвращает уникальный идентификатор пользователя
     */
    private UUID checkEmail(String email){
        for (UserResponse userResponse : listUsers()) {
            if (userMiddleware.checkEmail(email, userResponse)) {
                return userResponse.getId();
            }
        }
        return null;
    }

}
