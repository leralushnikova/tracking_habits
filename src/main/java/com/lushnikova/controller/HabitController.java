package com.lushnikova.controller;

import com.lushnikova.dto.req.HabitRequest;
import com.lushnikova.dto.resp.HabitResponse;
import com.lushnikova.exception.ModelNotFound;
import com.lushnikova.mapper_mapstruct.PersonMapper;
import com.lushnikova.middleware.DateMiddleware;
import com.lushnikova.model.enums.Repeat;
import com.lushnikova.model.enums.Status;
import com.lushnikova.service.PersonService;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static com.lushnikova.controller.PersonController.scannerString;
import static com.lushnikova.controller.PersonController.wrongInput;

public class HabitController {

    private final PersonService personService;
    private final DateMiddleware dateMiddleware;

    public HabitController(PersonService personService, DateMiddleware dateMiddleware) {
        this.personService = personService;
        this.dateMiddleware = dateMiddleware;
    }

    //crud привычек
    public void crudHabits(UUID idPerson) throws ModelNotFound, ParseException {
        while (true) {
            List<HabitResponse> list = personService.getHabitsByIdPerson(idPerson);
            System.out.println("Вы хотите создать(c), удалить(d), " +
                    "редактировать(u) привычку или посмотреть все привычки(r), выход(exit)?[c/d/u/r/exit]");

            String answer = scannerString();

            switch (answer) {
                case "c" -> create(idPerson);
                case "d" -> delete(idPerson, list);
                case "u" -> update(idPerson, list);
                case "r" -> readHabits(idPerson, list);
                case "exit" -> {
                    return;
                }
                default -> wrongInput();
            }
        }
    }

    //создание привычки
    public void create(UUID idPerson) throws ModelNotFound {
        HabitRequest habitRequest = new HabitRequest();

        System.out.println("Назовите привычку: ");
        habitRequest.setTitle(scannerString());

        System.out.println("Опишите привычку: ");
        habitRequest.setDescription(scannerString());

        habitRequest.setRepeat(getRepeat());

        personService.addHabitByIdPerson(idPerson, habitRequest);
    }

    public void delete(UUID idPerson, List<HabitResponse> list){
        if (!list.isEmpty()) {
            System.out.println("Какую привычку вы хотите удалить?");
            System.out.println("Введите число от 1 до " + list.size() + ":");

            long idHabit = Long.parseLong(scannerString());

            if (idHabit > 0 && idHabit <= list.size()) {
                personService.deleteHabitByIdPerson(idPerson, idHabit);
                System.out.println("Ваша привычка удалена");
            } else getErrorHabit();
        } else getErrorHabits();

    }

    public void update(UUID idPerson, List<HabitResponse> list){
        if (!list.isEmpty()) {
            System.out.println("Какую привычку вы хотите редактировать?");
            System.out.println("Введите число от 1 до " + list.size() + ":");

            long idHabit = Long.parseLong(scannerString());

            if (idHabit > 0 && idHabit <= list.size()) {
                while (true) {
                    System.out.println("Что вы хотите отредактировать название(t), " +
                            "описание(d), повторение(r), статус(s) привычки или выход(exit)?[t/d/r/s/exit]");

                    String answer = scannerString();

                    switch (answer) {
                        case "t" -> {

                            System.out.println("Введите новое название: ");
                            String title = scannerString();

                            personService.updateTitleByIdHabitByIdPerson(idPerson, idHabit, title);
                        }
                        case "d" -> {
                            System.out.println("Введите новое описание: ");
                            String description = scannerString();

                            personService.updateDescriptionByIdHabitByIdPerson(idPerson, idHabit, description);
                        }
                        case "r" -> personService.updateRepeatByIdHabitByIdPerson(idPerson, idHabit, getRepeat());
                        case "s" -> personService.updateStatusByIdHabitByIdPerson(idPerson, idHabit, getStatus());
                        case "exit" -> {
                            return;
                        }
                        default -> wrongInput();
                    }
                }

            } else getErrorHabit();
        } else getErrorHabits();

    }

    public void readHabits(UUID idPerson, List<HabitResponse> list) throws ModelNotFound, ParseException {
        if(!list.isEmpty()){
            while (true) {
                System.out.println("Хотите заполучить полный список(a) или отфильтровать его(f)?[a/f]");
                String answer2 = scannerString();

                switch (answer2) {
                    case "a" -> {
                        list.forEach(System.out::println);
                        return;
                    }
                    case "f" -> {
                        while (true) {
                            System.out.println("Отфильтровать по дате создания(d) или по статусу(s)?");
                            String answer3 = scannerString();

                            switch (answer3) {
                                case "d" -> {
                                    getListHabitByDateCreate(idPerson).forEach(System.out::println);
                                    return;
                                }
                                case "s" -> {
                                    getListHabitByStatus(idPerson).forEach(System.out::println);
                                    return;
                                }
                                default -> wrongInput();
                            }
                        }
                    }
                    default -> wrongInput();
                }
            }
        } else getErrorHabits();

    }


    public  void addHabitDoneDates(UUID idPerson){
        List<HabitResponse> list = personService.findById(idPerson).getHabits();
        while (true) {

            System.out.println("Хотите отметить привычку отмеченной?[y/n]");

            String answer = scannerString();

            if (answer.equals("y")) {

                System.out.println("Какую привычку вы хотите редактировать?");
                System.out.println("Введите число от 1 до " + list.size() + ":");

                long idHabit = Long.parseLong(scannerString());

                if (idHabit > 0 && idHabit <= list.size()) {
                    personService.setDoneDatesHabitByIdPerson(idPerson, idHabit);
                } else getErrorHabit();
            }
            if (answer.equals("n")) return;

            if(!answer.equals("y")) wrongInput();
        }
    }


    public List<HabitResponse> getListHabitByDateCreate(UUID idPerson) throws ModelNotFound, ParseException {
        while (true) {
            System.out.println("Введите время создания привычки(формат ввода yyyy-MM-dd): ");
            String time = scannerString();
            LocalDate localdate = LocalDate.parse(time);

            if (dateMiddleware.checkDate(localdate)) {
                return personService.getHabitsByLocalDateByIdPerson(idPerson, localdate);
            } else System.out.println("Не верный формат даты!");
        }
    }

    public List<HabitResponse> getListHabitByStatus(UUID idPerson) throws ModelNotFound {
        Status status = getStatus();
        return personService.getHabitsByStatusByIdPerson(idPerson, status);
    }


    public static void getErrorHabits(){
        System.out.println("У вас еще не привычек!");
    }

    public static void getErrorHabit(){
        System.out.println("Такая привычка не найдена!");
    }

    private Repeat getRepeat(){
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
                default -> wrongInput();
            }
        }
    }

    public Status getStatus(){
        while (true) {
            System.out.println("Выберите статус привычки " +
                    "в списке(t), в процессе(p) или готово(d)?[t/p/d] ");
            String status = scannerString();

            switch (status){
                case "t" -> {
                    return Status.CREATED;
                }
                case "p" -> {
                    return Status.IN_PROGRESS;
                }
                case "d" -> {
                    return Status.DONE;
                }
                default -> wrongInput();
            }
        }
    }

}
