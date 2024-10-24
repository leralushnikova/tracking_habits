package com.lushnikova.homework_2.service.impl;

import com.lushnikova.homework_2.model.enum_for_model.Statistics;
import com.lushnikova.homework_2.dto.req.HabitRequest;
import com.lushnikova.homework_2.dto.resp.HabitResponse;
import com.lushnikova.homework_2.mapper.HabitMapper;
import com.lushnikova.homework_2.model.Habit;
import com.lushnikova.homework_2.model.enum_for_model.Repeat;
import com.lushnikova.homework_2.model.enum_for_model.Status;
import com.lushnikova.homework_2.repository.HabitRepository;
import com.lushnikova.homework_2.service.HabitService;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.List;

public class HabitServiceImpl implements HabitService {

    /** Поле преобразования привычек*/
    private final HabitMapper habitMapper;

    /** Поле репозиторий привычек */
    private final HabitRepository habitRepository;


    /**
     * Конструктор - создание нового объекта с определенными значениями
     *
     * @param habitRepository - репозиторий привычек
     */
    public HabitServiceImpl(HabitRepository habitRepository) {
        this.habitRepository = habitRepository;
        this.habitMapper = HabitMapper.INSTANCE;
    }

    /**
     * Процедура добавления привычки {@link HabitRepository#save(Habit, Long)}

     * @param habitRequest - привычка
     * @param idUser       - id пользователя
     * @throws SQLException
     */
    @Override
    public void save(HabitRequest habitRequest, Long idUser) throws SQLException {
        Habit habit = habitMapper.mapToEntity(habitRequest);
        habitRepository.save(habit, idUser);
    }

    /**
     * Функция получения привычки {@link HabitRepository#findById(Long)}
     *
     * @param id - id привычки
     * @return возвращает объект привычки
     * @throws SQLException
     */
    @Override
    public HabitResponse findById(Long id) throws SQLException {
        Habit habit = habitRepository.findById(id);
        return habitMapper.mapToResponse(habit);
    }

    /**
     * Функция получения списка привычек {@link HabitRepository#findAll(Long)}
     *
     * @param idUser - id пользователя
     * @return возвращает список привычек по idUser
     * @throws SQLException
     */
    @Override
    public List<HabitResponse> findAll(Long idUser) throws SQLException {
        return habitRepository.findAll(idUser).stream().map(habitMapper::mapToResponse).toList();
    }

    /**
     * Функция получения списка привычек пользователя по статусу {@link HabitRepository#getHabitsByStatus(Long, Status)}
     *
     * @param idUser - id пользователя
     * @param status - статус привычки
     * @return возвращает список привычек пользователя по статусу
     * @throws SQLException
     */
    @Override
    public List<HabitResponse> getHabitsByStatus(Long idUser, Status status) throws SQLException {
        return habitRepository.getHabitsByStatus(idUser, status).stream().map(habitMapper::mapToResponse).toList();
    }

    /**
     * Функция получения списка привычек пользователя по дате создания {@link HabitRepository#getHabitsByDate(Long, Date)}
     *
     * @param idUser - id пользователя
     * @param date   - дата создания
     * @return возвращает список привычек пользователя по дате создания
     * @throws SQLException
     */
    @Override
    public List<HabitResponse> getHabitsByDate(Long idUser, Date date) throws SQLException {
        return habitRepository.getHabitsByDate(idUser, date).stream().map(habitMapper::mapToResponse).toList();
    }

    /**
     * Процедура обновления названия привычки {@link HabitRepository#updateTitleByIdHabit(Long, String)}
     *
     * @param id       - id привычки
     * @param newTitle - новое название привычки
     * @throws SQLException
     */
    @Override
    public void updateTitleByIdHabit(Long id, String newTitle) throws SQLException {
        habitRepository.updateTitleByIdHabit(id, newTitle);
    }

