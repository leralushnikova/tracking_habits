package com.lushnikova.homework_1.controller;

import com.lushnikova.homework_1.dto.resp.AdminResponse;
import com.lushnikova.homework_1.dto.resp.UserResponse;
import com.lushnikova.homework_1.exception.ModelNotFound;
import com.lushnikova.homework_1.middleware.Middleware;
import com.lushnikova.homework_1.service.AdminService;

import java.util.List;
import java.util.UUID;

/**
 * Класс Controller для администратора
 */
public class AdminController {

    /** Поле сервис администраторов*/
    private final AdminService adminService;

    /** Поле инструмент проверки*/
    private final Middleware middleware;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param adminService - сервис администраторов
     * @param middleware - инструмент проверки
     */
    public AdminController(AdminService adminService, Middleware middleware) {
        this.adminService = adminService;
        this.middleware = middleware;
    }

    /**
     * Функция получения администратора
     * проверки его почты и пароля при входе
     * @return возвращение найденного администратора
     */
    public AdminResponse getAdminAfterAuthentication() {
        while (true){
            String email = UserController.email();
            UUID idUserFromCheckEmail = checkEmail(email);

            if(idUserFromCheckEmail != null){
                return recursionByPassword(idUserFromCheckEmail);
            } else System.out.println("Данного администратора не существует");
        }
    }

    /**
     * Процедура получения списка пользователей и их привычек,
     * а так же режим по управлению пользователями
     * @throws ModelNotFound
     */
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

    /**
     * Функция получения администратора по id при проверке пароля,
     * если пароль не совпадает, то предлагается переустановить пароль
     * @param idUserFromCheckEmail - id администратора
     * @return возвращает объект администратора
     */
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

    /**
     * Функция блокировки пользователя
     * @return возвращает значение заблокирован пользователь или нет
     */
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

    /**
     * Функция получения id администратора при совпадении почты из списка администраторов{@link AdminController#listAdmins}
     * @param email - почта при вводе
     * @return возвращает уникальный идентификатор пользователя
     */
    private UUID checkEmail(String email){
        for (AdminResponse adminResponse : listAdmins()) {
            if (middleware.checkEmail(email, adminResponse)) {
                return adminResponse.getId();
            }
        }
        return null;
    }

    /**
     * Функция получения списка администраторов {@link AdminService#findAllAdmins()}
     * @return возвращает список администраторов
     */
    private List<AdminResponse> listAdmins(){
        return adminService.findAllAdmins();
    }

    /**
     * Ответ при неправильном вводе id
     */
    private void wrongUUID(){
        System.out.println("Неверно введен id");
    }
}
