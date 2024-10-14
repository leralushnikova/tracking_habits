package com.lushnikova.repository;

import com.lushnikova.model.Habit;
import com.lushnikova.model.User;
import com.lushnikova.model.enums.Repeat;
import com.lushnikova.model.enums.Statistics;
import com.lushnikova.model.enums.Status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class PersonRepository {
    private static final PersonRepository INSTANCE;
    private final CopyOnWriteArrayList<User> people;

    static {
        INSTANCE = new PersonRepository();
    }

    private PersonRepository() {
        people = new CopyOnWriteArrayList<>();

        User user1 = new User("Jame", "jame@gmail.com", "jame");

        User user2 = new User("Kirill", "kirill@gmail.com", "kirill");

        Random random = new Random();

        long l = 1L;
        for (int i = 1; i <= 10; i++, l++) {
            int digit = random.nextInt(1, 4);
            Habit habit = new Habit();
            habit.setId(l);
            habit.setTitle("Habit " + i);
            habit.setDescription("Description " + i);
            if (digit == 1) {
                habit.setRepeat(Repeat.DAILY);
                habit.setStatus(Status.CREATED);
            } else if (digit == 2) {
                habit.setRepeat(Repeat.DAILY);
                habit.setStatus(Status.IN_PROGRESS);
            } else {
                habit.setRepeat(Repeat.WEEKLY);
                habit.setStatus(Status.DONE);
            }


            int year = 2024;
            int month = random.nextInt(1, 11);
            int day = random.nextInt(1, 29);
            LocalDate createdAt = LocalDate.of(year, month, day);
            habit.setCreatedAt(createdAt);

            LocalDate date = createdAt;
            for (int j = 0; j < 5; j++) {
                habit.setDoneDates(date);
                date = date.plusDays(random.nextInt(1, 3));
            }

            user1.addHabit(habit);
        }

        long k = 1L;
        for (int i = 11; i <= 20; i++, k++) {
            int digit = random.nextInt(1, 4);
            Habit habit = new Habit();
            habit.setId(k);
            habit.setTitle("Habit " + i);
            habit.setDescription("Description " + i);
            if (digit == 1) {
                habit.setRepeat(Repeat.DAILY);
                habit.setStatus(Status.CREATED);
            } else if (digit == 2) {
                habit.setRepeat(Repeat.DAILY);
                habit.setStatus(Status.IN_PROGRESS);
            } else {
                habit.setRepeat(Repeat.WEEKLY);
                habit.setStatus(Status.DONE);
            }


            int year = 2024;
            int month = random.nextInt(1, 11);
            int day = random.nextInt(1, 29);
            LocalDate createdAt = LocalDate.of(year, month, day);
            habit.setCreatedAt(createdAt);

            LocalDate date = createdAt;
            for (int j = 0; j < 5; j++) {
                habit.setDoneDates(date);
                date = date.plusDays(random.nextInt(1, 3));
            }

            user2.addHabit(habit);
        }

        people.add(user1);
        people.add(user2);
    }

    public static PersonRepository getInstance() {
        return INSTANCE;
    }

    public synchronized User save(User user) {
        people.add(user);
        return user;
    }

    public User findById(UUID id) {
        return people.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    public synchronized void updateName(UUID id, String name) {
      findById(id).setName(name);
    }

    public synchronized void updateEmail(UUID id, String email) {
        findById(id).setEmail(email);
    }

    public synchronized void updatePassword(UUID id, String password) {
        findById(id).setPassword(password);
    }


    public synchronized void delete(UUID id) {
        people.remove(findById(id));
    }


    public synchronized void addHabitByIdPerson(UUID idPerson, Habit habit) {
        findById(idPerson).addHabit(habit);
    }


    public synchronized List<Habit> getHabitsByIdPerson(UUID idPerson) {
        return findById(idPerson).getHabits();
    }

    public synchronized List<Habit> getHabitsByStatusByIdPerson(UUID idPerson, Status status) {
        return findById(idPerson).getHabitsByStatus(status);
    }

    public synchronized List<Habit> getHabitsByLocalDateByIdPerson(UUID idPerson, LocalDate localDate) {
        return findById(idPerson).getHabitsByLocalDate(localDate);
    }

    public synchronized void updateTitleByIdHabitByIdPerson(UUID idPerson, Long idHabit, String newTitle) {
        findById(idPerson).updateTitle(idHabit, newTitle);
    }

    public synchronized void updateDescriptionByIdHabitByIdPerson(UUID idPerson, Long idHabit, String newDescription) {
        findById(idPerson).updateDescription(idHabit, newDescription);
    }

    public synchronized void updateRepeatByIdHabitByIdPerson(UUID idPerson, Long idHabit, Repeat newRepeat) {
        findById(idPerson).updateRepeat(idHabit, newRepeat);
    }

    public synchronized void updateStatusByIdHabitByIdPerson(UUID idPerson, Long idHabit, Status newStatus) {
        findById(idPerson).updateStatus(idHabit, newStatus);
    }

    public synchronized void deleteHabitByIdPerson(UUID idPerson, Long idHabit) {
        findById(idPerson).deleteHabit(idHabit);
    }


    public synchronized List<String> getHabitFulfillmentStatisticsByIdPerson(UUID idPerson, Statistics statistics, Long idHabit, LocalDate dateFrom) {
        return findById(idPerson).getHabitFulfillmentStatistics(statistics, idHabit, dateFrom);
    }

    public synchronized int percentSuccessHabitsByIdPerson(UUID idPerson, LocalDate dateFrom, LocalDate dateTo) {
        return findById(idPerson).percentSuccessHabits(dateFrom, dateTo);
    }

    public void reportHabitByIdPerson(UUID idPerson, Long idHabit) {
        findById(idPerson).reportHabit(idHabit);
    }

    public void setDoneDatesHabitByIdPerson(UUID idPerson, Long idHabit) {
        findById(idPerson).getHabits().get(Math.toIntExact(idHabit) - 1).setDoneDates();
    }

    public void setIsActiveByIdPerson(UUID idPerson, boolean isActive){
        findById(idPerson).setActive(isActive);
    }

    public synchronized List<User> findAll() {
        return new ArrayList<>(people);
    }


}
