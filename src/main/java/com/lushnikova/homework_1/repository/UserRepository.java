package com.lushnikova.homework_1.repository;

import com.lushnikova.homework_1.model.Habit;
import com.lushnikova.homework_1.model.User;
import com.lushnikova.homework_1.model.ENUM.Repeat;
import com.lushnikova.homework_1.model.ENUM.Statistics;
import com.lushnikova.homework_1.model.ENUM.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

/**
 * Класс Repository для пользователей.
 * Используется паттерн Singleton
 */
public class UserRepository {
    private static final UserRepository INSTANCE;

    /** Поле списка пользователй */
    private final Set<User> users;

    /** Статический блок инициализации по созданию репозитория единожды */
    static {
        INSTANCE = new UserRepository();
    }

    /**
     * Приватный пустой конструктор для создания нового объекта
     * и созданий списка пользователей
     */
    private UserRepository() {
        users = new HashSet<>();

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

        users.add(user1);
        users.add(user2);
    }

    /** Возвращение экземпляра репозитория*/
    public static UserRepository getInstance() {
        return INSTANCE;
    }

    /**
     * Процедура сохранения пользователя в список {@link UserRepository#users}
     * @param user - объект пользователя
     */
    public synchronized User save(User user) {
        users.add(user);
        return user;
    }

