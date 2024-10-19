package com.lushnikova.homework_1.controller;

import com.lushnikova.homework_1.dto.resp.AdminResponse;
import com.lushnikova.homework_1.dto.resp.UserResponse;
import com.lushnikova.homework_1.middleware.Middleware;
import com.lushnikova.homework_1.model.Admin;
import com.lushnikova.homework_1.repository.AdminRepository;
import com.lushnikova.homework_1.service.AdminService;

import java.util.List;
import java.util.UUID;

import static com.lushnikova.homework_1.consts.ModesConsts.*;

/**
 * Класс Controller для администратора
 */
public class AdminController {

    /** Поле сервис администраторов*/
    private final AdminService adminService;

    /** Поле репозиторий администраторов*/
    private final AdminRepository adminRepository;

    /** Поле инструмент проверки*/
    private final Middleware middleware;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param adminService - сервис администраторов
     * @param adminRepository - репозиторий администраторов
     * @param middleware - инструмент проверки
     */
    public AdminController(AdminService adminService, AdminRepository adminRepository, Middleware middleware) {
        this.adminService = adminService;
        this.adminRepository = adminRepository;
        this.middleware = middleware;
    }

    /**
     * Процедура проверки почты и пароля
     * администратора при входе
     */
    public void getAdminAfterAuthentication() {
        String email = UserController.email();
        UUID idUserFromCheckEmail = checkEmail(email);

        if (idUserFromCheckEmail != null) {
            recursionByPassword(idUserFromCheckEmail);
        } else {
            System.out.println("Данного администратора не существует");
            getAdminAfterAuthentication();
        }
    }

    /**
     * Процедура получения списка пользователей и их привычек,
     * а так же режим по управлению пользователями
     */
    public void modesForUsers(){
        while (true) {
            System.out.println(MODES_FOR_USER_ADMIN);

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
                    System.out.println("Введите id пользователя: ");
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
     * Процедура проверки пароля администратора,
     * если пароль не совпадает, то предлагается переустановить пароль
     * @param idUserFromCheckEmail - id администратора
     */
    private void recursionByPassword(UUID idUserFromCheckEmail){

        String password = UserController.password();

        Admin adminFromRepository = adminRepository.findById(idUserFromCheckEmail);

        if (!middleware.checkPassword(password, adminFromRepository)) {
            System.out.println(RECOVER_PASSWORD);

            String answer = UserController.scannerString();

            switch (answer) {
                case "y" -> {
                    System.out.println("Введите новый пароль: ");

                    String newPassword = UserController.scannerString();

                    adminService.updatePassword(idUserFromCheckEmail, newPassword);

                }
                case "n" -> recursionByPassword(idUserFromCheckEmail);
                default -> UserController.wrongInput();
            }
        }
    }

    /**
     * Функция блокировки пользователя
     * @return возвращает значение заблокирован пользователь или нет
     */
    private boolean blockUser() {
        System.out.println(BLOCK_USER);

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
     * Функция получения списка администраторов {@link AdminService#findAll()}
     * @return возвращает список администраторов
     */
    private List<AdminResponse> listAdmins(){
        return adminService.findAll();
    }

    /**
     * Ответ при неправильном вводе id
     */
    private void wrongUUID(){
        System.out.println("Неверно введен id");
    }
}
