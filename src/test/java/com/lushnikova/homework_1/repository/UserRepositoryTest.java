package com.lushnikova.homework_1.repository;

import com.lushnikova.homework_1.model.Habit;
import com.lushnikova.homework_1.model.User;
import com.lushnikova.homework_1.model.ENUM.Repeat;
import com.lushnikova.homework_1.model.ENUM.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Класс тестирования репозитория пользователей")
class UserRepositoryTest {
    private final UserRepository userRepository = UserRepository.getInstance();

    @Test
    @DisplayName("Сохранение пользователя в репозиторий")
    void shouldSaveUser() {
        User user = createUser();
        userRepository.save(user);

        User userFromRepository = userRepository.findById(user.getId());

        assertEquals(userFromRepository, user);
    }

    @Test
    @DisplayName("Изменение имени пользователя")
    void shouldUpdateNameUser() {
        User user = createUser();
        userRepository.save(user);

        String newName = "new_name";
        user.setName(newName);

        userRepository.updateName(user.getId(), newName);

        User userFromRepository = userRepository.findById(user.getId());

        assertEquals(userFromRepository.getName(), user.getName());
    }

    @Test
    @DisplayName("Обновление почты пользователя")
    void shouldUpdateEmailUser() {
        User user = createUser();
        userRepository.save(user);

        String newEmail = "email@gmail.com";
        user.setEmail(newEmail);

        userRepository.updateEmail(user.getId(), newEmail);

        User userFromRepository = userRepository.findById(user.getId());

        assertEquals(userFromRepository.getEmail(), user.getEmail());
    }

    @Test
    @DisplayName("Изменение пароля пользователя")
    void shouldUpdatePasswordUser() {
        User user = createUser();
        userRepository.save(user);

        String newPassword = "new_password";
        user.setPassword(newPassword);

        userRepository.updatePassword(user.getId(), newPassword);



        User userFromRepository = userRepository.findById(user.getId());

        assertEquals(userFromRepository.getPassword(), user.getPassword());
    }

    @Test
    @DisplayName("Добавление привычки пользователю")
    void shouldAddHabitByIdUser() {
        User user = getUserById();

        Habit habit = createHabit();

        userRepository.addHabitByIdUser(user.getId(), habit);

        Habit habitFromRepository = userRepository.findById(user.getId()).findById(habit.getId());

        assertEquals(habit, habitFromRepository);
    }

    @Test
    @DisplayName("Изменение названия привычки")
    void shouldUpdateTitleHabit(){
        User user = getUserById();

        int idHabit = 1;
        Habit habit = user.getHabits().get(idHabit);

        String newTitle = "new_habit";
        habit.setTitle(newTitle);

        userRepository.updateTitleByIdHabitByIdUser(user.getId(), habit.getId(), newTitle);

        Habit fromRepository = userRepository.findById(user.getId()).getHabits().get(idHabit);
        assertEquals(newTitle, fromRepository.getTitle());
    }

    @Test
    @DisplayName("Изменение описания привычки")
    void shouldUpdateDescriptionHabit(){
        User user = getUserById();

        int idHabit = 3;
        Habit habit = user.getHabits().get(idHabit);

        String newDescription = "new_description";
        habit.setDescription(newDescription);

        userRepository.updateDescriptionByIdHabitByIdUser(user.getId(), habit.getId(), newDescription);

        Habit fromRepository = userRepository.findById(user.getId()).getHabits().get(idHabit);
        assertEquals(newDescription, fromRepository.getDescription());
    }

    @Test
    @DisplayName("Изменение частоты повторения привычки")
    void shouldRepeatTitleHabit(){
        User user = getUserById();

        int idHabit = 3;
        Habit habit = user.getHabits().get(idHabit);

        Repeat newRepeat = Repeat.WEEKLY;
        habit.setRepeat(newRepeat);

        userRepository.updateRepeatByIdHabitByIdUser(user.getId(), habit.getId(), newRepeat);

        Habit fromRepository = userRepository.findById(user.getId()).getHabits().get(idHabit);
        assertEquals(newRepeat, fromRepository.getRepeat());
    }

