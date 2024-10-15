package com.lushnikova.homework_1.controller;

import com.lushnikova.homework_1.dto.resp.AdminResponse;
import com.lushnikova.homework_1.dto.resp.UserResponse;
import com.lushnikova.homework_1.exception.ModelNotFound;
import com.lushnikova.homework_1.middleware.Middleware;
import com.lushnikova.homework_1.service.AdminService;

import java.util.List;
import java.util.UUID;


public class AdminController {
    private final AdminService adminService;
    private final Middleware middleware;

    public AdminController(AdminService adminService, Middleware middleware) {
        this.adminService = adminService;
        this.middleware = middleware;
    }

    //проверка мыла и пароля при входе
    public AdminResponse getAdminAfterAuthentication() {
        while (true){
            String email = UserController.email();
            UUID idUserFromCheckEmail = checkEmail(email);

            if(idUserFromCheckEmail != null){
                return recursionByPassword(idUserFromCheckEmail);
            } else System.out.println("Данного администратора не существует");
        }
    }

    public void modesForUsers() throws ModelNotFound {
        while (true) {
            System.out.println("Выберите:");
            System.out.println("1 - просмотреть список пользователей и их привычки");
            System.out.println("2 - заблокировать пользователя");
            System.out.println("3 - удалить пользователя");
            System.out.println("exit - выход");


            String answer = UserController.scannerString();
            switch (answer) {
                case "1" -> {
                    adminService.findAllUsers().forEach(System.out::println);
                    System.out.println("----------------------------------------------");

                }
                case "2" -> {
                    System.out.println("Введите id пользователя ");
                    try {
                        UUID idUser = UUID.fromString(UserController.scannerString());
                        UserResponse userResponse = adminService.findByIdUser(idUser);
                        if (userResponse != null) {
                            boolean flag = blockUser();
                            if(flag) {
                                System.out.println("Пользователь разблокирован");
                            } else {
                                System.out.println("Пользователь заблокирован");
                            }
                            System.out.println("----------------------------------------------");
                            adminService.blockByIdUser(userResponse.getId(), flag);
                        }
                        else wrongUUID();
                    } catch (IllegalArgumentException e){
                        wrongUUID();
                    }

                }
                case "3" -> {
                    System.out.println("Введите id пользователя ");
                    try {
                        UUID idUser = UUID.fromString(UserController.scannerString());
                        UserResponse userResponse = adminService.findByIdUser(idUser);
                        if (userResponse != null) {
                            adminService.deleteUser(idUser);
                            System.out.println("Пользователь удален!");
                            System.out.println("----------------------------------------------");
                        }
                        else wrongUUID();
                    } catch (IllegalArgumentException e){
                        wrongUUID();
                    }
                }
                case "exit" -> {return;}
                default -> UserController.wrongInput();
            }
        }
    }


    //рекрусивный метод ввода пароля
    private AdminResponse recursionByPassword(UUID idUserFromCheckEmail){

        String password = UserController.password();

        AdminResponse adminFromService = adminService.findById(idUserFromCheckEmail);

        if (middleware.checkPassword(password, adminFromService)) {
            return adminFromService;
        } else {
            System.out.println("Пароль введен не верно! Хотите восстановить пароль(y) или попробовать еще попытку(n)? [y/n]");
            String answer = UserController.scannerString();

            switch (answer) {
                case "y" -> {
                    System.out.println("Введите новый пароль: ");
                    String newPassword = UserController.scannerString();
                    adminService.updatePassword(idUserFromCheckEmail, newPassword);
                    return adminService.findById(idUserFromCheckEmail);
                }
                case "n" -> recursionByPassword(idUserFromCheckEmail);
                default -> UserController.wrongInput();
            }
        }
        return adminFromService;
    }

    private boolean blockUser() {
        System.out.println("Выберите:");
        System.out.println("1 - заблокировать пользователя");
        System.out.println("2 - разблокировать пользователя");

        String answer = UserController.scannerString();
        switch (answer) {
            case "1" -> {return false;}
            case "2" -> {return true;}
            default -> {
                UserController.wrongInput();
                blockUser();
            }
        }
        return false;
    }

    private UUID checkEmail(String email){
        for (AdminResponse adminResponse : listAdmins()) {
            if (middleware.checkEmail(email, adminResponse)) {
                return adminResponse.getId();
            }
        }
        return null;
    }

    private List<AdminResponse> listAdmins(){
        return adminService.findAllAdmins();
    }

    private void wrongUUID(){
        System.out.println("Неверно введен id");
    }
}
