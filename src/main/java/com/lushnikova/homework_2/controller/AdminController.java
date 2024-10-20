package com.lushnikova.homework_2.controller;

import com.lushnikova.homework_2.dto.resp.AdminResponse;
import com.lushnikova.homework_2.dto.resp.UserResponse;
import com.lushnikova.homework_2.mapper.UserMapper;
import com.lushnikova.homework_2.middleware.AdminMiddleware;
import com.lushnikova.homework_2.middleware.Middleware;
import com.lushnikova.homework_2.model.Admin;
import com.lushnikova.homework_2.repository.AdminRepository;
import com.lushnikova.homework_2.repository.UserRepository;
import com.lushnikova.homework_2.service.AdminService;
import com.lushnikova.homework_2.service.impl.AdminServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.lushnikova.homework_2.consts.ModesConsts.*;

/**
 * Класс Controller для администратора
 */
public class AdminController extends Controller {
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
     * @param userMapper - репозиторий пользователей
     * @param connection - соединение с б/д
     *
     */
    public AdminController(UserRepository userRepository, UserMapper userMapper, Connection connection) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.adminRepository = new AdminRepository(connection);
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
        try {
            String email = UserController.email();
            Long idUserFromCheckEmail = checkEmail(email);
            if (idUserFromCheckEmail != null) {
                recursionByPassword(idUserFromCheckEmail);
            } else {
                System.out.println("Данного администратора не существует");
                getAdminAfterAuthentication();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
                    try {
                        adminService.findAllUsers().forEach(System.out::println);
                        System.out.println("----------------------------------------------");
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }

                }
                case "2" -> {
                    System.out.println("Введите id пользователя ");
                    try {
                        Long idUser = Long.parseLong(UserController.scannerString());
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
                        else wrongLong();
                    } catch (IllegalArgumentException | SQLException e){
                        System.out.println(e.getMessage());
                    }

                }
                case "3" -> {
                    System.out.println("Введите id пользователя: ");
                    try {
                        Long idUser = Long.parseLong(UserController.scannerString());
                        UserResponse userResponse = adminService.findByIdUser(idUser);
                        if (userResponse != null) {
                            adminService.deleteUser(idUser);
                            System.out.println("Пользователь удален!");
                            System.out.println("----------------------------------------------");
                        }
                        else wrongLong();
                    } catch (IllegalArgumentException | SQLException e){
                        System.out.println(e.getMessage());
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
    private void recursionByPassword(Long idUserFromCheckEmail){

        String password = UserController.password();

        Admin adminFromRepository = null;
        try {
            adminFromRepository = adminRepository.findById(idUserFromCheckEmail);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (!middleware.checkPassword(password, adminFromRepository)) {
            System.out.println(RECOVER_PASSWORD);

            String answer = UserController.scannerString();

            switch (answer) {
                case "y" -> {
                    System.out.println("Введите новый пароль: ");

                    String newPassword = UserController.scannerString();

                    try {
                        adminService.updatePassword(idUserFromCheckEmail, newPassword);
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }

                }
                case "n" -> recursionByPassword(idUserFromCheckEmail);
                default -> {
                    UserController.wrongInput();
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
    private Long checkEmail(String email) throws SQLException {
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
    private List<AdminResponse> listAdmins() throws SQLException {
        return adminService.findAll();
    }

    /**
     * Ответ при неправильном вводе id
     */
    private void wrongLong(){
        System.out.println("Неверно введен id");
    }
}
