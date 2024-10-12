package com.lushnikova.service.impl;

import com.lushnikova.dto.req.PersonRequest;
import com.lushnikova.dto.resp.PersonResponse;
import com.lushnikova.mapper.PersonMapper;
import com.lushnikova.model.Habit;
import com.lushnikova.model.Person;
import com.lushnikova.repository.HabitRepository;
import com.lushnikova.service.PersonService;
import com.lushnikova.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;

public class PersonServiceImpl implements PersonService {
    private final PersonRepository personRepository;
    private final HabitRepository habitRepository;
    private final PersonMapper personMapper;

    public PersonServiceImpl(PersonRepository personRepository, HabitRepository habitRepository, PersonMapper personMapper) {
        this.personRepository = personRepository;
        this.habitRepository = habitRepository;
        this.personMapper = personMapper;
    }

    @Override
    public Person save(PersonRequest personRequest) {
        Person person = personMapper.mapToEntity(personRequest);
        personRepository.save(person);
        return person;
    }

    @Override
    public PersonResponse findById(Long id){
        return personMapper.mapToResponse(personRepository.findById(id));
    }

    @Override
    public PersonResponse update(Long id, Person person){
        return personMapper.mapToResponse(personRepository.update(id, person));
    }

    @Override
    public void delete(Long id){
        personRepository.delete(id);
    }

    @Override
    public List<PersonResponse> findAll() {
        return personRepository.findAll().stream().map(personMapper::mapToResponse).toList();
    }

    @Override
    public void addHabitOfPerson(Long idPerson, Long idHabit){
        Habit habit = habitRepository.findById(idHabit);
        Person person = personRepository.findById(idPerson);

        List<Habit> habitList;
        if (person.getHabits() == null) {
            habitList = new ArrayList<>();
        } else habitList = person.getHabits();
        habitList.add(habit);
        personRepository.findById(idPerson).setHabits(habitList);
    }

    public void deleteHabitOfPerson(Long idPerson, Long idHabit) {
        Habit habit = habitRepository.findById(idHabit);
        Person person = personRepository.findById(idPerson);

        List<Habit> habitList = person.getHabits();
        habitList.remove(habit);
        personRepository.findById(idPerson).setHabits(habitList);

    }


}
