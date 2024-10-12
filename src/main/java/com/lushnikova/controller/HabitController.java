package com.lushnikova.controller;

import com.lushnikova.dto.req.HabitRequest;
import com.lushnikova.dto.resp.HabitResponse;
import com.lushnikova.exception.ModelNotFound;
import com.lushnikova.model.Habit;
import com.lushnikova.model.enums.Repeat;
import com.lushnikova.model.enums.Status;
import com.lushnikova.repository.PersonRepository;
import com.lushnikova.service.HabitService;
import com.lushnikova.service.PersonService;

import java.time.LocalDate;
import java.util.List;

import static com.lushnikova.controller.PersonController.scannerString;
import static com.lushnikova.controller.PersonController.wrongInput;

public class HabitController {
    private final PersonRepository personRepository;
    private final HabitService habitService;
    private final PersonService personService;
    private final PersonController personController;

    public HabitController(PersonRepository personRepository, HabitService habitService, PersonService personService, PersonController personController) {
        this.personRepository = personRepository;
        this.habitService = habitService;
        this.personService = personService;
        this.personController = personController;
    }

    //создание привычки
    public HabitResponse createHabit(Long idPerson) throws ModelNotFound {
        HabitRequest habitRequest = new HabitRequest();

        System.out.println("Назовите привычку: ");
        habitRequest.setTitle(scannerString());

        System.out.println("Опишите привычку: ");
        habitRequest.setDescription(scannerString());

        one:
        while (true) {
            System.out.println("Как часто ее нужно выполнять ежедневно(d) или еженедельно(w)?[d/w]");
            String answer = scannerString();
            switch (answer) {
                case "d" -> {
                    habitRequest.setRepeat(Repeat.DAILY);
                    break one;
                }
                case "w" -> {
                    habitRequest.setRepeat(Repeat.WEEKLY);
                    break one;
                }
                default -> wrongInput();
            }
        }
        HabitResponse response =  habitService.save(habitRequest);
        personService.addHabitOfPerson(idPerson, response.getId());
        return response;
    }

    public void deleteHabit(Long idPerson, Long idHabit) throws ModelNotFound {
        personService.deleteHabitOfPerson(idPerson, idHabit);
        habitService.delete(idHabit);
    }

    public void updateHabit(Long idPerson, Long idHabit) throws ModelNotFound {
        Habit habit = personRepository.findById(idPerson).getHabits().get(Math.toIntExact(idHabit) - 1);
        one:
        while (true) {
            System.out.println("Что вы хотите отредактировать название(t), " +
                    "описание(d), повторение(r), статус(s) привычки или выход(e)?[t/d/r/s/e]");
            String answer = scannerString();
            switch (answer) {
                case "t" -> {
                    System.out.println("Введите новое название: ");
                    String title = scannerString();
                    habit.setTitle(title);
                }
                case "d" -> {
                    System.out.println("Введите новое описание: ");
                    String description = scannerString();
                    habit.setDescription(description);
                    break one;
                }
                case "r" -> {
                    while (true) {
                        System.out.println("Выберите ежедневное(d) или еженедельное(w)?[d/w] ");
                        String repeat = scannerString();
                        switch (repeat){
                            case "d" -> {
                                habit.setRepeat(Repeat.DAILY);
                                break one;
                            }
                            case "w" -> {
                                habit.setRepeat(Repeat.WEEKLY);
                                break one;
                            }
                            default -> wrongInput();
                        }
                    }
                }
                case "s" -> {
                    System.out.println("Выберите статус привычки " +
                            "в списке(t), в процессе(p) или готово(d)?[t/p/d] ");
                    String status = scannerString();
                    switch (status){
                        case "t" -> {
                            habit.setStatus(Status.TO_DO);
                            break one;
                        }
                        case "p" -> {
                            habit.setStatus(Status.IN_PROGRESS);
                            break one;
                        }
                        case "d" -> {
                            habit.setStatus(Status.DONE);
                            break one;
                        }
                        default -> wrongInput();
                    }
                }
                case "e" -> {break one;}
                default -> wrongInput();
            }
        }
        habitService.update(idPerson, habit);
        habitService.findById(idHabit);
    }

