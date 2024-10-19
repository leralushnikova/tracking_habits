package com.lushnikova.homework_1.service;

import com.lushnikova.homework_1.dto.req.HabitRequest;
import com.lushnikova.homework_1.dto.req.UserRequest;
import com.lushnikova.homework_1.dto.resp.HabitResponse;
import com.lushnikova.homework_1.dto.resp.UserResponse;
import com.lushnikova.homework_1.model.enum_for_model.Repeat;
import com.lushnikova.homework_1.model.enum_for_model.Statistics;
import com.lushnikova.homework_1.model.enum_for_model.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

/**
 * Интерфейс Service по управлению пользователями и их привычек
 */
public interface UserService {

    /** Процедура сохранения пользователя
     * @param userRequest - пользователя
     * @return возвращает объект пользователя
     */
    UserResponse save(UserRequest userRequest);

    /** Функция получения пользователя по
     * @param id - id пользователя
     * @return возвращает объект пользователя
     */
    UserResponse findById(UUID id);

    /**
     * Процедура обновления имени пользователя
     * @param id - id пользователя
     * @param name - новое имя пользователя
     */
    void updateName(UUID id, String name);

    /**
     * Процедура обновления почты пользователя
     * @param id - id пользователя
     * @param email - новое имя пользователя
     */
    void updateEmail(UUID id, String email);

    /**
     * Процедура обновления пароля пользователя
     * @param id - id пользователя
     * @param password - новый пароль пользователя
     */
    void updatePassword(UUID id, String password);

    /**
     * Процедура удаления пользователя,
     * @param id - id пользователя
     */
    void delete(UUID id);

    /**
     * Процедура добавления привычки пользователя
     * @param idUser - id пользователя
     * @param habitRequest - привычка
     */
    void addHabitByIdUser(UUID idUser, HabitRequest habitRequest);

    /**
     * Функция получения списка привычек пользователя
     * @param idUser - id пользователя
     * @return возвращает список привычек по idUser
     */
    List<HabitResponse> getHabitsByIdUser(UUID idUser);

    /**
     * Функция получения списка привычек пользователя по статусу
     * @param idUser - id пользователя
     * @param status - статус привычки
     * @return возвращает список привычек пользователя по статусу
     */
    List<HabitResponse> getHabitsByStatusByIdUser(UUID idUser, Status status);

    /**
     * Функция получения списка привычек пользователя по дате создания
     * @param idUser - id пользователя
     * @param localDate - дата создания
     * @return возвращает список привычек пользователя по дате создания
     */
    List<HabitResponse> getHabitsByLocalDateByIdUser(UUID idUser, LocalDate localDate);

    /**
     * Процедура обновления названия привычки пользователя
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param newTitle - новое название привычки
     */
    void updateTitleByIdHabitByIdUser(UUID idUser, Long idHabit, String newTitle);

    /**
     * Процедура обновления описания привычки пользователя
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param newDescription - новое описания привычки
     */
    void updateDescriptionByIdHabitByIdUser(UUID idUser, Long idHabit, String newDescription);

    /**
     * Процедура обновления частоты выполнения привычки пользователя
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param newRepeat - новая частота выполнения
     */
    void updateRepeatByIdHabitByIdUser(UUID idUser, Long idHabit, Repeat newRepeat);

    /**
     * Процедура обновления статуса привычки пользователя
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param newStatus - новый статс
     */
    void updateStatusByIdHabitByIdUser(UUID idUser, Long idHabit, Status newStatus);

    /**
     * Процедура удаления привычки пользователя
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     */
    void deleteHabitByIdUser(UUID idUser, Long idHabit);

    /**
     * Процедура отметки даты, когда выполнялась привычка
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     */
    void setDoneDatesHabitByIdUser(UUID idUser, Long idHabit);

    /**
     * Функция получения генерации статистики выполнения привычки
     * @param idUser - id пользователя
     * @param statistics - указания периода (DAY - за день, WEEK - за неделю, MONTH - месяц)
     * @param idHabit - id привычки
     * @param dateFrom - дата, с какого числа нужно посчитать статистику
     * @return возвращает статистики выполнения привычки
     */
    List<String> getHabitFulfillmentStatisticsByIdUser(UUID idUser, Statistics statistics, Long idHabit, LocalDate dateFrom);

    /**
     * Функция получения процента успешного выполнения привычек за определенный период
     * @param idUser - id пользователя
     * @param dateFrom - с какого числа
     * @param dateTo - по какое число
     * @return возвращает процент успешного выполнения привычек
     */
    int percentSuccessHabitsByIdUser(UUID idUser, LocalDate dateFrom, LocalDate dateTo);

    /**
     * Процедура формирование отчета для пользователя по прогрессу выполнения привычки
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     */
    void reportHabitByIdUser(UUID idUser, Long idHabit);

    /**
     * Функция получения списка администраторов
     * @return возвращает копию списка администраторов
     */
    List<UserResponse> findAll();

    /**
     * Процедура определения значения поля
     * @param idUser - id пользователя
     * @param isActive - блокировка
     */
    void setIsActiveByIdUser(UUID idUser, boolean isActive);

    /**
     * Процедура включения отправки уведомления привычки в указанное время
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param pushTime - время уведомления привычки
     */
    void switchOnPushNotificationByIdUser(UUID idUser, Long idHabit, LocalTime pushTime);

    /**
     * Процедура выключения уведомления привычки
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     */
    void switchOffPushNotificationByIdUser(UUID idUser, Long idHabit);
}
