package com.lushnikova.repository;

import com.lushnikova.model.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonRepository {
    private final List<Person> people;

    public PersonRepository() {
        people = new ArrayList<>();

        Person admin = new Person();
        admin.setId(1L);
        admin.setName("admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("admin");

        Person person1 = new Person();
        person1.setId(2L);
        person1.setName("Jame");
        person1.setEmail("jame@gmail.com");
        person1.setPassword("ldkgAj38er");


        Person person2 = new Person();
        person2.setName("Kirill");
        person2.setEmail("kirill@yandex.com");
        person2.setPassword("dkjaTlkds55");

        save(admin);
        save(person1);
        save(person2);
    }

    public void save(Person person) {
        person.setId((long) (people.size() + 1));
        people.add(person);
    }

    public Person findById(Long id) {
        return people.stream().filter(person -> person.getId().equals(id)).findFirst().orElse(null);
    }

    public Person update(Long id, Person person) {
        for (int i = 0; i < people.size(); i++) {
            if(id.equals(people.get(i).getId())) {
                person.setId(id);
                people.set(i, person);
            }
        }
        return person;
    }

    public void delete(Long id) {
        people.remove(findById(id));
        for (int i = 0; i < people.size(); i++) {
            people.get(i).setId((long) (i + 1));
        }
    }

    public List<Person> findAll() {
        return people;
    }


}
