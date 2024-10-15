package com.lushnikova.homework_1.controller;

import com.lushnikova.homework_1.dto.req.UserRequest;
import com.lushnikova.homework_1.dto.resp.UserResponse;
import com.lushnikova.homework_1.middleware.Middleware;
import com.lushnikova.homework_1.service.UserService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class UserController {

    private final UserService userService;
    private final Middleware userMiddleware;


    public UserController(UserService userService, Middleware userMiddleware) {
        this.userService = userService;
        this.userMiddleware = userMiddleware;
    }

    //регистрация нового пользователя
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
                    System.out.println("Пароль должен содержать 8 символов латинского алфавита, " +
                            "минимум одну заглавную букву, " +
                            "одну маленькую букву либо спец. символ, либо цифру.");
                    String password = password();

                    if (userMiddleware.checkPassword(password)) {
                        userRequest.setPassword(password);

                        return userService.save(userRequest);
                    } else wrongInput();
                }
            }
        }
    }

    //авторизация пользователя
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

    // получение пользователя
    public UserResponse getUser(UUID id) {
        return userService.findById(id);
    }

    // информация о пользователе
    public void readUser(UUID id) {
        UserResponse userResponse = userService.findById(id);
        System.out.println("Ваши данные: ");
        System.out.println("Имя =  " + userResponse.getName());
        System.out.println("Почта =  " + userResponse.getEmail());
        System.out.println("Пароль =  " + userResponse.getPassword());
        System.out.println("Статус =  " + userResponse.isActive());
        System.out.println("----------------------------------------------");
    }


    //редактирование данных пользователя
    public void editUser(UUID idUser) {
        System.out.println("Вы хотите изменить:");
        System.out.println("1 - имя");
        System.out.println("2 - email");
        System.out.println("3 - пароль");
        System.out.println("4 - удалить профиль");
        System.out.println("5 - выход из режима редактирования пользователя");

        String answer = scannerString();
        switch (answer) {
            case "1" -> {
                System.out.println("Введите новое имя: ");
                String name = scannerString();
                userService.updateName(idUser, name);
                editUser(idUser);
            }
            case "2" -> {
                String email = email();
                userService.updateEmail(idUser, email);
                editUser(idUser);
            }
            case "3" -> {
                String password = password();
                if(userMiddleware.checkPassword(password)){
                    userService.updatePassword(idUser, password);
                    editUser(idUser);
                } else wrongInput();

            }
            case "4" -> {
                userService.delete(idUser);
                System.out.println("Ваш профиль удален");
            }

            case "5" -> {
            }
            default -> wrongInput();
        }
    }

    //рекрусивный метод ввода пароля
    private UserResponse recursionForPassword(UUID idUserFromCheckEmail){

        String password = password();

        UserResponse UserFromService = userService.findById(idUserFromCheckEmail);
        if (userMiddleware.checkPassword(password, UserFromService)) {
            return UserFromService;
        } else {
            System.out.println("Пароль введен не верно! Хотите восстановить пароль(y) или попробовать еще попытку(n)? [y/n]");
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

    public static String email() {
        System.out.println("Введите свою почту: ");
        return scannerString();
    }

    public static String password() {
        System.out.println("Введите свой пароль: ");
        return scannerString();
    }

    public static String scannerString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void wrongInput() {
        System.out.println("Неправильный ввод!");
        System.out.println("----------------------------------------------");

    }

    private List<UserResponse> listPeople() {
        return userService.findAll();
    }

    private UUID checkEmail(String email){
        for (UserResponse userResponse : listPeople()) {
            if (userMiddleware.checkEmail(email, userResponse)) {
                return userResponse.getId();
            }
        }
        return null;
    }

}
