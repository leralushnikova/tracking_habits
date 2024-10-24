package com.lushnikova.homework_1.controller;

import com.lushnikova.homework_1.dto.resp.AdminResponse;
import com.lushnikova.homework_1.dto.resp.UserResponse;
import com.lushnikova.homework_1.mapper_mapstruct.UserMapper;
import com.lushnikova.homework_1.middleware.AdminMiddleware;
import com.lushnikova.homework_1.middleware.Middleware;
import com.lushnikova.homework_1.model.Admin;
import com.lushnikova.homework_1.repository.AdminRepository;
import com.lushnikova.homework_1.repository.UserRepository;
import com.lushnikova.homework_1.service.AdminService;
import com.lushnikova.homework_1.service.impl.AdminServiceImpl;

import java.util.List;
import java.util.UUID;

import static com.lushnikova.homework_1.consts.ModesConsts.*;
import static com.lushnikova.homework_1.controller.UserController.wrongInput;

/**
 * Класс Controller для администратора
 */
public class AdminController extends Controller{
    /** Поле репозиторий пользователей*/
    private final UserRepository userRepository;

    /** Поле репозиторий администраторов*/
    private final AdminRepository adminRepository;

    /** Поле сервис администраторов*/
    private AdminService adminService;

    /** Поле инструмент проверки*/
    private final Middleware middleware;
    /** Поле преобразования пользователей*/
    private final UserMapper userMapper;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param userRepository - репозиторий пользователей
     */
    public AdminController(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.adminRepository = AdminRepository.getInstance();
        middleware = new AdminMiddleware();
    }

    /**
     * Процедура создания сервиса администратора
     */
    @Override
    public void createService() {
        adminService = new AdminServiceImpl(userRepository, adminRepository, userMapper);
    }

    /**
     * Авторизация администратора
     */
    @Override
    void enter() {
        getAdminAfterAuthentication();
        modesForUsers();
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
    public void modesForUsers() {

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
                default -> wrongInput();
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
                default -> {
                    wrongInput();
                    recursionByPassword(idUserFromCheckEmail);
                }
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
                wrongInput();
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
