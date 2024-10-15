package com.lushnikova.homework_1.reminder;


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
                checkReminders(idPerson);
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