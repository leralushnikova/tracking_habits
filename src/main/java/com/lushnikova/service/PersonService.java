package com.lushnikova.service;

import com.lushnikova.dto.req.PersonRequest;
import com.lushnikova.dto.resp.PersonResponse;
import com.lushnikova.exception.ModelNotFound;
import com.lushnikova.model.Person;

import java.util.List;

public interface PersonService {
    Person save(PersonRequest personRequest);
    PersonResponse findById(Long id) throws ModelNotFound;
    PersonResponse update(Long id, Person person) throws ModelNotFound;
    void delete(Long id) throws ModelNotFound;
    List<PersonResponse> findAll();
    void addHabitOfPerson(Long idPerson, Long idHabit) throws ModelNotFound;
    void deleteHabitOfPerson(Long idPerson, Long idHabit) throws ModelNotFound;
}