    /**
     * Функция получения пользователя из списка {@link UserRepository#users}
     * @param id - id пользователя
     * @return возвращает объект пользователя
     */
    public User findById(UUID id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Процедура обновления имени пользователя {@link User#setName(String)},
     * пользователя берем из {@link UserRepository#findById(UUID)}
     * @param id - id пользователя
     * @param name - новое имя пользователя
     */
    public synchronized void updateName(UUID id, String name) {
        findById(id).setName(name);
    }

    /**
     * Процедура обновления почты пользователя {@link User#setEmail(String)},
     * пользователя берем из {@link UserRepository#findById(UUID)}
     * @param id - id пользователя
     * @param email - новое имя пользователя
     */
    public synchronized void updateEmail(UUID id, String email) {
        findById(id).setEmail(email);
    }

    /**
     * Процедура обновления пароля пользователя {@link User#setPassword(String)},
     * пользователя берем из {@link UserRepository#findById(UUID)}
     * @param id - id пользователя
     * @param password - новый пароль пользователя
     */
    public synchronized void updatePassword(UUID id, String password) {
        findById(id).setPassword(password);
    }

    /**
     * Процедура удаления пользователя,
     * пользователя берем из {@link UserRepository#findById(UUID)}
     * @param id - id пользователя
     */
    public synchronized void delete(UUID id) {
        users.remove(findById(id));
    }

    /**
     * Процедура добавления привычки пользователя {@link User#addHabit(Habit)},
     * пользователя берем из {@link UserRepository#findById(UUID)}
     * @param idUser - id пользователя
     * @param habit - привычка
     */
    public synchronized void addHabitByIdUser(UUID idUser, Habit habit) {
        findById(idUser).addHabit(habit);
    }

    /**
     * Функция получения списка привычек пользователя {@link User#getHabits},
     * пользователя берем из {@link UserRepository#findById(UUID)}
     * @param idUser - id пользователя
     * @return возвращает список привычек пользователя
     */
    public synchronized List<Habit> getHabitsByIdUser(UUID idUser) {
        return findById(idUser).getHabits();
    }

    /**
     * Функция получения списка привычек пользователя по статусу {@link User#getHabitsByStatus(Status)},
     * пользователя берем из {@link UserRepository#findById(UUID)}
     * @param idUser - id пользователя
     * @param status - статус привычки
     * @return возвращает список привычек пользователя по статусу
     */
    public synchronized List<Habit> getHabitsByStatusByIdUser(UUID idUser, Status status) {
        return findById(idUser).getHabitsByStatus(status);
    }

    /**
     * Функция получения списка привычек пользователя по дате создания {@link User#getHabitsByLocalDate(LocalDate)},
     * пользователя берем из {@link UserRepository#findById(UUID)}
     * @param idUser - id пользователя
     * @param localDate - дата создания
     * @return возвращает список привычек пользователя по дате создания
     */
    public synchronized List<Habit> getHabitsByLocalDateByIdUser(UUID idUser, LocalDate localDate) {
        return findById(idUser).getHabitsByLocalDate(localDate);
    }

    /**
     * Процедура обновления названия привычки пользователя {@link User#updateTitle(Long, String)},
     * пользователя берем из {@link UserRepository#findById(UUID)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param newTitle - новое название привычки
     */
    public synchronized void updateTitleByIdHabitByIdUser(UUID idUser, Long idHabit, String newTitle) {
        findById(idUser).updateTitle(idHabit, newTitle);
    }

    /**
     * Процедура обновления описания привычки пользователя {@link User#updateDescription(Long, String)},
     * пользователя берем из {@link UserRepository#findById(UUID)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param newDescription - новое описания привычки
     */
    public synchronized void updateDescriptionByIdHabitByIdUser(UUID idUser, Long idHabit, String newDescription) {
        findById(idUser).updateDescription(idHabit, newDescription);
    }

    /**
     * Процедура обновления частоты выполнения привычки пользователя {@link User#updateRepeat(Long, Repeat)},
     * пользователя берем из {@link UserRepository#findById(UUID)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param newRepeat - новая частота выполнения
     */
    public synchronized void updateRepeatByIdHabitByIdUser(UUID idUser, Long idHabit, Repeat newRepeat) {
        findById(idUser).updateRepeat(idHabit, newRepeat);
    }

    /**
     * Процедура обновления статуса привычки пользователя {@link User#updateStatus(Long, Status)},
     * пользователя берем из {@link UserRepository#findById(UUID)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param newStatus - новый статс
     */
    public synchronized void updateStatusByIdHabitByIdUser(UUID idUser, Long idHabit, Status newStatus) {
        findById(idUser).updateStatus(idHabit, newStatus);
    }

    /**
     * Процедура удаления привычки пользователя {@link User#deleteHabit(Long)},
     * пользователя берем из {@link UserRepository#findById(UUID)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     */
    public synchronized void deleteHabitByIdUser(UUID idUser, Long idHabit) {
        findById(idUser).deleteHabit(idHabit);
    }

    /**
     * Функция получения генерации статистики выполнения привычки {@link User#getHabitFulfillmentStatistics(Statistics, Long, LocalDate)},
     * пользователя берем из {@link UserRepository#findById(UUID)}
     * @param idUser - id пользователя
     * @param statistics - указания периода (DAY - за день, WEEK - за неделю, MONTH - месяц)
     * @param idHabit - id привычки
     * @param dateFrom - дата, с какого числа нужно посчитать статистику
     * @return возвращает статистики выполнения привычки
     */
    public synchronized List<String> getHabitFulfillmentStatisticsByIdUser(UUID idUser, Statistics statistics, Long idHabit, LocalDate dateFrom) {
        return findById(idUser).getHabitFulfillmentStatistics(statistics, idHabit, dateFrom);
    }

    /**
     * Функция получения процента успешного выполнения привычек за определенный период {@link User#percentSuccessHabits(LocalDate, LocalDate)},
     * пользователя берем из {@link UserRepository#findById(UUID)}
     * @param idUser - id пользователя
     * @param dateFrom - с какого числа
     * @param dateTo - по какое число
     * @return возвращает процент успешного выполнения привычек
     */
    public synchronized int percentSuccessHabitsByIdUser(UUID idUser, LocalDate dateFrom, LocalDate dateTo) {
        return findById(idUser).percentSuccessHabits(dateFrom, dateTo);
    }

    /**
     * Процедура формирование отчета для пользователя по прогрессу выполнения привычки {@link User#reportHabit(Long)},
     * пользователя берем из {@link UserRepository#findById(UUID)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     */
    public void reportHabitByIdUser(UUID idUser, Long idHabit) {
        findById(idUser).reportHabit(idHabit);
    }

    /**
     * Процедура отметки даты, когда выполнялась привычка {@link Habit#setDoneDates}
     * пользователя берем из {@link UserRepository#findById(UUID)}
     * @param idUser - id пользователя
     */
    public synchronized void setDoneDatesHabitByIdUser(UUID idUser, Long idHabit) {
        findById(idUser).getHabits().get(Math.toIntExact(idHabit) - 1).setDoneDates();
    }

    /**
     * Процедура определения значения поля {@link User#setActive(boolean)},
     * пользователя берем из {@link UserRepository#findById(UUID)}
     * @param idUser - id пользователя
     * @param isActive - блокировка
     */
    public synchronized void setIsActiveByIdUser(UUID idUser, boolean isActive){
        findById(idUser).setActive(isActive);
    }

    /**
     * Процедура включения отправки уведомления привычки в указанное время {@link User#switchOnPushNotification(Long, LocalTime)},
     * пользователя берем из {@link UserRepository#findById(UUID)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param pushTime - время уведомления привычки
     */
    public synchronized void switchOnPushNotificationByIdUser(UUID idUser, Long idHabit, LocalTime pushTime) {
        findById(idUser).switchOnPushNotification(idHabit, pushTime);
    }

    /**
     * Процедура выключения уведомления привычки {@link User#switchOffPushNotification(Long)},
     * пользователя берем из {@link UserRepository#findById(UUID)}
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     */
    public synchronized void switchOffPushNotificationByIdUser(UUID idUser, Long idHabit){
        findById(idUser).switchOffPushNotification(idHabit);
    }

    /**
     * Функция получения списка администраторов {@link UserRepository#users}
     * @return возвращает копию списка администраторов
     */
    public synchronized Set<User> findAll() {
        return users;
    }


}
