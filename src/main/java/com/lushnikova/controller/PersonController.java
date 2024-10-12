package com.lushnikova.controller;

import com.lushnikova.dto.req.PersonRequest;
import com.lushnikova.dto.req.UserRequest;
import com.lushnikova.dto.resp.PersonResponse;
import com.lushnikova.exception.ModelNotFound;
import com.lushnikova.mapper.PersonMapper;
import com.lushnikova.middleware.PersonMiddleware;
import com.lushnikova.model.Person;
import com.lushnikova.repository.PersonRepository;
import com.lushnikova.service.PersonService;

import java.util.Scanner;

public class PersonController {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final PersonService personService;
    private final PersonMiddleware personMiddleware;


    public PersonController(PersonRepository personRepository, PersonMapper personMapper, PersonService personService, PersonMiddleware personMiddleware) {
        this.personRepository = personRepository;
        this.personMapper = personMapper;
        this.personService = personService;
        this.personMiddleware = personMiddleware;
    }

    //регистрация нового пользователя
    public PersonResponse createPerson(){
        Person person;
        PersonRequest personRequest = new PersonRequest();

        System.out.println("Введите свое имя: ");
        personRequest.setName(scannerString());

        while (true) {
            String email = email();

            if (personMiddleware.checkEmail(email)) {

                personRequest.setEmail(email);

                while (true){
                    System.out.println("Пароль должен содержать 8 символов латинского алфавита, " +
                            "минимум одну заглавную букву, " +
                            "одну маленькую букву либо спец. символ, либо цифру.");
                    String password = password();

                    if (personMiddleware.checkPassword(password)) {
                        personRequest.setPassword(password);

                        person = personService.save(personRequest);
                        return personMapper.mapToResponse(person);
                    } else wrongInput();
                }

            } else System.out.println("Данная почта уже существует");
        }
    }

    //авторизация пользователя
    public PersonResponse getPersonAfterAuthentication(){
        Person person;
        while (true){
            String email = email();

            if(!personMiddleware.checkEmail(email)){
                UserRequest userRequest = new UserRequest();
                userRequest.setEmail(email);
                while (true){
                    String password = password();
                    userRequest.setPassword(password);

                    person = personMapper.foundToPerson(userRequest);
                    if (person != null) {
                        return personMapper.mapToResponse(person);
                    } else wrongInput();
                }

            } else System.out.println("Данный пользователь не зарегистрирован!");
        }
    }

    public PersonResponse getPerson(Long id) throws ModelNotFound {
        return personService.findById(id);
    }

    //обновление пользователя
    public PersonResponse updatePerson(Long idPerson, Person person) throws ModelNotFound {
        return personService.update(idPerson, person);
    }

    //удаление пользователя
    public void delete(Long id) throws ModelNotFound {
        personService.delete(id);
    }

    public PersonResponse editPerson(Long idPerson) throws ModelNotFound {
        Person person = personRepository.findById(idPerson);
        while (true) {
            System.out.println("Вы хотите изменить имя(n), email (e), пароль(p), удалить профиль(d) или посмотреть данные(l)?");
            String answer = scannerString();
            switch (answer) {
                case "n" -> {
                    System.out.println("Введите новое имя: ");
                    person.setName(scannerString());
                    return updatePerson(idPerson, person);
                }
                case "e" -> {
                    person.setEmail(email());
                    return updatePerson(idPerson, person);
                }
                case "p" -> {
                    person.setPassword(password());
                    return updatePerson(idPerson, person);
                }
                case "d" -> {
                    delete(idPerson);
                    System.out.println("Ваш профиль удален");
                    return null;
                }
                case "l" -> System.out.println(getPerson(idPerson));
                default -> wrongInput();
            }
        }
    }

    public static String email() {
        System.out.println("Введите свою почту: ");
        return scannerString();
    }

    public static String password(){
        System.out.println("Введите свой пароль: ");
        return scannerString();
    }

    public static String scannerString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void wrongInput(){
        System.out.println("Неправильный ввод!");
    }
}
