package com.lushnikova.repository;

import com.lushnikova.model.Habit;
import com.lushnikova.model.enums.Repeat;
import com.lushnikova.model.enums.Status;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

import static org.junit.Assert.*;

@DisplayName("Класс тестирования репозитория привычек")
class HabitRepositoryTest extends AbstractIntegrationTest{

    @Autowired
    HabitRepository habitRepository;

    @SneakyThrows
    @Test
    @DisplayName("Добавление привычки пользователю")
    void shouldSaveHabit() {
        String title = "new habit";
        Habit habit = Habit.builder()
                .title(title)
                .description("new habit")
                .repeat(Repeat.DAILY)
                .build();

        habitRepository.save(habit, 2L);

        assertNotNull(habitRepository.findAll(2L).stream().filter(el -> el.equals(title)));
    }

    @SneakyThrows
    @Test
    @DisplayName("Изменение названия привычки")
    void shouldUpdateTitleByIdHabit() {
        String title = "new habit";

        long idUser = 2L;
        long idHabit = 1L;
        habitRepository.updateTitleByIdHabit(idHabit, title);

        assertEquals(habitRepository.findById(idHabit, idUser).getTitle(), title);
    }

    @SneakyThrows
    @Test
    @DisplayName("Изменение описания привычки")
    void shouldUpdateDescriptionByIdHabit() {
        String newDescription = "new description";

        long idUser = 2L;
        long idHabit = 1L;
        habitRepository.updateDescriptionByIdHabit(idHabit, newDescription);

        assertEquals(habitRepository.findById(idHabit, idUser).getDescription(), newDescription);
    }

    @SneakyThrows
    @Test
    @DisplayName("Изменение частоты повторения привычки")
    void shouldUpdateRepeatByIdHabit() {
        Repeat repeat = Repeat.DAILY;

        long idUser = 2L;
        long idHabit = 1L;
        habitRepository.updateRepeatByIdHabit(idHabit, repeat);

        assertEquals(habitRepository.findById(idHabit, idUser).getRepeat(), repeat);
    }

    @SneakyThrows
    @Test
    @DisplayName("Изменение статуса привычки")
    void shouldUpdateStatusByIdHabit() {
        Status status = Status.DONE;

        long idUser = 2L;
        long idHabit = 1L;
        habitRepository.updateStatusByIdHabit(idHabit, status);

        assertEquals(habitRepository.findById(idHabit, idUser).getStatus(), status);
    }

    @SneakyThrows
    @Test
    @DisplayName("Удаление привычки")
    void shouldDelete() {

        long idUser = 2L;
        long idHabit = 1L;
        Habit habit = habitRepository.findById(idHabit, idUser);

        habitRepository.delete(idHabit);

        assertFalse(habitRepository.findAll(habit.getUserId()).contains(habit));
    }

    @SneakyThrows
    @Test
    @DisplayName("Отметка, что привычка выполнения")
    void shouldSetDoneDates() {
        long idUser = 2L;
        long idHabit = 1L;

        Date date = Date.valueOf(LocalDate.now());
        habitRepository.setDoneDates(idHabit, idUser);

        Habit habit = habitRepository.findById(idHabit, idUser);

        assertTrue(habit.getDoneDates().contains(date));
    }

    @SneakyThrows
    @Test
    @DisplayName("Выключение уведомления привычки")
    void shouldSwitchOnOrOffPushNotification() {

        Time time = Time.valueOf("11:10:00");

        long idUser = 2L;
        long idHabit = 1L;
        habitRepository.switchOnOrOffPushNotification(idHabit, time);

        Habit habit = habitRepository.findById(idHabit, idUser);

        assertEquals(habit.getPushTime(), time);
    }
}