    /**
     * Процедура обновления описания привычки пользователя {@link HabitRepository#updateDescriptionByIdHabit(Long, String)}
     *
     * @param id             - id привычки
     * @param newDescription - новое описания привычки
     * @throws SQLException
     */
    @Override
    public void updateDescriptionByIdHabit(Long id, String newDescription) throws SQLException {
        habitRepository.updateDescriptionByIdHabit(id, newDescription);
    }

    /**
     * Процедура обновления частоты выполнения привычки пользователя {@link HabitRepository#updateRepeatByIdHabit(Long, Repeat)}
     *
     * @param id        - id привычки
     * @param newRepeat - новая частота выполнения
     * @throws SQLException
     */
    @Override
    public void updateRepeatByIdHabit(Long id, Repeat newRepeat) throws SQLException {
        habitRepository.updateRepeatByIdHabit(id, newRepeat);
    }

    /**
     * Процедура обновления статуса привычки пользователя {@link HabitRepository#updateStatusByIdHabit(Long, Status)}
     *
     * @param id        - id привычки
     * @param newStatus - новый статс
     * @throws SQLException
     */
    @Override
    public void updateStatusByIdHabit(Long id, Status newStatus) throws SQLException {
        habitRepository.updateStatusByIdHabit(id, newStatus);
    }

    /**
     * Процедура удаления привычки пользователя {@link HabitRepository#delete(Long)}
     *
     * @param id - id привычки
     * @throws SQLException
     */
    @Override
    public void delete(Long id) throws SQLException {
        habitRepository.delete(id);
    }

    /**
     * Функция получения генерации статистики выполнения привычки {@link HabitRepository#getHabitFulfillmentStatistics(Long, LocalDate, Statistics)}
     *
     * @param id         - id привычки
     * @param dateFrom   - дата, с какого числа нужно посчитать статистику
     * @param statistics - указания периода (DAY - за день, WEEK - за неделю, MONTH - месяц)
     * @return возвращает статистики выполнения привычки
     * @throws SQLException
     */
    @Override
    public List<String> getHabitFulfillmentStatistics(Long id, LocalDate dateFrom, Statistics statistics) throws SQLException {
        return habitRepository.getHabitFulfillmentStatistics(id, dateFrom, statistics);
    }

    /**
     * Процедура отметки даты, когда выполнялась привычка {@link HabitRepository#setDoneDates(Long)}
     *
     * @param id - id привычки
     * @throws SQLException
     */
    @Override
    public void setDoneDates(Long id) throws SQLException {
        habitRepository.setDoneDates(id);
    }

    /**
     * Функция получения процента успешного выполнения привычек за определенный период {@link HabitRepository#percentSuccessHabits(Long, LocalDate, LocalDate)}
     *
     * @param idUser   - id пользователя
     * @param dateFrom - с какого числа
     * @param dateTo   - по какое число
     * @return возвращает процент успешного выполнения привычек
     * @throws SQLException
     */
    @Override
    public int percentSuccessHabits(Long idUser, LocalDate dateFrom, LocalDate dateTo) throws SQLException {
        return habitRepository.percentSuccessHabits(idUser, dateFrom, dateTo);
    }

    /**
     * Процедура формирование отчета для пользователя по прогрессу выполнения привычки {@link HabitRepository#reportHabit(Long)}
     *
     * @param id - id привычки
     * @throws SQLException
     */
    @Override
    public void reportHabit(Long id) throws SQLException {
        habitRepository.reportHabit(id);
    }

    /**
     * Процедура включения/выключения отправки уведомления привычки в указанное время {@link HabitRepository#switchOnOrOffPushNotification(Long, Time)}
     *
     * @param id       - id привычки
     * @param pushTime - время уведомления привычки
     * @throws SQLException
     */
    @Override
    public void switchOnOrOffPushNotification(Long id, Time pushTime) throws SQLException {
        habitRepository.switchOnOrOffPushNotification(id, pushTime);
    }
}