package com.lushnikova.homework_2.controller;

import com.lushnikova.homework_2.dto.req.HabitRequest;
import com.lushnikova.homework_2.dto.resp.HabitResponse;
import com.lushnikova.homework_2.middleware.DateMiddleware;
import com.lushnikova.homework_2.model.ENUM.Repeat;
import com.lushnikova.homework_2.model.ENUM.Statistics;
import com.lushnikova.homework_2.model.ENUM.Status;
import com.lushnikova.homework_2.repository.HabitRepository;
import com.lushnikova.homework_2.service.HabitService;
import com.lushnikova.homework_2.service.impl.HabitServiceImpl;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import static com.lushnikova.homework_2.consts.ModesConsts.*;
import static com.lushnikova.homework_2.controller.UserController.scannerString;
import static com.lushnikova.homework_2.controller.UserController.wrongInput;

/**
 * Класс Controller для привычек
 */
public class HabitController {


    /** Поле сервис привычек*/
    private final HabitService habitService;

    /** Поле инструмент проверки дат*/
    private final DateMiddleware dateMiddleware;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param habitRepository - репозиторий привычек
     */
    public HabitController(HabitRepository habitRepository) {
        this.habitService = new HabitServiceImpl(habitRepository);
        this.dateMiddleware =  new DateMiddleware();
    }


