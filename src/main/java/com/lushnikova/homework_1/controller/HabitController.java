package com.lushnikova.homework_1.controller;

import com.lushnikova.homework_1.dto.req.HabitRequest;
import com.lushnikova.homework_1.dto.resp.HabitResponse;
import com.lushnikova.homework_1.middleware.DateMiddleware;
import com.lushnikova.homework_1.model.ENUM.Repeat;
import com.lushnikova.homework_1.model.ENUM.Statistics;
import com.lushnikova.homework_1.model.ENUM.Status;
import com.lushnikova.homework_1.service.UserService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

import static com.lushnikova.homework_1.consts.ModesConsts.*;
import static com.lushnikova.homework_1.controller.UserController.scannerString;
import static com.lushnikova.homework_1.controller.UserController.wrongInput;

/**
 * Класс Controller для привычек
 */
public class HabitController {


    /** Поле сервис пользователей*/
    private final UserService userService;

    /** Поле инструмент проверки дат*/
    private final DateMiddleware dateMiddleware;


    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param userService - сервис пользователей
     */
    public HabitController(UserService userService) {
        this.userService = userService;
        this.dateMiddleware =  new DateMiddleware();
    }


    /**
     * Режим по управлению привычек операции CRUD
     * @param idUser - id пользователя
     */
    public void crudHabits(UUID idUser){
        while (true) {
            List<HabitResponse> list = userService.getHabitsByIdUser(idUser);

            System.out.println(CRUD_HABITS);

            String answer = scannerString();

            switch (answer) {
                case "1" -> create(idUser);
                case "2" -> delete(idUser, list);
                case "3" -> update(idUser, list);
                case "4" -> addHabitDoneDates(idUser);
                case "5" -> switchOnOrOffHabitsNotification(idUser);
                case "exit" -> {
                    return;
                }
                default -> UserController.wrongInput();
            }
        }
    }


    /**
     * Процедура создания привычки
     * @param idUser - id пользователя
     */
    public void create(UUID idUser){
        HabitRequest habitRequest = new HabitRequest();

        System.out.println("Назовите привычку: ");
        habitRequest.setTitle(scannerString());

        System.out.println("Опишите привычку: ");
        habitRequest.setDescription(scannerString());

        habitRequest.setRepeat(getRepeat());

        userService.addHabitByIdUser(idUser, habitRequest);
    }

    /**
     * Процедура удаления привычки
     * @param idUser - id пользователя
     * @param list - список привычек пользователя
     */
    public void delete(UUID idUser, List<HabitResponse> list) {
        if (!list.isEmpty()) {
            System.out.println("Какую привычку вы хотите удалить?");
            System.out.println("Введите число от 1 до " + list.size() + ":");

            try {
                long idHabit = Long.parseLong(scannerString());

                if (idHabit > 0 && idHabit <= list.size()) {
                    userService.deleteHabitByIdUser(idUser, idHabit);

                    System.out.println("Ваша привычка удалена!");
                    System.out.println("----------------------------------------------");

                } else getErrorHabit();
            } catch (NumberFormatException e) {
                wrongInput();
            }
        } else getErrorHabits();

    }

    /**
     * Процедура обновления привычки
     * @param idUser - id пользователя
     * @param list - список привычек пользователя
     */
    public void update(UUID idUser, List<HabitResponse> list) {
        if (!list.isEmpty()) {
            System.out.println("Какую привычку вы хотите редактировать?");

            long idHabit = choiceNumber(list.size());

            if (idHabit > 0 && idHabit <= list.size()) {
                while (true) {
                    System.out.println(UPDATE_HABIT);

                    String answer = scannerString();

                    switch (answer) {
                        case "1" -> {
                            System.out.println("Введите новое название: ");
                            String title = scannerString();

                            userService.updateTitleByIdHabitByIdUser(idUser, idHabit, title);
                        }
                        case "2" -> {
                            System.out.println("Введите новое описание: ");
                            String description = scannerString();

                            userService.updateDescriptionByIdHabitByIdUser(idUser, idHabit, description);
                        }
                        case "3" -> userService.updateRepeatByIdHabitByIdUser(idUser, idHabit, getRepeat());
                        case "4" -> userService.updateStatusByIdHabitByIdUser(idUser, idHabit, getStatus());
                        case "exit" -> {
                            return;
                        }
                        default -> UserController.wrongInput();
                    }
                }

            } else getErrorHabit();
        } else getErrorHabits();

    }

