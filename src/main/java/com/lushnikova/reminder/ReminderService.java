package com.lushnikova.reminder;

import com.lushnikova.model.Habit;
import com.lushnikova.repository.HabitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


/**
 * Класс, который проверяет у пользователя наличие время уведомления привычек
 * и посылает уведомление
 */
@Component
public class ReminderService {
    /** Поле существования потока*/
    private volatile boolean running;

    /** Поле потока*/
    private Thread thread;

    /** Поле репозиторий привычек*/
    private final HabitRepository habitRepository;

    /**
     * Конструктор - создание нового объекта с определенными значениями
     * @param habitRepository - репозиторий пользователей
     */
    @Autowired
    public ReminderService(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
    }

    /**
     * Операция начало работы потока
     * @param idUser - id пользователя
     * Поток проверяет каждую минуту проверку напоминаний
     */
    public void start(Long idUser) {
        running = true;
        thread = new Thread(() -> {
            while (running) {

                try {
                    checkReminders(idUser);
                    Thread.sleep(60 * 1000); // Пауза на 1 минуту
                } catch (InterruptedException | SQLException e) {
                    System.err.println("Поток отправки уведомления привычек остановлен");
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
    private void checkReminders(Long idUser) throws SQLException {
        List<Habit> listHabits = habits(idUser);

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
     * Функция получения списка привычек пользователя
     * @param idUser - id пользователя
     * @return список привычек
     */
    private List<Habit> habits(Long idUser) throws SQLException {
        return habitRepository.findAll(idUser);
    }
}