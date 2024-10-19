package com.lushnikova.homework_1.controller;

import com.lushnikova.homework_1.dto.req.UserRequest;
import com.lushnikova.homework_1.dto.resp.UserResponse;
import com.lushnikova.homework_1.middleware.Middleware;
import com.lushnikova.homework_1.model.User;
import com.lushnikova.homework_1.repository.UserRepository;
import com.lushnikova.homework_1.service.UserService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import static com.lushnikova.homework_1.consts.ModesConsts.*;

/**
 * Класс Controller для пользователей
 */
public class UserController {

    /** Поле сервис пользователей*/
    private final UserService userService;

    /** Поле репозиторий пользователей*/
    private final UserRepository userRepository;

    /** Поле инструмент проверки*/
    private final Middleware userMiddleware;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param userService - сервис пользователей
     * @param userRepository - репозиторий пользователей
     * @param userMiddleware - инструмент проверки*
     */
    public UserController(UserService userService, UserRepository userRepository, Middleware userMiddleware) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userMiddleware = userMiddleware;
    }

    //регистрация нового пользователя

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

    //рекрусивный метод ввода пароля
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