    /**
     * Процедура получения привычки
     * @param idUser - id пользователя
     */
    public void readHabits(UUID idUser){
        List<HabitResponse> list = userService.getHabitsByIdUser(idUser);
        if (!list.isEmpty()) {
            while (true) {

                System.out.println(READ_HABITS);

                String answer2 = scannerString();

                switch (answer2) {
                    case "1" -> {
                        list.forEach(System.out::println);
                        System.out.println("----------------------------------------------");
                        return;
                    }
                    case "2" -> {
                        getListHabitByDateCreate(idUser).forEach(System.out::println);
                        System.out.println("----------------------------------------------");
                        return;
                    }
                    case "3" -> {
                        getListHabitByStatus(idUser).forEach(System.out::println);
                        System.out.println("----------------------------------------------");
                        return;
                    }
                    case "exit" -> {
                        return;
                    }
                    default -> UserController.wrongInput();
                }
            }
        } else getErrorHabits();

    }

    /**
     * Процедура по выполнению привычки
     * @param idUser - id пользователя
     */
    public void addHabitDoneDates(UUID idUser) {
        List<HabitResponse> list = userService.findById(idUser).getHabits();
        System.out.println("Какую привычку вы хотите редактировать?");
        System.out.println("Введите число от 1 до " + list.size() + ":");

        if (!list.isEmpty()) {
            try {
                long idHabit = Long.parseLong(scannerString());

                if (idHabit > 0 && idHabit <= list.size()) {
                    userService.setDoneDatesHabitByIdUser(idUser, idHabit);

                    System.out.println("Отмечено!");
                    System.out.println("----------------------------------------------");

                } else {
                    getErrorHabit();
                    addHabitDoneDates(idUser);
                }

            } catch (NumberFormatException e) {
                wrongInput();
                addHabitDoneDates(idUser);
            }

        } else getErrorHabits();
    }

