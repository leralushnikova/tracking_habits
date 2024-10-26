package com.lushnikova.homework_3.model;

import com.lushnikova.homework_3.model.ENUM.Repeat;
import com.lushnikova.homework_3.model.ENUM.Statistics;
import com.lushnikova.homework_3.model.ENUM.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Класс Пользователя
 */
public class User {
    /** Поле уникальный идентификатор */
    private Long id;

    /** Поле имя */
    private String name;

    /** Поле почта */
    private String email;

    /** Поле пароль */
    private String password;

    /**
     * Поле блокировка пользователя
     * isActive = true - пользователя активен
     * isActive = false - пользователя заблокирован
     */
    private boolean isActive;

    /** Список привычек пользователя*/
    private final List<Habit> habits = new ArrayList<>();

    /**
     * Нестатический блок инициализации для генерации уникального идентификатора
     * и активации пользователя
     */
    {
        isActive = true;
    }

    /**
     * Пустой конструктор для создания нового объекта
     */
    public User() {
    }

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param name - имя
     * @param email - почта
     * @param password - пароль
     */
    public User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }


    /**
     * Функция получения значения поля {@link User#id}
     * @return возвращает идентификатор пользователя
     */
    public Long getId() {
        return id;
    }

    /**
     * Процедура определения значения поля {@link User#id}
     * @param id - идентификатор
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Функция получения значения поля {@link User#name}
     * @return возвращает имя пользователя
     */
    public String getName() {
        return name;
    }

    /**
     * Процедура определения значения поля {@link User#name}
     * @param name - имя
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Функция получения значения поля {@link User#email}
     * @return возвращает почту пользователя
     */
    public String getEmail() {
        return email;
    }

    /**
     * Процедура определения значения поля {@link User#email}
     * @param email - почта
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Функция получения значения поля {@link User#password}
     * @return возвращает пароль пользователя
     */
    public String getPassword() {
        return password;
    }

    /**
     * Процедура определения значения поля {@link User#password}
     * @param password - пароль
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Процедура добавления привычки в список привычек для поля {@link User#habits}
     * @param habit - привычка
     */
    public void addHabit(Habit habit) {
        habit.setId((long) (habits.size() + 1));
        habit.setStatus(Status.CREATED);
        if (habit.getCreatedAt() == null) {
            habit.setCreatedAt(LocalDate.now());
        }
        habits.add(habit);
    }

    /**
     * Функция получения списка привычек {@link User#habits}
     * @return возвращает список привычек пользователя
     */
    public List<Habit> getHabits() {
        return habits;
    }

    /**
     * Функция получения значения поля {@link User#isActive}
     * @return возвращает заблокирован или активен пользователь
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Процедура определения значения поля {@link User#isActive}
     * @param active - блокировка
     */
    public void setActive(boolean active) {
        isActive = active;
    }

    /**
     * Функция получения списка привычек пользователя по статусу
     * @param status - статус привычки
     * @return возвращает список привычек пользователя по статусу
     */
    public List<Habit> getHabitsByStatus(Status status) {
        return habits.stream().filter(el -> el.getStatus().equals(status)).toList();
    }

    /**
     * Функция получения списка привычек пользователя по дате создания
     * @param localDate - дата создания
     * @return возвращает список привычек пользователя по дате создания
     */
    public List<Habit> getHabitsByLocalDate(LocalDate localDate) {
        return habits.stream().filter(el -> el.getCreatedAt().toString().equals(localDate.toString())).toList();
    }

    /**
     * Функция получения привычки из поля {@link User#habits}
     * @param id - идентификатор привычки
     * @return возвращает привычку пользователя
     */
    public Habit findById(Long id){
        return habits.stream().filter(habit -> habit.getId().equals(id)).findFirst().orElse(null);
    }

    /**
     * Процедура обновления названия привычки пользователя {@link Habit#setTitle(String)}
     * @param idHabit - id привычки
     * @param newTitle - новое название привычки
     */
    public void updateTitle(Long idHabit, String newTitle){
        findById(idHabit).setTitle(newTitle);
    }

    /**
     * Процедура обновления описания привычки пользователя {@link Habit#setDescription(String)}
     * @param idHabit - id привычки
     * @param newDescription - новое описания привычки
     */
    public void updateDescription(Long idHabit, String newDescription){
        findById(idHabit).setDescription(newDescription);
    }

    /**
     * Процедура обновления частоты выполнения привычки пользователя {@link Habit#setRepeat(Repeat)}
     * @param idHabit - id привычки
     * @param newRepeat - новая частота выполнения
     */
    public void updateRepeat(Long idHabit, Repeat newRepeat){
        findById(idHabit).setRepeat(newRepeat);
    }

    /**
     * Процедура обновления статуса привычки пользователя {@link Habit#setStatus(Status)}
     * @param idHabit - id привычки
     * @param newStatus - новый статс
     */
    public void updateStatus(Long idHabit, Status newStatus){
        findById(idHabit).setStatus(newStatus);
    }

    /**
     * Процедура удаления привычки пользователя из списка {@link User#habits}
     * @param idHabit - id привычки
     */
    public synchronized void deleteHabit(Long idHabit){
        habits.removeIf(habit -> habit.getId().equals(idHabit));
    }

    /**
     * Функция получения генерации статистики выполнения привычки за указанный период (день, неделя, месяц)
     * @param statistics - указания периода (DAY - за день, WEEK - за неделю, MONTH - месяц)
     * @param idHabit - id привычки
     * @param dateFrom - дата, с какого числа нужно посчитать статистику
     * @return возвращает статистики выполнения привычки
     */
    public synchronized List<String> getHabitFulfillmentStatistics(Statistics statistics, Long idHabit, LocalDate dateFrom) {
        int days = 0;
        switch (statistics) {
            case DAY -> days = 1;
            case WEEK -> days = 7;
            case MONTH -> days = 31; //потом учесть февраль
        }

        List<String> list = new ArrayList<>();
        LocalDate dateTo = dateFrom.plusDays(days);

        for (Habit habit : habits) {
            if (habit.getId().equals(idHabit)) {
                while (!dateTo.isEqual(dateFrom)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(dateFrom).append("\t");

                    Set<LocalDate> listDates = habit.getDoneDates();
                    boolean flag = false;
                    for (LocalDate date : listDates) {
                        if (dateFrom.equals(date)) {
                            flag = true;
                            break;
                        }
                    }
                    if(flag) sb.append(" + ");
                    else sb.append(" - ");
                    list.add(sb.toString());
                    dateFrom = dateFrom.plusDays(1);
                }
            }
        }
        return list;
    }


    /**
     * Функция получения процента успешного выполнения привычек за определенный период.
     * @param dateFrom - с какого числа
     * @param dateTo - по какое число
     * @return возвращает процент успешного выполнения привычек
     */
    public synchronized int percentSuccessHabits(LocalDate dateFrom, LocalDate dateTo){
        long resultDays = ChronoUnit.DAYS.between(dateFrom, dateTo);
        int allHabits = habits.size();
        int sumHabit = 0;
        while (!dateTo.isEqual(dateFrom)) {

            for (Habit habit : habits) {
                Set<LocalDate> listDates = habit.getDoneDates();
                for (LocalDate date : listDates) {
                    if(date.equals(dateFrom)) {
                        sumHabit++;
                        break;
                    }
                }
            }

            dateFrom = dateFrom.plusDays(1);
        }

        return (int) (Math.round((double) (sumHabit * 100)/ (double) (allHabits * resultDays)));
    }

    /**
     * Функция получения отчета для пользователя по прогрессу выполнения привычки
     * @param idHabit - id привычки
     * @return возвращает отчет по привычке
     */
    public synchronized String reportHabit(Long idHabit) {
        StringBuilder builder = new StringBuilder();

        Habit habit = findById(idHabit);

        builder.append("Название: ").append(habit.getTitle()).append("\n");
        builder.append("Описание: ").append(habit.getDescription()).append("\n");
        builder.append("Статус: ").append(habit.getStatus()).append("\n");
        builder.append("Частота выполнения: ").append(habit.getRepeat()).append("\n");

        StringBuilder stringBuilder = new StringBuilder("Уведомления : ");
        if (habit.getPushTime() == null) stringBuilder.append("выкл");
        else stringBuilder.append("вкл").append(" в ").append(habit.getPushTime());

        builder.append(stringBuilder).append("\n");
        builder.append("Текущая серия выполнений: ").append(habit.getStreak()).append("\n");
        builder.append("Даты выполнения привычки: ").append(habit.getDoneDates()).append("\n");

        return builder.toString();
    }

    /**
     * Процедура включения отправки уведомления привычки в указанное время {@link Habit#setPushTime(LocalTime)}
     * @param idHabit - id привычки
     * @param pushTime - время уведомления привычки
     */
    public void switchOnPushNotification(Long idHabit, LocalTime pushTime) {
        findById(idHabit).setPushTime(pushTime);
    }

    /**
     * Процедура выключения уведомления привычки {@link Habit#setPushTime(LocalTime)}
     * @param idHabit - id привычки
     */
    public void switchOffPushNotification(Long idHabit) {
        findById(idHabit).setPushTime(null);
    }

    /**
     * Переопределенный метод {@link Object#equals(Object)}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return isActive == user.isActive && Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(habits, user.habits);
    }

    /**
     * Переопределенный метод {@link Object#hashCode}
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, isActive, habits);
    }

    /**
     * Переопределенный метод {@link Object#toString}
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", habits=" + habits +
                '}';
    }
}