    @Test
    @DisplayName("Изменение статуса привычки")
    void shouldStatusTitleHabit(){
        User user = getUserById();

        int idHabit = 3;
        Habit habit = user.getHabits().get(idHabit);

        Status newStatus = Status.IN_PROGRESS;
        habit.setStatus(newStatus);

        userRepository.updateStatusByIdHabitByIdUser(user.getId(), habit.getId(), newStatus);

        Habit fromRepository = userRepository.findById(user.getId()).getHabits().get(idHabit);
        assertEquals(newStatus, fromRepository.getStatus());
    }

    @Test
    @DisplayName("Удаление привычки")
    void shouldDeleteHabit(){
        User user = getUserById();

        long idHabit = 3;

        Habit habit = user.getHabits().get((int) idHabit);

        userRepository.deleteHabitByIdUser(user.getId(), idHabit + 1);

        Habit habitFromRepository = userRepository
                .findById(user.getId())
                .getHabits()
                .stream()
                .filter(el -> el.getTitle().equals(habit.getTitle()))
                .findFirst()
                .orElse(null);

        assertNull(habitFromRepository);
    }

    @Test
    @DisplayName("Отметка, что привычка выполнения")
    void shouldSetDoneDatesHabit(){
        User user = getUserById();

        long idHabit = 2;

        Habit habit = user.getHabits().get((int) idHabit);
        LocalDate localDate = habit.getCreatedAt();
        LocalDate setDoneDate1 = localDate.plusDays(10);
        LocalDate setDoneDate2 = setDoneDate1.plusDays(5);
        LocalDate setDoneDate3 = setDoneDate2.plusDays(5);

        habit.setDoneDates(setDoneDate1);
        habit.setDoneDates(setDoneDate2);
        habit.setDoneDates(setDoneDate3);

        userRepository.setDoneDatesHabitByIdUser(user.getId(), habit.getId());

        Habit habit1 = userRepository.findById(user.getId()).findById(habit.getId());

        assertEquals(habit1, habit);
    }

    @Test
    @DisplayName("Включение уведомления привычки в определенное время")
    void shouldSwitchOnPushNotification(){
        User user = getUserById();

        LocalTime localTime = LocalTime.parse("11:00");

        long idHabit = 2;

        Habit habit = user.getHabits().get((int) idHabit);

        userRepository.switchOnPushNotificationByIdUser(user.getId(), habit.getId(), localTime);
        Habit habit1 = userRepository.findById(user.getId()).findById(habit.getId());

        assertEquals(localTime, habit1.getPushTime());
    }

    @Test
    @DisplayName("Выключение уведомления привычки")
    void shouldSwitchOffPushNotification(){
        User user = getUserById();

        long idHabit = 2;

        Habit habit = user.getHabits().get((int) idHabit);

        userRepository.switchOffPushNotificationByIdUser(user.getId(), habit.getId());
        Habit habit1 = userRepository.findById(user.getId()).findById(habit.getId());

        assertNull(habit1.getPushTime());
    }


    @Test
    @DisplayName("Удаление пользователя")
    void shouldDeleteUser() {
        User user = getUserById();

        userRepository.delete(user.getId());

        User userFromRepository = userRepository.findById(user.getId());

        assertNull(userFromRepository);
    }


    /**
     * Функция получения пользователя
     * @return возвращает объект пользователя
     */
    private User getUserById(){
        return userRepository.findAll().stream().filter(el -> el.getName().equals("Jame")).findFirst().orElse(null);
    }

    /**
     * Процедура создания пользователя
     * @return возвращает объект пользователя
     */
    private User createUser() {
        return new User("user", "user@gmail.com", "user");
    }

    /**
     * Процедура создания привычки
     * @return возвращает объект привычки
     */
    private Habit createHabit() {
        return new Habit("habit", "habit", Repeat.WEEKLY);
    }
}