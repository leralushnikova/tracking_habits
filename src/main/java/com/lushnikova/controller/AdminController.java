package com.lushnikova.controller;

import com.lushnikova.dto.resp.AdminResponse;
import com.lushnikova.dto.resp.UserResponse;
import com.lushnikova.exception.ModelNotFound;
import com.lushnikova.middleware.Middleware;
import com.lushnikova.service.AdminService;

import java.util.List;
import java.util.UUID;

import static com.lushnikova.controller.UserController.*;


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
            String email = email();
            UUID idPersonFromCheckEmail = checkEmail(email);

            if(idPersonFromCheckEmail != null){
                return recursionByPassword(idPersonFromCheckEmail);
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


            String answer = scannerString();
            switch (answer) {
                case "1" -> {
                    adminService.findAllUsers().forEach(System.out::println);
                    System.out.println("----------------------------------------------");

                }
                case "2" -> {
                    System.out.println("Введите id пользователя ");
                    try {
                        UUID idUser = UUID.fromString(scannerString());
                        UserResponse userResponse = adminService.findByIdUser(idUser);
                        if (userResponse != null) {
                            boolean flag = blockPerson();
                            if(flag) {
                                System.out.println("Пользователь разблокирован");
                            } else {
                                System.out.println("Пользователь заблокирован");
                            }
                            System.out.println("----------------------------------------------");
                            adminService.blockByIpPerson(userResponse.getId(), flag);
                        }
                        else wrongUUID();
                    } catch (IllegalArgumentException e){
                        wrongUUID();
                    }

                }
                case "3" -> {
                    System.out.println("Введите id пользователя ");
                    try {
                        UUID idUser = UUID.fromString(scannerString());
                        UserResponse userResponse = adminService.findByIdUser(idUser);
                        if (userResponse != null) {
                            adminService.deletePerson(idUser);
                            System.out.println("Пользователь удален!");
                            System.out.println("----------------------------------------------");
                        }
                        else wrongUUID();
                    } catch (IllegalArgumentException e){
                        wrongUUID();
                    }
                }
                case "exit" -> {return;}
                default -> wrongInput();
            }
        }
    }


    //рекрусивный метод ввода пароля
    private AdminResponse recursionByPassword(UUID idPersonFromCheckEmail){

        String password = password();

        AdminResponse adminFromService = adminService.findById(idPersonFromCheckEmail);

        if (middleware.checkPassword(password, adminFromService)) {
            return adminFromService;
        } else {
            System.out.println("Пароль введен не верно! Хотите восстановить пароль(y) или попробовать еще попытку(n)? [y/n]");
            String answer = scannerString();

            switch (answer) {
                case "y" -> {
                    System.out.println("Введите новый пароль: ");
                    String newPassword = scannerString();
                    adminService.updatePassword(idPersonFromCheckEmail, newPassword);
                    return adminService.findById(idPersonFromCheckEmail);
                }
                case "n" -> recursionByPassword(idPersonFromCheckEmail);
                default -> wrongInput();
            }
        }
        return adminFromService;
    }

    private boolean blockPerson() {
        System.out.println("Выберите:");
        System.out.println("1 - заблокировать пользователя");
        System.out.println("2 - разблокировать пользователя");

        String answer = scannerString();
        switch (answer) {
            case "1" -> {return false;}
            case "2" -> {return true;}
            default -> {
                wrongInput();
                blockPerson();
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
