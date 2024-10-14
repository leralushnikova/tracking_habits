package com.lushnikova;

import com.lushnikova.controller.HabitController;
import com.lushnikova.controller.PersonController;
import com.lushnikova.dto.resp.PersonResponse;
import com.lushnikova.exception.ModelNotFound;
import com.lushnikova.mapper_mapstruct.PersonMapper;
import com.lushnikova.middleware.DateMiddleware;
import com.lushnikova.middleware.PersonMiddleware;
import com.lushnikova.repository.PersonRepository;
import com.lushnikova.service.PersonService;
import com.lushnikova.service.impl.PersonServiceImpl;

import java.text.ParseException;
import java.util.UUID;

import static com.lushnikova.controller.PersonController.scannerString;
import static com.lushnikova.controller.PersonController.wrongInput;


public class Main {


    private static final PersonMapper personMapper = PersonMapper.INSTANCE;
    private static final DateMiddleware dateMiddleware = new DateMiddleware();
    private static final PersonMiddleware personMiddleware = new PersonMiddleware();
    private static final PersonRepository personRepository = PersonRepository.getInstance();
    private static final PersonService personService = new PersonServiceImpl(personMapper, personRepository);
    private static final PersonController personController = new PersonController(personService, personMiddleware);
    private static final HabitController habitController = new HabitController(personService, dateMiddleware);

    public static void main(String[] args) throws ModelNotFound, ParseException {

        PersonResponse personResponse;
        while (true){
            System.out.println("Вы хотите войти(in), зарегистрироваться(up) или выйти(exit)?[in/up/exit]");
            String input = scannerString();

            switch (input) {
                case ("in") -> {
                    personResponse = personController.getPersonAfterAuthentication();
                    crudHabit(personResponse.getId());
                }
                case ("up") -> {
                    personResponse = personController.createPerson();
                    crudHabit(personResponse.getId());
                }
                case ("exit") -> {return;}

                default -> wrongInput();
            }
        }

//        System.out.println(personService.findById(personResponse.getId()));
    }

    public static void crudHabit(UUID idPerson) throws ModelNotFound, ParseException {
        while (true) {
            PersonResponse personResponse = personController.getPerson(idPerson);
            if (personResponse != null) {
                System.out.println();
                System.out.println("Ваши данные: ");
                System.out.println("Имя =  " + personResponse.getName());
                System.out.println("Почта =  " + personResponse.getEmail());
                System.out.println("Пароль =  " + personResponse.getPassword());
                System.out.println();
                System.out.println("Вы хотите редактировать профиль?[y/n]");
                String answer = scannerString();

                if (answer.equals("y")) {
                    personController.editPerson(personResponse.getId());
                }
                if (answer.equals("n")) {
                    habitController.crudHabits(personResponse.getId());
                    habitController.addHabitDoneDates(idPerson);
                }
                if(!answer.equals("y") && !answer.equals("n")) wrongInput();
            }
            if(personResponse == null) return;
        }
    }


}