    /**
     * Режим по управлению привычек операции CRUD
     * @param idUser - id пользователя
     */
    public void crudHabits(Long idUser){
        while (true) {
            List<HabitResponse> list;
            try {
                list = habitResponseList(idUser);

                System.out.println(CRUD_HABITS);

                String answer = scannerString();

                switch (answer) {
                    case "1" -> create(idUser);
                    case "2" -> delete(list);
                    case "3" -> update(list);
                    case "4" -> addHabitDoneDates(idUser);
                    case "5" -> switchOnOrOffHabitsNotification(idUser);
                    case "exit" -> {
                        return;
                    }
                    default -> UserController.wrongInput();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
    }


    /**
     * Процедура создания привычки
     * @param idUser - id пользователя
     */
    public void create(Long idUser){
        HabitRequest habitRequest = new HabitRequest();

        System.out.println("Назовите привычку: ");
        habitRequest.setTitle(scannerString());

        System.out.println("Опишите привычку: ");
        habitRequest.setDescription(scannerString());

        habitRequest.setRepeat(getRepeat());

        try {
            habitService.save(habitRequest, idUser);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Процедура удаления привычки
     * @param list - список привычек пользователя
     */
    public void delete(List<HabitResponse> list) {
        if (!list.isEmpty()) {
            System.out.println("Какую привычку вы хотите удалить?");
            System.out.println("Введите число от 1 до " + list.size() + ":");

            try {
                long idHabit = Long.parseLong(scannerString());

                if (idHabit > 0 && idHabit <= list.size()) {
                    try {
                        habitService.delete(idHabit);
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }

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
     * @param list - список привычек пользователя
     */
    public void update(List<HabitResponse> list) {

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

                            try {
                                habitService.updateTitleByIdHabit(idHabit, title);
                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        case "2" -> {
                            System.out.println("Введите новое описание: ");
                            String description = scannerString();

                            try {
                                habitService.updateDescriptionByIdHabit(idHabit, description);
                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        case "3" -> {
                            try {
                                habitService.updateRepeatByIdHabit(idHabit, getRepeat());
                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        case "4" -> {
                            try {
                                habitService.updateStatusByIdHabit(idHabit, getStatus());
                            } catch (SQLException e) {
                                System.out.println(e.getMessage());
                            }
                        }
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
    public void readHabits(Long idUser){
        List<HabitResponse> list;
        try {
            list = habitResponseList(idUser);

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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    /**
     * Процедура по выполнению привычки
     * @param idUser - id пользователя
     */
    public void addHabitDoneDates(Long idUser) {
        List<HabitResponse> list ;
        try {
            list = habitResponseList(idUser);

            System.out.println("Какую привычку вы хотите редактировать?");
            System.out.println("Введите число от 1 до " + list.size() + ":");

            if (!list.isEmpty()) {
                try {
                    long idHabit = Long.parseLong(scannerString());

                    if (idHabit > 0 && idHabit <= list.size()) {
                        habitService.setDoneDates(idHabit);

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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Функция получения списка привычек пользователя по дате создания {@link HabitService#getHabitsByDate(Long, Date)}
     * @param idUser - id пользователя
     * @return возвращает список привычек пользователя по дате создания
     */
    private List<HabitResponse> getListHabitByDateCreate(Long idUser){
        while (true) {
            System.out.println("Введите время создания привычки: ");

            try {
                LocalDate localdate = enterDate();
                if (dateMiddleware.checkDate(localdate)) {
                    List<HabitResponse> list;
                    try {
                        list = habitService.getHabitsByDate(idUser, Date.valueOf(localdate.toString()));

                        if (!list.isEmpty()) return list;
                        else System.out.println("Данного списка нет по дате создания!");
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                }
            } catch (DateTimeParseException e) {
                System.out.println("Не верный формат даты!");
            }
        }
    }

    /**
     * Функция получения списка привычек пользователя по статусу {@link HabitService#getHabitsByStatus(Long, Status)}
     * @param idUser - id пользователя
     * @return возвращает список привычек пользователя по статусу
     */
    private List<HabitResponse> getListHabitByStatus(Long idUser){
        Status status = getStatus();
        try {
            return habitService.getHabitsByStatus(idUser, status);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * Процедура генерации статистики выполнения привычки {@link HabitService#getHabitFulfillmentStatistics(Long, LocalDate,Statistics)}
     * @param idUser - id пользователя
     */
    public void getHabitFulfillmentStatisticsByIdUser(Long idUser){
        List<HabitResponse> list;
        try {
            list = habitResponseList(idUser);

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

                                List<String> statisticsList = habitService.getHabitFulfillmentStatistics(idHabit, localdate, statistics);

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

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    /**
     * Получение процента успешного выполнения привычек за определенный период {@link HabitService#percentSuccessHabits(Long, LocalDate, LocalDate)}
     * @param idUser - id пользователя
     */
    public void getPercentSuccessHabitsByIdUser(Long idUser) {
        try {
            System.out.println("Введите день начало периода: ");
            LocalDate dateFrom = enterDate();

            System.out.println("Введите день конца периода: ");

            LocalDate dateTo = enterDate();

            if (dateMiddleware.checkDate(dateFrom) && dateMiddleware.checkDate(dateTo)) {
                try {
                    int percent = habitService.percentSuccessHabits(idUser, dateFrom, dateTo);
                    System.out.println("Процент успешного выполнения привычек = " + percent + "%");
                    System.out.println("----------------------------------------------");
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

            }
        } catch (DateTimeParseException e) {
            System.out.println("Не верный формат даты!");
            getPercentSuccessHabitsByIdUser(idUser);
        }
    }


    /**
     * Процедура включения или выключения отправки уведомления привычки в указанное время
     * {@link HabitService#switchOnOrOffPushNotification(Long, Time)}
     * @param idUser - id пользователя
     */
    private void switchOnOrOffHabitsNotification(Long idUser){
        List<HabitResponse> list;
        try {
            list = habitResponseList(idUser);

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

                            habitService.switchOnOrOffPushNotification(idHabit, Time.valueOf(localTime.toString()));
                        } catch (DateTimeParseException e ) {
                            System.out.println("Не верный формат даты!");
                        }

                    }
                    case "off" -> habitService.switchOnOrOffPushNotification(idHabit, null);
                    default -> {
                        wrongInput();
                        switchOnOrOffHabitsNotification(idUser);
                    }
                }

            } else {
                getErrorHabit();
                switchOnOrOffHabitsNotification(idUser);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Получение отчета для пользователя по прогрессу выполнения привычки {@link HabitService#reportHabit(Long)}
     * @param idUser - id пользователя
     */
    public void reportHabitByIdUser(Long idUser){
        List<HabitResponse> list ;
        try {
            list = habitResponseList(idUser);

            if(!list.isEmpty()){
                long idHabit = choiceNumber(list.size());

                habitService.reportHabit(idHabit);
            } else {
                getErrorHabits();
                reportHabitByIdUser(idUser);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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

    /**
     * Функция получения списка привычек {@link HabitService#findAll(Long)}
     * @param idUser - пользователя
     * @return возвращает список привычек пользователя
     */
    private List<HabitResponse> habitResponseList(Long idUser) throws SQLException {
        return habitService.findAll(idUser);
    }
}
