package com.lushnikova.homework_3.service;

import com.lushnikova.homework_3.dto.req.HabitRequest;
import com.lushnikova.homework_3.dto.req.UserRequest;
import com.lushnikova.homework_3.dto.resp.HabitResponse;
import com.lushnikova.homework_3.dto.resp.UserResponse;
import com.lushnikova.homework_3.exception.ModelNotFound;
import com.lushnikova.homework_3.model.ENUM.Repeat;
import com.lushnikova.homework_3.model.ENUM.Statistics;
import com.lushnikova.homework_3.model.ENUM.Status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Интерфейс Service по управлению пользователями и их привычек
 */
public interface UserService extends Service{

    /** Процедура сохранения пользователя
     * @param userRequest - пользователя
     * @return возвращает объект пользователя
     */
    UserResponse save(UserRequest userRequest);

    /** Функция получения пользователя по
     * @param id - id пользователя
     * @return возвращает объект пользователя
     * @throws ModelNotFound
     */
    UserResponse findById(Long id)  throws ModelNotFound;

    /**
     * Процедура обновления имени пользователя
     * @param id - id пользователя
     * @param name - новое имя пользователя
     * @throws ModelNotFound
     */
    void updateName(Long id, String name)  throws ModelNotFound;

    /**
     * Процедура обновления почты пользователя
     * @param id - id пользователя
     * @param email - новое имя пользователя
     * @throws ModelNotFound
     */
    void updateEmail(Long id, String email)  throws ModelNotFound;

    /**
     * Процедура обновления пароля пользователя
     * @param id - id пользователя
     * @param password - новый пароль пользователя
     * @throws ModelNotFound
     */
    void updatePassword(Long id, String password)  throws ModelNotFound;

    /**
     * Процедура удаления пользователя,
     * @param id - id пользователя
     * @throws ModelNotFound
     */
    void delete(Long id)  throws ModelNotFound;

    /**
     * Процедура добавления привычки пользователя
     * @param idUser - id пользователя
     * @param habitRequest - привычка
     * @throws ModelNotFound
     */
    void addHabitByIdUser(Long idUser, HabitRequest habitRequest)  throws ModelNotFound;

    /**
     * Функция получения списка привычек пользователя
     * @param idUser - id пользователя
     * @return возвращает список привычек по idUser
     * @throws ModelNotFound
     */
    List<HabitResponse> getHabitsByIdUser(Long idUser)  throws ModelNotFound;

    /**
     * Функция получения списка привычек пользователя по статусу
     * @param idUser - id пользователя
     * @param status - статус привычки
     * @return возвращает список привычек пользователя по статусу
     * @throws ModelNotFound
     */
    List<HabitResponse> getHabitsByStatusByIdUser(Long idUser, Status status)  throws ModelNotFound;

    /**
     * Функция получения списка привычек пользователя по дате создания
     * @param idUser - id пользователя
     * @param localDate - дата создания
     * @return возвращает список привычек пользователя по дате создания
     * @throws ModelNotFound
     */
    List<HabitResponse> getHabitsByLocalDateByIdUser(Long idUser, LocalDate localDate)  throws ModelNotFound;

    /**
     * Процедура обновления названия привычки пользователя
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param newTitle - новое название привычки
     * @throws ModelNotFound
     */
    void updateTitleByIdHabitByIdUser(Long idUser, Long idHabit, String newTitle)  throws ModelNotFound;

    /**
     * Процедура обновления описания привычки пользователя
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param newDescription - новое описания привычки
     * @throws ModelNotFound
     */
    void updateDescriptionByIdHabitByIdUser(Long idUser, Long idHabit, String newDescription)  throws ModelNotFound;

    /**
     * Процедура обновления частоты выполнения привычки пользователя
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param newRepeat - новая частота выполнения
     * @throws ModelNotFound
     */
    void updateRepeatByIdHabitByIdUser(Long idUser, Long idHabit, Repeat newRepeat)  throws ModelNotFound;

    /**
     * Процедура обновления статуса привычки пользователя
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param newStatus - новый статус
     * @throws ModelNotFound
     */
    void updateStatusByIdHabitByIdUser(Long idUser, Long idHabit, Status newStatus)  throws ModelNotFound;

    /**
     * Процедура удаления привычки пользователя
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @throws ModelNotFound
     */
    void deleteHabitByIdUser(Long idUser, Long idHabit)  throws ModelNotFound;

    /**
     * Процедура отметки даты, когда выполнялась привычка
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @throws ModelNotFound
     */
    void setDoneDatesHabitByIdUser(Long idUser, Long idHabit)  throws ModelNotFound;

    /**
     * Функция получения генерации статистики выполнения привычки
     * @param idUser - id пользователя
     * @param statistics - указания периода (DAY - за день, WEEK - за неделю, MONTH - месяц)
     * @param idHabit - id привычки
     * @param dateFrom - дата, с какого числа нужно посчитать статистику
     * @return возвращает статистики выполнения привычки
     * @throws ModelNotFound
     */
    List<String> getHabitFulfillmentStatisticsByIdUser(Long idUser, Statistics statistics, Long idHabit, LocalDate dateFrom)  throws ModelNotFound;

    /**
     * Функция получения процента успешного выполнения привычек за определенный период
     * @param idUser - id пользователя
     * @param dateFrom - с какого числа
     * @param dateTo - по какое число
     * @return возвращает процент успешного выполнения привычек
     * @throws ModelNotFound
     */
    Integer percentSuccessHabitsByIdUser(Long idUser, LocalDate dateFrom, LocalDate dateTo)  throws ModelNotFound;

    /**
     * Процедура формирование отчета для пользователя по прогрессу выполнения привычки
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @throws ModelNotFound
     */
    String reportHabitByIdUser(Long idUser, Long idHabit)  throws ModelNotFound;

    /**
     * Функция получения списка администраторов
     * @return возвращает копию списка администраторов
     */
    List<UserResponse> findAll();


    /**
     * Процедура включения отправки уведомления привычки в указанное время
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @param pushTime - время уведомления привычки
     * @throws ModelNotFound
     */
    void switchOnPushNotificationByIdUser(Long idUser, Long idHabit, LocalTime pushTime)  throws ModelNotFound;

    /**
     * Процедура выключения уведомления привычки
     * @param idUser - id пользователя
     * @param idHabit - id привычки
     * @throws ModelNotFound
     */
    void switchOffPushNotificationByIdUser(Long idUser, Long idHabit)  throws ModelNotFound;


}
