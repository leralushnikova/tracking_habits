package com.lushnikova.service;

import com.lushnikova.dto.request.HabitRequest;
import com.lushnikova.dto.response.HabitResponse;
import com.lushnikova.model.enums.Repeat;
import com.lushnikova.model.enums.Statistics;
import com.lushnikova.model.enums.Status;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface HabitService {

    /**
     * Процедура добавления привычки
     * @param habitRequest - привычка
     * @param idUser - id пользователя
     */
    void save(HabitRequest habitRequest, Long idUser);

    /**
     * Функция получения привычки
     * @param idHabit - id привычки
     * @param idUser - id пользователя
     * @return возвращает объект привычки
     */
    Optional<HabitResponse> findById(Long idHabit, Long idUser);

    /**
     * Функция получения списка привычек
     * @param idUser - id пользователя
     * @return возвращает список привычек по idUser
     */
    List<HabitResponse> findAll(Long idUser);

    /**
     * Функция получения списка привычек пользователя по статусу
     * @param idUser - id пользователя
     * @param status - статус привычки
     * @return возвращает список привычек пользователя по статусу
     */
    List<HabitResponse> getHabitsByStatus(Long idUser, Status status);

    /**
     * Функция получения списка привычек пользователя по дате создания
     * @param idUser - id пользователя
     * @param date - дата создания
     * @return возвращает список привычек пользователя по дате создания
     */
    List<HabitResponse> getHabitsByDate(Long idUser, Date date);

    /**
     * Процедура обновления названия привычки
     * @param id - id привычки
     * @param newTitle - новое название привычки
     */
    void updateTitleByIdHabit(Long id, String newTitle);

    /**
     * Процедура обновления описания привычки пользователя
     * @param id - id привычки
     * @param newDescription - новое описания привычки
     */
    void updateDescriptionByIdHabit(Long id, String newDescription);

    /**
     * Процедура обновления частоты выполнения привычки пользователя
     * @param id- id привычки
     * @param newRepeat - новая частота выполнения
     */
    void updateRepeatByIdHabit(Long id, Repeat newRepeat);

    /**
     * Процедура обновления статуса привычки пользователя
     * @param id - id привычки
     * @param newStatus - новый статс
     */
    void updateStatusByIdHabit(Long id, Status newStatus);

    /**
     * Процедура удаления привычки пользователя
     * @param id - id привычки
     */
    void delete(Long id);

    /**
     * Функция получения генерации статистики выполнения привычки
     * @param id - id привычки
     * @param dateFrom - дата, с какого числа нужно посчитать статистику
     * @param statistics - указания периода (DAY - за день, WEEK - за неделю, MONTH - месяц)
     * @return возвращает статистики выполнения привычки
     */
    List<String> getHabitFulfillmentStatistics(Long id, LocalDate dateFrom, Statistics statistics);

    /**
     * Процедура отметки даты, когда выполнялась привычка
     * @param idHabit - id привычки
     * @param idUser - id пользователя
     */
    void setDoneDates(Long idHabit, Long idUser);

    /**
     * Функция получения процента успешного выполнения привычек за определенный период
     * @param idUser - id пользователя
     * @param dateFrom - с какого числа
     * @param dateTo - по какое число
     * @return возвращает процент успешного выполнения привычек
     */
    Optional<Integer> percentSuccessHabits(Long idUser, LocalDate dateFrom, LocalDate dateTo);

    /**
     * Процедура формирование отчета для пользователя по прогрессу выполнения привычки
     * @param idHabit - id привычки
     * @param idUser - id пользователя
     */
    Optional<String> reportHabit(Long idHabit, Long idUser);


    /**
     * Процедура включения/выключения отправки уведомления привычки в указанное время
     * @param id - id привычки
     * @param pushTime - время уведомления привычки
     */
    void switchOnOrOffPushNotification(Long id, Time pushTime);

}
