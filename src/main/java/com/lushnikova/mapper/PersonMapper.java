package com.lushnikova.mapper;

import com.lushnikova.dto.req.PersonRequest;
import com.lushnikova.dto.req.UserRequest;
import com.lushnikova.dto.resp.PersonResponse;
import com.lushnikova.model.Person;
import com.lushnikova.repository.PersonRepository;

import java.util.List;

public class PersonMapper {
    private final PersonRepository personRepository;
    private final HabitMapper habitMapper;

    public PersonMapper(PersonRepository personRepository, HabitMapper habitMapper) {
        this.personRepository = personRepository;
        this.habitMapper = habitMapper;
    }

    public Person mapToEntity(PersonRequest personRequest) {
        if (personRequest == null) return null;

        Person person = new Person();
        person.setName(personRequest.getName());
        person.setEmail(personRequest.getEmail());
        person.setPassword(personRequest.getPassword());
        return person;
    }

    public PersonResponse mapToResponse(Person person) {
        if (person == null) return null;

        PersonResponse personResponse = new PersonResponse();
        personResponse.setId(person.getId());
        personResponse.setName(person.getName());
        personResponse.setEmail(person.getEmail());
        personResponse.setPassword(person.getPassword());

        if (person.getHabits() != null) {
            personResponse.setHabits(person.getHabits().stream().map(habitMapper::mapToResponse).toList());
        }
        return personResponse;
    }

    public Person foundToPerson(UserRequest userRequest) {
        List<Person> list = personRepository.findAll();
        Person person = null;
        for(Person p : list) {
            if(userRequest.getEmail().equals(p.getEmail()) && userRequest.getPassword().equals(p.getPassword())) {
                person = p;
            }
        }
        return person;
    }
}