    public List<HabitResponse> getListHabitByDateCreate(Long idPerson){
        try {
            System.out.println("Введите время создания привычки(формат ввода yyyy-MM-dd): ");
            String time = scannerString();
            LocalDate localdate = LocalDate.parse(time);

            return habitService.getListHabitByDateCreate(idPerson, localdate);
        } catch (Exception e) {
            System.out.println("Не верный формат даты!");
        }
        return null;
    }

    public List<HabitResponse> getListHabitByStatus(Long idPerson){

        while (true) {

            System.out.println("Введите статус в списке(t), в процессе(p) или готово(d)?[t/p/d]");
            String status = scannerString();

            switch (status){
                case "t" -> {
                    return habitService.getListHabitByStatus(idPerson, Status.TO_DO);
                }
                case "p" -> {
                    return habitService.getListHabitByStatus(idPerson, Status.IN_PROGRESS);
                }
                case "d" -> {
                    return habitService.getListHabitByStatus(idPerson, Status.DONE);
                }
                default -> wrongInput();
            }
        }
    }

    //просмотр привычек
    public void crudHabits(Long idPerson) throws ModelNotFound {
        if(idPerson != null) {
            one:
            while (true) {
                System.out.println("Вы хотите создать(c), удалить(d), " +
                        "редактировать(u) привычку или посмотреть все привычки(r), выход(e)?[c/d/u/r/e]");

                String answer = scannerString();

                switch (answer) {
                    case "c" -> System.out.println(createHabit(idPerson));
                    case "d" -> {
                        if (personController.getPerson(idPerson).getHabits() != null) {
                            System.out.println("Какую привычку вы хотите удалить?");
                            System.out.println("Введите число от 1 до " + personController.getPerson(idPerson).getHabits().size() + ":");
                            long idHabit = Long.parseLong(scannerString());
                            deleteHabit(idPerson, idHabit);
                        } else getErrorHabit();

                    }
                    case "u" -> {
                        if (personController.getPerson(idPerson).getHabits() != null) {
                            System.out.println("Какую привычку вы хотите редактировать?");
                            System.out.println("Введите число от 1 до " + personController.getPerson(idPerson).getHabits().size() + ":");
                            long idHabit = Long.parseLong(scannerString());
                            updateHabit(idPerson, idHabit);
                        } else getErrorHabit();

                    }
                    case "r" -> {
                        if (personController.getPerson(idPerson).getHabits() != null) {
                            two:
                            while (true) {
                                System.out.println("Хотите заполучить полный список(a) или отфильтровать его(f)?[a/f]");
                                String answer2 = scannerString();

                                switch (answer2){
                                    case "a" -> {
                                        System.out.println(personController.getPerson(idPerson).getHabits());
                                        break two;
                                    }
                                    case "f" -> {
                                        while (true) {
                                            System.out.println("Отфильтровать по дате создания(d) или по статусу(s)?");
                                            String answer3 = scannerString();

                                            switch (answer3) {
                                                case "d" -> {
                                                    System.out.println(getListHabitByDateCreate(idPerson));
                                                    break two;
                                                }
                                                case "s" -> {
                                                    System.out.println(getListHabitByStatus(idPerson));
                                                    break two;
                                                }
                                                default -> wrongInput();
                                            }
                                        }
                                    }
                                    default -> wrongInput();
                                }
                            }
                        } else getErrorHabit();

                    }
                    case "e" -> {
                        break one;
                    }
                    default -> wrongInput();
                }
            }
        }
    }

    public static void getErrorHabit(){
        System.out.println("У вас еще не привычек!");
    }
}
