package com.lushnikova.controller;

import com.lushnikova.dto.req.PersonRequest;
import com.lushnikova.dto.resp.PersonResponse;
import com.lushnikova.middleware.PersonMiddleware;
import com.lushnikova.service.PersonService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class PersonController {

    private final PersonService personService;
    private final PersonMiddleware personMiddleware;


    public PersonController(PersonService personService, PersonMiddleware personMiddleware) {
        this.personService = personService;
        this.personMiddleware = personMiddleware;
    }

    //регистрация нового пользователя
    public PersonResponse createPerson() {
        PersonRequest personRequest = new PersonRequest();
        System.out.println("Введите свое имя: ");
        personRequest.setName(scannerString());

        while (true) {
            String email = email();

            if (checkEmail(email) != null) System.out.println("Данная почта уже существует");
            else {
                personRequest.setEmail(email);

                while (true) {
                    System.out.println("Пароль должен содержать 8 символов латинского алфавита, " +
                            "минимум одну заглавную букву, " +
                            "одну маленькую букву либо спец. символ, либо цифру.");
                    String password = password();

                    if (personMiddleware.checkPassword(password)) {
                        personRequest.setPassword(password);

                        return personService.save(personRequest);
                    } else wrongInput();
                }
            }
        }
    }

    //авторизация пользователя
    public PersonResponse getPersonAfterAuthentication() {
        while (true){
            String email = email();
            UUID idPersonFromCheckEmail = checkEmail(email);

            if(idPersonFromCheckEmail != null){
                return recursionByPassword(idPersonFromCheckEmail);
            } else System.out.println("Данный пользователь не зарегистрирован");
        }
    }

    private PersonResponse recursionByPassword(UUID idPersonFromCheckEmail){

        String password = password();

        PersonResponse personFromService = personService.findById(idPersonFromCheckEmail);
        if (personMiddleware.checkPassword(password, personFromService)) {
            return personFromService;
        } else {
            System.out.println("Пароль введен не верно! Хотите восстановить пароль(y) или попробовать еще попытку(n)? [y/n]");
            String answer = scannerString();

            switch (answer) {
                case "y" -> {
                    System.out.println("Введите новый пароль: ");
                    String newPassword = scannerString();
                    personService.updatePassword(idPersonFromCheckEmail, newPassword);
                    return personService.findById(idPersonFromCheckEmail);
                }
                case "n" -> recursionByPassword(idPersonFromCheckEmail);
                default -> wrongInput();
            }
        }
        return personFromService;
    }

    public PersonResponse getPerson(UUID id) {
        return personService.findById(id);
    }


    public void editPerson(UUID idPerson) {
        while (true) {
            System.out.println("Вы хотите изменить имя(n), email (e), пароль(p), удалить профиль(d), выход(exit)?");
            String answer = scannerString();
            switch (answer) {
                case "n" -> {
                    System.out.println("Введите новое имя: ");
                    String name = scannerString();
                    personService.updateName(idPerson, name);
                }
                case "e" -> {
                    String email = scannerString();
                    personService.updateEmail(idPerson, email);
                }
                case "p" -> {
                    String password = scannerString();
                    personService.updatePassword(idPerson, password);
                }
                case "d" -> {
                    personService.delete(idPerson);
                    System.out.println("Ваш профиль удален");
                    return;
                }
                case "exit" -> {
                    return;
                }
                default -> wrongInput();
            }
        }
    }

    public static String email() {
        System.out.println("Введите свою почту: ");
        return scannerString();
    }

    public static String password() {
        System.out.println("Введите свой пароль: ");
        return scannerString();
    }

    public static String scannerString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static void wrongInput() {
        System.out.println("Неправильный ввод!");
    }

    private List<PersonResponse> listPeople() {
        return personService.findAll();
    }

    private UUID checkEmail(String email){
        for (PersonResponse personResponse : listPeople()) {
            if (personMiddleware.checkEmail(email, personResponse)) {
                return personResponse.getId();
            }
        }
        return null;
    }

}
