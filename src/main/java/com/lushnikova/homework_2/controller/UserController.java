package com.lushnikova.homework_2.controller;

import com.lushnikova.homework_2.dto.request.UserRequest;
import com.lushnikova.homework_2.dto.response.UserResponse;
import com.lushnikova.homework_2.mapper.UserMapper;
import com.lushnikova.homework_2.middleware.Middleware;
import com.lushnikova.homework_2.middleware.UserMiddleware;
import com.lushnikova.homework_2.model.User;
import com.lushnikova.homework_2.reminder.ReminderService;
import com.lushnikova.homework_2.repository.HabitRepository;
import com.lushnikova.homework_2.repository.UserRepository;
import com.lushnikova.homework_2.service.UserService;
import com.lushnikova.homework_2.service.impl.UserServiceImpl;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import static com.lushnikova.homework_2.consts.ErrorConsts.WRONG_REQUEST;
import static com.lushnikova.homework_2.consts.ModesConsts.*;

/**
 * Класс Controller для пользователей
 */
public class UserController extends Controller {

    /**
     * Поле сервис пользователей
     */
    private UserService userService;

    /**
     * Поле репозиторий пользователей
     */
    private final UserRepository userRepository;

    /**
     * Поле инструмент проверки
     */
    private final Middleware userMiddleware;


    /**
     * Поле контроллер привычек
     */
    private final HabitController habitController;

    /**
     * Поле сервис по проверки уведомлений
     */
    final ReminderService reminderService;


    /**
     * Конструктор - создание нового объекта с определенными значениями
     *
     * @param userRepository - репозиторий пользователей
     */
    public UserController(UserRepository userRepository, HabitRepository habitRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMiddleware = new UserMiddleware();
        this.habitController = new HabitController(habitRepository);
        userService = new UserServiceImpl(userMapper, userRepository);
        reminderService = new ReminderService(habitRepository);
    }


    /**
     * Авторизация пользователя
     */
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
                    createUser();
                    return;
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
     *
     * @param idUser - id пользователя
     */
    public void modesForUsers(Long idUser) {
        while (true) {
            UserResponse userResponse;
            try {
                userResponse = getUser(idUser);

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
            } catch (SQLException e) {
                System.err.println(WRONG_REQUEST);
            }
        }
    }

    /**
     * Процедура создания нового пользователя
     *
     */
    public void createUser() {
        UserRequest userRequest = new UserRequest();
        System.out.println("Введите свое имя: ");
        userRequest.setName(scannerString());

        while (true) {
            String email = email();

            try {
                if (checkEmail(email) != null) throw new SQLException();
                else {
                    userRequest.setEmail(email);

                    while (true) {
                        System.out.println(MESSAGE_PASSWORD);

                        String password = password();

                        if (userMiddleware.checkPassword(password)) {
                            userRequest.setPassword(password);

                            userService.save(userRequest);
                            return;
                        }
                        else wrongInput();
                    }
                }
            } catch (SQLException e) {
                System.err.println("Данная почта уже существует");
            }
        }
    }

    /**
     * Функция получения пользователя после авторизации
     *
     * @return возвращение объекта пользователя
     */
    public UserResponse getUserAfterAuthentication() {
        while (true) {
            String email = email();

            try {
                Long idUserFromCheckEmail = checkEmail(email);

                if (idUserFromCheckEmail != null) {
                    UserResponse userResponse = getUser(idUserFromCheckEmail);

                    if (userResponse.getIsActive()) return recursionForPassword(idUserFromCheckEmail);
                    else System.out.println("Пользователь заблокирован!");

                } else throw new SQLException();

            } catch (NullPointerException | SQLException e) {
                System.err.println("Данный пользователь не зарегистрирован");
            }
        }
    }

    /**
     * Функция получения пользователя {@link UserService#findById(Long)}
     *
     * @param id - id пользователя
     * @return возвращение объекта пользователя
     * @throws SQLException
     */
    public UserResponse getUser(Long id) throws SQLException {
        return userService.findById(id);
    }

