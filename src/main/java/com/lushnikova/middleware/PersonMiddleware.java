package com.lushnikova.middleware;

import com.lushnikova.consts.RegexConsts;
import com.lushnikova.model.Person;
import com.lushnikova.repository.PersonRepository;

import java.util.List;

public class PersonMiddleware {
    private final PersonRepository repository;

    public PersonMiddleware(PersonRepository repository) {
        this.repository = repository;
    }

    //проверка почты
    public boolean checkEmail(String email) {
        List<Person> persons = repository.findAll();
        for (Person p : persons) {
            if(p.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    //проверка пароля
    public boolean checkPassword(String password) {
        return password.matches(RegexConsts.REGEX_PASSWORD);
    }

}