    /**
     * Функция получения списка привычек пользователя по дате создания {@link UserService#getHabitsByLocalDateByIdUser(UUID, LocalDate)}
     * @param idUser - id пользователя
     * @return возвращает список привычек пользователя по дате создания
     */
    private List<HabitResponse> getListHabitByDateCreate(UUID idUser){
        while (true) {
            System.out.println("Введите время создания привычки: ");

            try {
                LocalDate localdate = enterDate();
                if (dateMiddleware.checkDate(localdate)) {
                    List<HabitResponse> list = userService.getHabitsByLocalDateByIdUser(idUser, localdate);
                    if (!list.isEmpty()) return list;
                    else System.out.println("Данного списка нет по дате создания!");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Не верный формат даты!");
            }
        }
    }

    /**
     * Функция получения списка привычек пользователя по статусу {@link UserService#getHabitsByStatusByIdUser(UUID, Status)}
     * @param idUser - id пользователя
     * @return возвращает список привычек пользователя по статусу
     */
    private List<HabitResponse> getListHabitByStatus(UUID idUser){
        Status status = getStatus();
        return userService.getHabitsByStatusByIdUser(idUser, status);
    }

    /**
     * Процедура генерации статистики выполнения привычки {@link UserService#getHabitFulfillmentStatisticsByIdUser(UUID, Statistics, Long, LocalDate)}
     * @param idUser - id пользователя
     */
    public void getHabitFulfillmentStatisticsByIdUser(UUID idUser){
        List<HabitResponse> list = userService.getHabitsByIdUser(idUser);

        if (!list.isEmpty()) {
            Statistics statistics = getStatistics();

            System.out.println("Выберите привычку.");

            long idHabit = choiceNumber(list.size());

            if (idHabit > 0 && idHabit <= list.size()) {
                while (true) {
                    System.out.println("Введите день начало указанного периода: ");

                    try {
                        LocalDate localdate = enterDate();

                        if (dateMiddleware.checkDate(localdate)) {

                            List<String> statisticsList = userService.getHabitFulfillmentStatisticsByIdUser(idUser, statistics, idHabit, localdate);

                            System.out.println("Генерация статистики выполнения привычки id = " + idHabit + " с " + localdate);

                            statisticsList.forEach(System.out::println);
                            System.out.println("----------------------------------------------");

                            return;
                        }
                    } catch (DateTimeParseException e) {
                        System.out.println("Не верный формат даты!");
                    }
                }
            } else getErrorHabit();
        } else getErrorHabits();
    }

    /**
     * Получение процента успешного выполнения привычек за определенный период {@link UserService#percentSuccessHabitsByIdUser(UUID, LocalDate, LocalDate)}
     * @param idUser - id пользователя
     */
    public void getPercentSuccessHabitsByIdUser(UUID idUser) {
        try {
            System.out.println("Введите день начало периода: ");
            LocalDate dateFrom = enterDate();

            System.out.println("Введите день конца периода: ");

            LocalDate dateTo = enterDate();


            if (dateMiddleware.checkDate(dateFrom) && dateMiddleware.checkDate(dateTo)) {
                System.out.println("Процент успешного выполнения привычек = " +
                        userService.percentSuccessHabitsByIdUser(idUser, dateFrom, dateTo) + "%");
                System.out.println("----------------------------------------------");

            }
        } catch (DateTimeParseException e) {
            System.out.println("Не верный формат даты!");
            getPercentSuccessHabitsByIdUser(idUser);
        }
    }


    /**
     * Процедура включения или выключения отправки уведомления привычки в указанное время
     * {@link UserService#switchOnPushNotificationByIdUser(UUID, Long, LocalTime)}
     * и {@link UserService#switchOffPushNotificationByIdUser(UUID, Long)}
     * @param idUser - id пользователя
     */
    private void switchOnOrOffHabitsNotification(UUID idUser){
        List<HabitResponse> list = userService.getHabitsByIdUser(idUser);
        System.out.println("Выберите привычку: ");

        long idHabit = choiceNumber(list.size());

        if (idHabit > 0 && idHabit <= list.size()) {
            System.out.println(SWITCH_ON_OR_OFF_NOTIFICATION);

            String answer = scannerString();

            switch (answer) {
                case "on" -> {
                    try {
                        System.out.println("Введите время в формате HH:mm");

                        LocalTime localTime = LocalTime.parse(scannerString());

                        userService.switchOnPushNotificationByIdUser(idUser, idHabit, localTime);
                    } catch (DateTimeParseException e ) {
                        System.out.println("Не верный формат даты!");
                    }

                }
                case "off" -> userService.switchOffPushNotificationByIdUser(idUser, idHabit);
                default -> {
                    wrongInput();
                    switchOnOrOffHabitsNotification(idUser);
                }
            }

        } else {
            getErrorHabit();
            switchOnOrOffHabitsNotification(idUser);
        }
    }

    /**
     * Получение отчета для пользователя по прогрессу выполнения привычки {@link UserService#reportHabitByIdUser(UUID, Long)}
     * @param idUser - id пользователя
     */
    public void reportHabitByIdUser(UUID idUser){
        List<HabitResponse> list = userService.getHabitsByIdUser(idUser);

        if(!list.isEmpty()){
            long idHabit = choiceNumber(list.size());
            userService.reportHabitByIdUser(idUser, idHabit);
        } else {
            getErrorHabits();
            reportHabitByIdUser(idUser);
        }
    }


    /**
     * Ответ ошибки по отсутствию привычек
     */
    public static void getErrorHabits() {
        System.out.println(GET_ERROR_HABITS);

    }

    /**
     * Ответ ошибки по отсутствию привычки
     */
    public static void getErrorHabit() {
        System.out.println(GET_ERROR_HABIT);
    }

    /**
     * Введение числа в консоль
     * @param size - размер списка привычек
     * @return возвращение введенного числа
     */
    private long choiceNumber(long size) {
        System.out.println("Введите число от 1 до " + size + ":");
        try {
            return Long.parseLong(scannerString());
        } catch (NumberFormatException e) {
            System.out.println("Неправильно введено число");
            return choiceNumber(size);
        }
    }


    /**
     * Введение частоты повторения в консоль
     * @return возвращает частоту повторения привычки
     */
    private Repeat getRepeat() {
        while (true) {
            System.out.println(REPEAT_HABIT);

            String answer = scannerString();

            switch (answer) {
                case "d" -> {
                    return Repeat.DAILY;
                }
                case "w" -> {
                    return Repeat.WEEKLY;
                }
                default -> UserController.wrongInput();
            }
        }
    }

    /**
     * Введение статуса в консоль
     * @return возвращает статус привычки
     */
    private Status getStatus() {
        while (true) {
            System.out.println(STATUS_HABIT);

            String status = scannerString();

            switch (status) {
                case "1" -> {
                    return Status.CREATED;
                }
                case "2" -> {
                    return Status.IN_PROGRESS;
                }
                case "3" -> {
                    return Status.DONE;
                }
                default -> UserController.wrongInput();
            }
        }
    }

    /**
     * Введение в консоль указание периода для статистики
     * @return возвращение периода
     */
    private Statistics getStatistics() {
        while (true) {
            System.out.println(STATISTICS_HABIT);

            String answer = scannerString();

            switch (answer) {
                case "1" -> {
                    return Statistics.DAY;
                }
                case "2" -> {
                    return Statistics.WEEK;
                }
                case "3" -> {
                    return Statistics.MONTH;
                }
                default -> UserController.wrongInput();
            }
        }
    }

    /**
     * Ввод в консоль даты
     * @return возвращение даты
     */
    private LocalDate enterDate() {
        System.out.println("формат ввода yyyy-MM-dd");
        return LocalDate.parse(scannerString());
    }

}
