package com.lushnikova.service.impl;

import com.lushnikova.dto.req.HabitRequest;
import com.lushnikova.dto.req.PersonRequest;
import com.lushnikova.dto.resp.HabitResponse;
import com.lushnikova.dto.resp.PersonResponse;
import com.lushnikova.exception.ModelNotFound;
import com.lushnikova.mapper_mapstruct.HabitMapper;
import com.lushnikova.mapper_mapstruct.PersonMapper;
import com.lushnikova.model.Habit;
import com.lushnikova.model.Person;
import com.lushnikova.model.enums.Repeat;
import com.lushnikova.model.enums.Statistics;
import com.lushnikova.model.enums.Status;
import com.lushnikova.service.PersonService;
import com.lushnikova.repository.PersonRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


public class PersonServiceImpl implements PersonService {
    private final HabitMapper habitMapper;
    private final PersonMapper personMapper;
    private final PersonRepository personRepository;

    public PersonServiceImpl(PersonMapper personMapper, PersonRepository personRepository) {
        this.personMapper = personMapper;
        this.personRepository = personRepository;
        this.habitMapper = HabitMapper.INSTANCE;
    }

    @Override
    public PersonResponse save(PersonRequest personRequest) {
        Person person = personMapper.mapToEntity(personRequest);
        return personMapper.mapToResponse(personRepository.save(person));
    }

    @Override
    public PersonResponse findById(UUID id) {
        return personMapper.mapToResponse(personRepository.findById(id));
    }

    @Override
    public void updateName(UUID id, String name) {
        personRepository.updateName(id, name);
    }

    @Override
    public void updateEmail(UUID id, String email) {
        personRepository.updateEmail(id, email);
    }

    @Override
    public void updatePassword(UUID id, String password) {
        personRepository.updatePassword(id, password);
    }

    @Override
    public void delete(UUID id){
        personRepository.delete(id);
    }

    @Override
    public void addHabitByIdPerson(UUID idPerson, HabitRequest habitRequest) throws ModelNotFound {
        Habit habit = habitMapper.mapToEntity(habitRequest);
        long idHabit = getHabitsByIdPerson(idPerson).size() + 1;
        habit.setId(idHabit);
        personRepository.addHabitByIdPerson(idPerson, habit);
    }

    @Override
    public List<HabitResponse> getHabitsByIdPerson(UUID idPerson) throws ModelNotFound {
        return personRepository.getHabitsByIdPerson(idPerson).stream().map(habitMapper::mapToResponse).toList();
    }

    @Override
    public List<HabitResponse> getHabitsByStatusByIdPerson(UUID idPerson, Status status) throws ModelNotFound {
        return personRepository.getHabitsByStatusByIdPerson(idPerson, status).stream().map(habitMapper::mapToResponse).toList();
    }

    @Override
    public List<HabitResponse> getHabitsByLocalDateByIdPerson(UUID idPerson, LocalDate localDate) throws ModelNotFound {
        return personRepository.getHabitsByLocalDateByIdPerson(idPerson, localDate).stream().map(habitMapper::mapToResponse).toList();
    }

    @Override
    public void updateTitleByIdHabitByIdPerson(UUID idPerson, Long idHabit, String newTitle) {
        personRepository.updateTitleByIdHabitByIdPerson(idPerson, idHabit, newTitle);
    }

    @Override
    public void updateDescriptionByIdHabitByIdPerson(UUID idPerson, Long idHabit, String newDescription) {
        personRepository.updateDescriptionByIdHabitByIdPerson(idPerson, idHabit, newDescription);
    }

    @Override
    public void updateRepeatByIdHabitByIdPerson(UUID idPerson, Long idHabit, Repeat newRepeat) {
        personRepository.updateRepeatByIdHabitByIdPerson(idPerson, idHabit, newRepeat);
    }

    @Override
    public void updateStatusByIdHabitByIdPerson(UUID idPerson, Long idHabit, Status newStatus) {
        personRepository.updateStatusByIdHabitByIdPerson(idPerson, idHabit, newStatus);
    }

    @Override
    public void deleteHabitByIdPerson(UUID idPerson, Long idHabit) {
        personRepository.deleteHabitByIdPerson(idPerson, idHabit);
    }

    @Override
    public List<String> getHabitFulfillmentStatisticsByIdPerson(UUID idPerson, Statistics statistics, Long idHabit, LocalDate dateFrom) {
        return personRepository.getHabitFulfillmentStatisticsByIdPerson(idPerson, statistics, idHabit, dateFrom);
    }

    @Override
    public int percentSuccessHabitsByIdPerson(UUID idPerson, LocalDate dateFrom, LocalDate dateTo) {
        return personRepository.percentSuccessHabitsByIdPerson(idPerson, dateFrom, dateTo);
    }

    @Override
    public void reportHabitByIdPerson(UUID idPerson, Long idHabit) {
        personRepository.reportHabitByIdPerson(idPerson, idHabit);
    }

    @Override
    public void setDoneDatesHabitByIdPerson(UUID idPerson, Long idHabit) {
        personRepository.setDoneDatesHabitByIdPerson(idPerson, idHabit);
    }

    @Override
    public List<PersonResponse> findAll() {
        return personRepository.findAll().stream().map(personMapper::mapToResponse).toList();
    }
}
