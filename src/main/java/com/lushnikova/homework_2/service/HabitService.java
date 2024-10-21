package com.lushnikova.homework_2.service;

import com.lushnikova.homework_2.model.enum_for_model.Statistics;
import com.lushnikova.homework_2.dto.req.HabitRequest;
import com.lushnikova.homework_2.dto.resp.HabitResponse;
import com.lushnikova.homework_2.model.enum_for_model.Repeat;
import com.lushnikova.homework_2.model.enum_for_model.Status;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public interface HabitService {

    /**
     * Процедура добавления привычки
     * @param habitRequest - привычка
     * @param idUser - id пользователя
     * @throws SQLException
     */
    void save(HabitRequest habitRequest, Long idUser) throws SQLException;

    /**
     * Функция получения привычки
     * @param id - id привычки
     * @return возвращает объект привычки
     * @throws SQLException
     */
    HabitResponse findById(Long id) throws SQLException;

    /**
     * Функция получения списка привычек
     * @param idUser - id пользователя
     * @return возвращает список привычек по idUser
     * @throws SQLException
     */
    List<HabitResponse> findAll(Long idUser) throws SQLException;

    /**
     * Функция получения списка привычек пользователя по статусу
     * @param idUser - id пользователя
     * @param status - статус привычки
     * @return возвращает список привычек пользователя по статусу
     * @throws SQLException
     */
    List<HabitResponse> getHabitsByStatus(Long idUser, Status status) throws SQLException;

    /**
     * Функция получения списка привычек пользователя по дате создания
     * @param idUser - id пользователя
     * @param date - дата создания
     * @return возвращает список привычек пользователя по дате создания
     * @throws SQLException
     */
    List<HabitResponse> getHabitsByDate(Long idUser, Date date) throws SQLException;

    /**
     * Процедура обновления названия привычки
     * @param id - id привычки
     * @param newTitle - новое название привычки
     * @throws SQLException
     */
    void updateTitleByIdHabit(Long id, String newTitle) throws SQLException;

    /**
     * Процедура обновления описания привычки пользователя
     * @param id - id привычки
     * @param newDescription - новое описания привычки
     * @throws SQLException
     */
    void updateDescriptionByIdHabit(Long id, String newDescription) throws SQLException;

    /**
     * Процедура обновления частоты выполнения привычки пользователя
     * @param id- id привычки
     * @param newRepeat - новая частота выполнения
     * @throws SQLException
     */
    void updateRepeatByIdHabit(Long id, Repeat newRepeat) throws SQLException;

    /**
     * Процедура обновления статуса привычки пользователя
     * @param id - id привычки
     * @param newStatus - новый статс
     * @throws SQLException
     */
    void updateStatusByIdHabit(Long id, Status newStatus) throws SQLException;

    /**
     * Процедура удаления привычки пользователя
     * @param id - id привычки
     * @throws SQLException
     */
    void delete(Long id) throws SQLException;

    /**
     * Функция получения генерации статистики выполнения привычки
     * @param id - id привычки
     * @param dateFrom - дата, с какого числа нужно посчитать статистику
     * @param statistics - указания периода (DAY - за день, WEEK - за неделю, MONTH - месяц)
     * @return возвращает статистики выполнения привычки
     * @throws SQLException
     */
    List<String> getHabitFulfillmentStatistics(Long id, LocalDate dateFrom, Statistics statistics) throws SQLException;

    /**
     * Процедура отметки даты, когда выполнялась привычка
     * @param id - id привычки
     * @throws SQLException
     */
    void setDoneDates(Long id) throws SQLException;

    /**
     * Функция получения процента успешного выполнения привычек за определенный период
     * @param idUser - id пользователя
     * @param dateFrom - с какого числа
     * @param dateTo - по какое число
     * @return возвращает процент успешного выполнения привычек
     * @throws SQLException
     */
    int percentSuccessHabits(Long idUser, LocalDate dateFrom, LocalDate dateTo) throws SQLException;

    /**
     * Процедура формирование отчета для пользователя по прогрессу выполнения привычки
     * @param id - id привычки
     * @throws SQLException
     */
    void reportHabit(Long id) throws SQLException;


    /**
     * Процедура включения/выключения отправки уведомления привычки в указанное время
     * @param id - id привычки
     * @param pushTime - время уведомления привычки
     * @throws SQLException
     */
    void switchOnOrOffPushNotification(Long id, Time pushTime) throws SQLException;

}
