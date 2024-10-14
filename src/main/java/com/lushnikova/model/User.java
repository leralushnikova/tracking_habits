package com.lushnikova.model;

import com.lushnikova.model.enums.Repeat;
import com.lushnikova.model.enums.Statistics;
import com.lushnikova.model.enums.Status;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class User {
    private final UUID id;
    private String name;
    private String email;
    private String password;
    private boolean isActive;
    private final CopyOnWriteArrayList<Habit> habits = new CopyOnWriteArrayList<>();

    {
        id = UUID.randomUUID();
        isActive = true;
    }
    public User() {
    }

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addHabit(Habit habit) {
        habits.add(habit);
    }

    public List<Habit> getHabits() {
        return habits;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<Habit> getHabitsByStatus(Status status) {
        return habits.stream().filter(el -> el.getStatus().equals(status)).toList();
    }

    public List<Habit> getHabitsByLocalDate(LocalDate localDate) {
        return habits.stream().filter(el -> el.getCreatedAt().toString().equals(localDate.toString())).toList();
    }

    public void updateTitle(Long idHabit, String newTitle){
        for (Habit habit : habits) {
            if (habit.getId().equals(idHabit)) {
                habit.setTitle(newTitle);
            }
        }
    }

    public void updateDescription(Long idHabit, String newDescription){
        for (Habit habit : habits) {
            if (habit.getId().equals(idHabit)) {
                habit.setDescription(newDescription);
            }
        }
    }

    public void updateRepeat(Long idHabit, Repeat newRepeat){
        for (Habit habit : habits) {
            if (habit.getId().equals(idHabit)) {
                habit.setRepeat(newRepeat);
            }
        }
    }

    public void updateStatus(Long idHabit, Status newStatus){
        for (Habit habit : habits) {
            if (habit.getId().equals(idHabit)) {
                habit.setStatus(newStatus);
            }
        }
    }

    public void deleteHabit(Long idHabit){
        habits.removeIf(habit -> habit.getId().equals(idHabit));
        for (int i = 0; i < habits.size(); i++) {
            habits.get(i).setId((long) (i + 1));
        }
    }

    public List<String> getHabitFulfillmentStatistics(Statistics statistics, Long idHabit, LocalDate dateFrom) {
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


    public int percentSuccessHabits(LocalDate dateFrom, LocalDate dateTo){
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

    public void reportHabit(Long idHabit) {
        for(Habit habit : habits){
            if(habit.getId().equals(idHabit)){
                System.out.println("Название: " + habit.getTitle());
                System.out.println("Описание: " + habit.getDescription());
                System.out.println("Статус: " + habit.getStatus());
                System.out.println("Частота выполнения: " + habit.getRepeat());
                System.out.println("Текущая серия выполнений: " + habit.getStreak());
                System.out.println("Даты выполнения привычки: " +  habit.getDoneDates());
                System.out.println("----------------------------------------------");
                break;
            }
        }
    }

   /* public void switchOnPushNotification(Long idHabit, LocalTime pushTime) {
        for (Habit habit : habits) {
            if (habit.getId().equals(idHabit)) {
                habit.setPushTime(pushTime);
                break;
            }
        }
    }

    public void switchOffPushNotification(Long idHabit) {
        for (Habit habit : habits) {
            if (habit.getId().equals(idHabit)) {
                habit.setPushTime(null);
                break;
            }
        }
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return isActive == user.isActive && Objects.equals(id, user.id) && Objects.equals(name, user.name) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(habits, user.habits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, isActive, habits);
    }

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
