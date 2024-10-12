package com.lushnikova;

import com.lushnikova.controller.HabitController;
import com.lushnikova.controller.PersonController;
import com.lushnikova.dto.resp.PersonResponse;
import com.lushnikova.exception.ModelNotFound;
import com.lushnikova.mapper.HabitMapper;
import com.lushnikova.mapper.PersonMapper;
import com.lushnikova.middleware.PersonMiddleware;
import com.lushnikova.repository.HabitRepository;
import com.lushnikova.repository.PersonRepository;
import com.lushnikova.service.HabitService;
import com.lushnikova.service.PersonService;
import com.lushnikova.service.impl.HabitServiceImpl;
import com.lushnikova.service.impl.PersonServiceImpl;

import static com.lushnikova.controller.PersonController.*;

public class Main {

    static final PersonRepository personRepository = new PersonRepository();
    static final HabitRepository habitRepository = new HabitRepository();
    static final HabitMapper habitMapper = new HabitMapper();
    static final PersonMapper personMapper = new PersonMapper(personRepository, habitMapper);
    static final HabitService habitService = new HabitServiceImpl(habitRepository, habitMapper, personRepository);
    static final PersonService personService = new PersonServiceImpl(personRepository, habitRepository, personMapper);
    static final PersonMiddleware personMiddleware = new PersonMiddleware(personRepository);
    static final PersonController personController = new PersonController(personRepository, personMapper, personService, personMiddleware);
    static final HabitController habitController = new HabitController(personRepository, habitService, personService, personController);

    public static void main(String[] args) throws ModelNotFound {

        PersonResponse personResponse;
        while (true){
            System.out.println("Вы хотите войти(in) или зарегистрироваться(up)?[in/up]");
            String input = scannerString();

            if (input.equals("in")) {
                personResponse = personController.getPersonAfterAuthentication();
                break;
            }
            if (input.equals("up")) {
                personResponse = personController.createPerson();
                break;
            }
            wrongInput();
        }

        while (true) {
            System.out.println("Вы хотите редактировать профиль?[y/n]");
            String answer = scannerString();

            if (answer.equals("y")) {
                personResponse = personController.editPerson(personResponse.getId());
            }
            if (answer.equals("n")) {
                habitController.crudHabits(personResponse.getId());
                break;
            }
            if(!answer.equals("y")) wrongInput();
        }

        System.out.println(personService.findById(personResponse.getId()));
    }

}
