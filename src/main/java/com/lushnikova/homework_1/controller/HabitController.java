package com.lushnikova.homework_1.controller;

import com.lushnikova.homework_1.dto.req.HabitRequest;
import com.lushnikova.homework_1.dto.resp.HabitResponse;
import com.lushnikova.homework_1.exception.ModelNotFound;
import com.lushnikova.homework_1.middleware.DateMiddleware;
import com.lushnikova.homework_1.model.enums.Repeat;
import com.lushnikova.homework_1.model.enums.Statistics;
import com.lushnikova.homework_1.model.enums.Status;
import com.lushnikova.homework_1.service.UserService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.UUID;

import static com.lushnikova.homework_1.controller.UserController.scannerString;
import static com.lushnikova.homework_1.controller.UserController.wrongInput;

public class HabitController {

    private final UserService userService;
    private final DateMiddleware dateMiddleware;

    public HabitController(UserService userService, DateMiddleware dateMiddleware) {
        this.userService = userService;
        this.dateMiddleware = dateMiddleware;
    }

    //crud привычек
    public void crudHabits(UUID idUser) throws ModelNotFound {
        while (true) {
            List<HabitResponse> list = userService.getHabitsByIdUser(idUser);
            System.out.println("Выберите:");
            System.out.println("1 - создать");
            System.out.println("2 - удалить");
            System.out.println("3 - редактировать привычку");
            System.out.println("4 - отметить выполненную привычку");
            System.out.println("5 - включить/выключить уведомление привычки");
            System.out.println("exit - выход из режима управления привычек");
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

    //создание привычки
    public void create(UUID idUser) throws ModelNotFound {
        HabitRequest habitRequest = new HabitRequest();

        System.out.println("Назовите привычку: ");
        habitRequest.setTitle(scannerString());

        System.out.println("Опишите привычку: ");
        habitRequest.setDescription(scannerString());

        habitRequest.setRepeat(getRepeat());

        userService.addHabitByIdUser(idUser, habitRequest);
    }

    //удаление привычки
    public void delete(UUID idUser, List<HabitResponse> list) {
        if (!list.isEmpty()) {
            System.out.println("Какую привычку вы хотите удалить?");
            System.out.println("Введите число от 1 до " + list.size() + ":");

            long idHabit = Long.parseLong(scannerString());

            if (idHabit > 0 && idHabit <= list.size()) {
                userService.deleteHabitByIdUser(idUser, idHabit);

                System.out.println("Ваша привычка удалена!");
                System.out.println("----------------------------------------------");

            } else getErrorHabit();
        } else getErrorHabits();

    }

    //обновление привычки
    public void update(UUID idUser, List<HabitResponse> list) {
        if (!list.isEmpty()) {
            System.out.println("Какую привычку вы хотите редактировать?");

            long idHabit = choiceNumber(list.size());

            if (idHabit > 0 && idHabit <= list.size()) {
                while (true) {
                    System.out.println("Отредактировать: ");
                    System.out.println("1 - название привычки");
                    System.out.println("2 - описание привычки");
                    System.out.println("3 - повторение привычки");
                    System.out.println("4 - статус привычки");
                    System.out.println("exit - выход из редактирования привычки");


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

    //получение привычки
    public void readHabits(UUID idUser) throws ModelNotFound {
        List<HabitResponse> list = userService.getHabitsByIdUser(idUser);
        if (!list.isEmpty()) {
            while (true) {

                System.out.println("Выберите список привычек: ");
                System.out.println("1 - полный список ");
                System.out.println("2 - отфильтрованный по дате создания");
                System.out.println("3 - отфильтрованный по статусу");
                System.out.println("exit - выход из списка привычек");

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

    // установка выполнилась ли привычка
    public void addHabitDoneDates(UUID idUser) {
        List<HabitResponse> list = userService.findById(idUser).getHabits();
        System.out.println("Какую привычку вы хотите редактировать?");
        System.out.println("Введите число от 1 до " + list.size() + ":");

        if (!list.isEmpty()) {

            long idHabit = Long.parseLong(scannerString());

            if (idHabit > 0 && idHabit <= list.size()) {
                userService.setDoneDatesHabitByIdUser(idUser, idHabit);
            } else {
                getErrorHabit();
                addHabitDoneDates(idUser);
            }
        } else getErrorHabits();
    }


    // список привычек по дате создания
    private List<HabitResponse> getListHabitByDateCreate(UUID idUser) throws ModelNotFound {
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

    //список привычек по статусу
    private List<HabitResponse> getListHabitByStatus(UUID idUser) throws ModelNotFound {
        Status status = getStatus();
        return userService.getHabitsByStatusByIdUser(idUser, status);
    }

    //получение статистики привычки
    public void getHabitFulfillmentStatisticsByIdUser(UUID idUser) throws ModelNotFound {
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

    //процент успешного выполнения привычек
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


    //включение или выключение уведомления привычки
    private void switchOnOrOffHabitsNotification(UUID idUser) throws ModelNotFound {
        List<HabitResponse> list = userService.getHabitsByIdUser(idUser);
        System.out.println("Выберите привычку: ");

        long idHabit = choiceNumber(list.size());

        System.out.println("Вы хотите включить или выключить привычку? [on/off]");

        String answer = scannerString();

        switch (answer) {
            case "on" -> {
                try {
                    System.out.println("Введите время в формате HH:mm");

                    LocalTime localTime = LocalTime.parse(scannerString());

                    userService.switchOnPushNotificationByIdUser(idUser, idHabit, localTime);
                } catch (DateTimeParseException e) {
                    System.out.println("Не верный формат даты!");
                }

            }
            case "off" -> userService.switchOffPushNotificationByIdUser(idUser, idHabit);
            default -> {
                wrongInput();
                switchOnOrOffHabitsNotification(idUser);
            }
        }
    }

    //отчет пользователя по прогрессу выполнения
    public void reportHabitByIdUser(UUID idUser) throws ModelNotFound {
        List<HabitResponse> list = userService.getHabitsByIdUser(idUser);

        if(!list.isEmpty()){
            long idHabit = choiceNumber(list.size());
            userService.reportHabitByIdUser(idUser, idHabit);
        } else {
            getErrorHabits();
            reportHabitByIdUser(idUser);
        }
    }


    public static void getErrorHabits() {
        System.out.println("У вас еще не привычек!");
        System.out.println("----------------------------------------------");

    }

    public static void getErrorHabit() {
        System.out.println("Такая привычка не найдена!");
        System.out.println("----------------------------------------------");

    }

    private long choiceNumber(long size) {
        System.out.println("Введите число от 1 до " + size + ":");
        return Long.parseLong(scannerString());
    }

    private Repeat getRepeat() {
        while (true) {
            System.out.println("Как часто ее нужно выполнять ежедневно(d) или еженедельно(w)?[d/w]");
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

    private Status getStatus() {
        while (true) {
            System.out.println("Выберите статус привычки:");
            System.out.println("1 - создана");
            System.out.println("2 - в процессе");
            System.out.println("3 - готово");

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

    private Statistics getStatistics() {
        while (true) {
            System.out.println("Выберите за какой период вы хотите получить привычку:");
            System.out.println("1 - день");
            System.out.println("2 - неделя");
            System.out.println("3 - месяц");

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

    private LocalDate enterDate() {
        System.out.println("формат ввода yyyy-MM-dd");
        return LocalDate.parse(scannerString());
    }

}