    /**
     * Получение информации о пользователе
     *
     * @param id - id пользователя
     */
    public void readUser(Long id) {
        UserResponse userResponse;
        try {
            userResponse = getUser(id);
            System.out.println("Ваши данные: ");
            System.out.println("Имя =  " + userResponse.getName());
            System.out.println("Почта =  " + userResponse.getEmail());
            System.out.println("Статус =  " + userResponse.getIsActive());
            System.out.println("----------------------------------------------");
        } catch (SQLException e) {
            System.err.println(WRONG_REQUEST);
        }
    }


    /**
     * Процедура редактирования пользователя
     *
     * @param idUser - id пользователя
     */
    public void editUser(Long idUser) {
        try {
            System.out.println(EDIT_USER);

            String answer = scannerString();
            switch (answer) {
                case "1" -> {
                    System.out.println("Введите новое имя: ");
                    String name = scannerString();
                    if (!name.isBlank() && !name.isEmpty()) {

                        userService.updateName(idUser, name);
                        editUser(idUser);
                    } else wrongInput();
                }
                case "2" -> {
                    String email = email();

                    if (!email.isBlank() && !email.isEmpty()) {

                        userService.updateEmail(idUser, email);
                        editUser(idUser);

                    } else wrongInput();
                }
                case "3" -> {
                    String password = password();
                    if (userMiddleware.checkPassword(password) && !password.isEmpty() && !password.isBlank()) {


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
        } catch (SQLException e) {
            System.err.println(WRONG_REQUEST);
        }
    }

    /**
     * Функция получения пользователя по id при проверке пароля,
     * если пароль не совпадает, то предлагается переустановить пароль
     *
     * @param idUserFromCheckEmail - id пользователя
     * @return возвращает объект пользователя
     */
    private UserResponse recursionForPassword(Long idUserFromCheckEmail) throws SQLException{

        String password = password();

        UserResponse userFromService = getUser(idUserFromCheckEmail);

        User userFromRepository = userRepository.findById(idUserFromCheckEmail);

        if (userMiddleware.checkPassword(password, userFromRepository)) {
            return userFromService;
        } else {

            System.out.println(RECOVER_PASSWORD);
            String answer = scannerString();

            switch (answer) {
                case "y" -> {
                    System.out.println("Введите новый пароль: ");
                    String newPassword = scannerString();
                    userService.updatePassword(idUserFromCheckEmail, newPassword);
                    return getUser(idUserFromCheckEmail);
                }
                case "n" -> recursionForPassword(idUserFromCheckEmail);
                default -> {
                    wrongInput();
                    recursionForPassword(idUserFromCheckEmail);
                }
            }
        }
        return userFromService;
    }

    /**
     * Введение почты в консоль
     *
     * @return возвращение почты
     */
    public static String email() {
        System.out.println("Введите свою почту: ");
        return scannerString();
    }

    /**
     * Введение пароля в консоль
     *
     * @return возвращение пароля
     */
    public static String password() {
        System.out.println("Введите свой пароль: ");
        return scannerString();
    }

    /**
     * Ввод в консоль
     *
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
        System.err.println(WRONG_INPUT);

    }

    /**
     * Функция получения списка пользователей {@link UserService#findAll()}
     *
     * @return возвращение пользователей
     */
    private List<UserResponse> listUsers() throws SQLException {
        return userService.findAll();
    }

    /**
     * Функция получения id пользователя при совпадении почты из списка администраторов{@link UserController#listUsers}
     *
     * @param email - почта при вводе
     * @return возвращает уникальный идентификатор пользователя
     */
    private Long checkEmail(String email) throws SQLException {
        for (UserResponse userResponse : listUsers()) {
            if (userMiddleware.checkEmail(email, userResponse)) {
                return userResponse.getId();
            }
        }
        return null;
    }

}
