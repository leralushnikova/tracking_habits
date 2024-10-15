package com.lushnikova.homework_1.reminder;


import com.lushnikova.homework_1.dto.resp.HabitResponse;
import com.lushnikova.homework_1.model.Habit;
import com.lushnikova.homework_1.model.User;
import com.lushnikova.homework_1.repository.UserRepository;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class ReminderService {
    private volatile boolean running;
    private Thread thread;
    private final UserRepository userRepository;

    public ReminderService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void start(UUID idPerson) {
        running = true;
        thread = new Thread(() -> {
            while (running) {
                checkReminders(idPerson); // Ваш метод проверки напоминаний
                try {
                    Thread.sleep(60 * 1000); // Пауза на 1 минуту
                } catch (InterruptedException e) {
                    // Обработка прерывания
                }
            }
        });
        thread.start();
    }

    public void stop() {
        running = false;
        thread.interrupt();
    }

    private void checkReminders(UUID idPerson) {
        // Реализация проверки напоминаний
        // Например, проверка текущего времени и отправка уведомлений
//        * API для отправки напоминаний пользователю о необходимости выполнения привычки.

        //2. Использование Timer и TimerTask
        //Timer и TimerTask из пакета java.util позволяют планировать задачи для выполнения в будущем или периодически.
        //
        //3. Использование ScheduledExecutorService
        //ScheduledExecutorService предоставляет более современный и гибкий способ планирования задач.
        List<Habit> listHabits = getUser(idPerson).getHabits();

        for (Habit habit : listHabits) {
            if (habit.getPushTime() != null) {
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
                if (habit.getPushTime().toString().equals(LocalTime.now().format(dtf))) {
                    System.out.println("Время выполнить привычку " + habit.getTitle());
                    break;
                }
            }
        }

    }

    private User getUser (UUID idPerson){
        return userRepository.findById(idPerson);
    }
}