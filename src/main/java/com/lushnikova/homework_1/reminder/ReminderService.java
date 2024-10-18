package com.lushnikova.homework_1.reminder;


import com.lushnikova.homework_1.model.Habit;
import com.lushnikova.homework_1.model.User;
import com.lushnikova.homework_1.repository.UserRepository;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;


/**
 * Класс, который проверяет у пользователя наличие время уведомления привычек 
 * и посылает уведомление
 */
public class ReminderService {
    /** Поле существования потока*/
    private volatile boolean running;
    
    /** Поле потока*/
    private Thread thread;
    
    /** Поле репозитория пользователей*/
    private final UserRepository userRepository;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param userRepository - репозиторий пользователей
     */
    public ReminderService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Операция начало работы потока
     * @param idUser - id пользователя
     * Поток проверяет каждую минуту проверку напоминаний
     */
    public void start(UUID idUser) {
        running = true;
        thread = new Thread(() -> {
            while (running) {
                checkReminders(idUser);
                try {
                    Thread.sleep(60 * 1000); // Пауза на 1 минуту
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        thread.start();
    }

    /**
     * Операция завершения работы потока
     */
    public void stop() {
        running = false;
        thread.interrupt();
    }

    /**
     * Операция проверяет у пользователя наличие обновлений у каждой привычке
     * @param idUser - - id пользователя
     * если время уведомления есть в наличии
     * при наличии уведомления проверяет текущее время с времени уведомлений
     * если все совпадает, то напоминает об привычке
     */
    private void checkReminders(UUID idUser) {
        List<Habit> listHabits = getUser(idUser).getHabits();

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

    /**
     * Функция получения пользователя из репозитория
     * @param idUser - id пользователя
     * @return возвращает пользователя
     */
    private User getUser (UUID idUser){
        return userRepository.findById(idUser);
    }
}