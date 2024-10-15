package com.lushnikova.homework_1.repository;

import com.lushnikova.homework_1.model.Habit;
import com.lushnikova.homework_1.model.User;
import com.lushnikova.homework_1.model.enums.Repeat;
import com.lushnikova.homework_1.model.enums.Statistics;
import com.lushnikova.homework_1.model.enums.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class UserRepository {
    private static final UserRepository INSTANCE;
    private final CopyOnWriteArrayList<User> people;

    static {
        INSTANCE = new UserRepository();
    }

    private UserRepository() {
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

    public static UserRepository getInstance() {
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

    public synchronized void addHabitByIdUser(UUID idUser, Habit habit) {
        findById(idUser).addHabit(habit);
    }

    public synchronized List<Habit> getHabitsByIdUser(UUID idUser) {
        return findById(idUser).getHabits();
    }

    public synchronized List<Habit> getHabitsByStatusByIdUser(UUID idUser, Status status) {
        return findById(idUser).getHabitsByStatus(status);
    }

    public synchronized List<Habit> getHabitsByLocalDateByIdUser(UUID idUser, LocalDate localDate) {
        return findById(idUser).getHabitsByLocalDate(localDate);
    }

    public synchronized void updateTitleByIdHabitByIdUser(UUID idUser, Long idHabit, String newTitle) {
        findById(idUser).updateTitle(idHabit, newTitle);
    }

    public synchronized void updateDescriptionByIdHabitByIdUser(UUID idUser, Long idHabit, String newDescription) {
        findById(idUser).updateDescription(idHabit, newDescription);
    }

    public synchronized void updateRepeatByIdHabitByIdUser(UUID idUser, Long idHabit, Repeat newRepeat) {
        findById(idUser).updateRepeat(idHabit, newRepeat);
    }

    public synchronized void updateStatusByIdHabitByIdUser(UUID idUser, Long idHabit, Status newStatus) {
        findById(idUser).updateStatus(idHabit, newStatus);
    }

    public synchronized void deleteHabitByIdUser(UUID idUser, Long idHabit) {
        findById(idUser).deleteHabit(idHabit);
    }


    public synchronized List<String> getHabitFulfillmentStatisticsByIdUser(UUID idUser, Statistics statistics, Long idHabit, LocalDate dateFrom) {
        return findById(idUser).getHabitFulfillmentStatistics(statistics, idHabit, dateFrom);
    }

    public synchronized int percentSuccessHabitsByIdUser(UUID idUser, LocalDate dateFrom, LocalDate dateTo) {
        return findById(idUser).percentSuccessHabits(dateFrom, dateTo);
    }

    public void reportHabitByIdUser(UUID idUser, Long idHabit) {
        findById(idUser).reportHabit(idHabit);
    }

    public synchronized void setDoneDatesHabitByIdUser(UUID idUser, Long idHabit) {
        findById(idUser).getHabits().get(Math.toIntExact(idHabit) - 1).setDoneDates();
    }

    public synchronized void setIsActiveByIdUser(UUID idUser, boolean isActive){
        findById(idUser).setActive(isActive);
    }

    public synchronized void switchOnPushNotificationByIdUser(UUID idUser, Long idHabit, LocalTime pushTime) {
        findById(idUser).switchOnPushNotification(idHabit, pushTime);
    }

    public synchronized void switchOffPushNotificationByIdUser(UUID idUser, Long idHabit){
        findById(idUser).switchOffPushNotification(idHabit);
    }

    public synchronized List<User> findAll() {
        return new ArrayList<>(people);
    }


}
